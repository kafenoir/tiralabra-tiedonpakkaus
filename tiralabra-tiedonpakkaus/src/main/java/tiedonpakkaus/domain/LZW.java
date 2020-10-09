package tiedonpakkaus.domain;

import java.util.BitSet;
import java.util.HashMap;

public class LZW {

    HashMap<String, Integer> sanakirjaA;
    HashMap<Integer, String> sanakirjaB;

    public LZW() {

    }

    public byte[] pakkaaLZW(byte[] sisalto) {
        
        return null;


    }

    public byte[] puraLZW(byte[] sisalto) {
        
        return null;


    }

    /**
     * Luo sanakirjan sanakirjaA, jossa avaimina ASCII-merkit 0-255 ja arvoina
     * niiden kokonaislukuarvot, sekä sanakirjan sanakirjaB, jossa arvot ja
     * avaimet ovat vaihdettu keskenään. Lisätään kumpaankin myös
     * tyhjennysmerkki ja lopetusmerkki arvoilla(avaimilla) 256 ja 257.
     */
    public void alustaSanakirjat() {

        sanakirjaA = new HashMap<>();
        sanakirjaB = new HashMap<>();

        for (int i = 0; i < 256; i++) {
            sanakirjaA.put(String.valueOf((char) i), i);
            sanakirjaB.put(i, String.valueOf((char) i));
        }

        sanakirjaA.put(null, 256);
        sanakirjaA.put(null, 257);
        sanakirjaB.put(256, null);
        sanakirjaB.put(257, null);
    }

    /**
     * Koodataan syöte LZW-menetelmällä. Muuttujassa mjono pidetään
     * käsiteltävänä olevaa syötteen osaa ja muuttujaan merkki asetetaan
     * syötteen seuraava yksittäinen merkki. Joka kierroksella katsotaan,
     * löytyykö sanakirjasta avainta mjono + merkki vastaava arvo. Löytyy:
     * lisätään merkki mjonoon. Ei löydy: Tehdään sanakirjaan uusi merkintä
     * avaimella mjono + merkki ja arvoksi annetaan järjestyksessä seuraava
     * kokonaisluku (numerointi lähtee siitä, mihin alustettu sanakirja loppui,
     * eli 258). Haetaan sanakirjasta mjonoa vastaava arvo ja lisätään se
     * tulosteeseen (koodi[]).
     *
     * @param syote Tiedostosta luettu koodattava syöte
     * @return LZW-koodi kokonaislukutaulukkona
     */
    public int[] rakennaSanakirja(String syote) {

        String mjono = "";
        String merkki = "";
        mjono += syote.charAt(0);
        int indeksi = 258;
        int[] koodi = new int[syote.length()];
        int j = 0;

        for (int i = 0; i < syote.length(); i++) {

            if (i != syote.length() - 1) {
                merkki += syote.charAt(i + 1);
            }
            if (sanakirjaA.containsKey(mjono + merkki)) {
                mjono += merkki;
            } else {

                //System.out.println(mjono);
                koodi[j] = sanakirjaA.get(mjono);
                sanakirjaA.put(mjono + merkki, indeksi);

                j++;
                mjono = merkki;
                indeksi++;
                System.out.println(indeksi);

            }
            merkki = "";
        }

        koodi[j] = sanakirjaA.get(mjono);

        int koodiT[] = new int[j + 2];
        for (int i = 0; i < j + 1; i++) {
            koodiT[i] = koodi[i];
            //System.out.print(koodiT[i] + " ");

        }

        koodiT[j + 1] = 256;

        return koodiT;
    }

    /**
     * LZW-dekoodaamisessa pohjana käytetään samaa 256-merkin sanakirjaa kuin
     * koodaamisessa sillä erotuksella, että avaimet ja arvot on vaihdettu
     * keskenään. Muuttujassa vanha pidetään edellisen kierroksen koodia ja
     * muuttujassa uusi seuraavaksi käsiteltävää koodia. Tulostetta muodostetaan
     * hakemalla koodeja vastaavia merkkijonoja sanakirjasta, laajentaen samalla
     * sanakirjaa samalla periaatteella kuin koodaamisvaiheessa. Jos uutta
     * koodia vastaava merkkijono löytyy sanakirjasta, se asetetetaan
     * työmerkkijonoksi (mjono) ja lisätään tulosteeseen. Tällöin sanakirjaan
     * lisätään edellisen koodin merkkijono + työmerkkijonon ensimmäinen merkki.
     * Jos uutta koodia vastaavaa merkkijonoa ei löydy sanakirjasta, asetetaan
     * tulostettavaksi työmerkkijonoksi edellisen koodin merkkijono + edellisen
     * merkkijonon ensimmäinen merkki. Sama yhdistelmä lisätään sanakirjaan.
     * Tämä perustuu siihen, että jos uutta koodia vastaavaa merkkijonoa ei
     * löydy sanakirjasta, täytyy sen olla tällä kierroksella sanakirjaan
     * lisättävä arvo, jolloin sen ensimmäisen merkin täytyy olla sama kuin
     * käsiteltävänä olevan merkkijonon ensimmäinen merkki.
     *
     * @param koodi tiedostosta luettu koodi taulukossa
     */
    public String purku(int[] koodi) {

        StringBuilder rakentaja = new StringBuilder();

        int vanha = koodi[0];
        String mjono = sanakirjaB.get(vanha);
        rakentaja.append(mjono);
        String merkki = "";
        merkki += mjono.charAt(0);
        int laskuri = 258;

        for (int i = 1; i < koodi.length; i++) {

            int uusi = koodi[i];

            if (koodi[i] == 256) {
                break;
            }

            if (!sanakirjaB.containsKey(uusi)) {
                mjono = sanakirjaB.get(vanha);
                mjono = mjono + merkki;
            } else {
                mjono = sanakirjaB.get(uusi);
            }

            System.out.println(mjono);
            rakentaja.append(mjono);

            merkki = "";
            merkki += mjono.charAt(0);
            sanakirjaB.put(laskuri, sanakirjaB.get(vanha) + merkki);
            vanha = uusi;
            laskuri++;

        }
        //System.out.println("");

        return rakentaja.toString();

    }

}
