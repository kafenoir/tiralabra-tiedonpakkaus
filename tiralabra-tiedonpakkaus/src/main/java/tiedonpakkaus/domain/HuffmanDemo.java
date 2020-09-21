package tiedonpakkaus.domain;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Map;

/**
 * Huffmanin koodaukseen liittyvät toiminnot.
 */
public class HuffmanDemo {

    HashMap<String, Integer> frekvenssit;
    HashMap<Byte, Integer> tavuFrekvenssit;
    HashMap<String, String> kooditaulu;
    PriorityQueue<SolmuDemo> minimikeko;
    int n;
    String syote;
    ArrayList<String> aineisto;
    ArrayList<String> koodilista;
    SolmuDemo juuri;

    public HuffmanDemo() {

        frekvenssit = new HashMap<>();
        minimikeko = new PriorityQueue<>();
        n = 0;
        kooditaulu = new HashMap<>();

    }

    /**
     * Laskee syötteen merkkien frekvenssit ja tallettaa ne hajautustauluun.
     *
     * @param syote käyttäjän antama merkkijono
     */
    public void laskeFrekvenssit(String syote) {
        
        frekvenssit = new HashMap<>();

        this.syote = syote;

        for (int i = 0; i < syote.length(); i++) {

            String s = syote.substring(i, i + 1);

            if (!frekvenssit.containsKey(s)) {
                frekvenssit.put(s, 1);
            } else {
                int frekvenssi = frekvenssit.get(s);
                frekvenssit.put(s, frekvenssi + 1);
            }
        }
    }
    
    public void asetaFrekvenssit(HashMap<String, Integer> sanakirja) {
        
        this.frekvenssit = sanakirja;
    }

    /**
     * Palauttaa merkkijonon, jossa merkit ja niiden frekvenssit. Testikäyttöön,
     * ei lopullisessa versiossa.
     *
     * @return
     */
//    public String tulostaFrekvenssit() {
//
//        StringBuilder rakentaja = new StringBuilder();
//
//        for (String kirjain : frekvenssit.keySet()) {
//            rakentaja.append(kirjain + ": " + frekvenssit.get(kirjain) + "\n");
//        }
//
//        String frekvenssiTuloste = rakentaja.toString();
//
//        return frekvenssiTuloste;
//    }
    /**
     * Luo Solmu-olioita merkeistä ja niiden frekvensseistä. Lisää luodut
     * Solmu-oliot minimikekoon.
     */
    public void rakennaKeko() {

        for (String kirjain : frekvenssit.keySet()) {
            SolmuDemo solmu = new SolmuDemo();
            solmu.setMerkki(kirjain);
            solmu.setFrekvenssi(frekvenssit.get(kirjain));
            minimikeko.add(solmu);
        }

    }

    /**
     * Huffmanin puun muodostaminen. Alussa minimikeossa ovat vain merkkejä ja
     * niiden frekvenssejä kuvaavat solmut. Tehdään n - 1 kierrosta, joissa
     * luodaan aina uusi solmu ja haetaan minimikeosta kaksi pienimmän
     * frekvenssiarvon omaavaa puuta, jotka asetetaan luodun solmun lapsiksi.
     * Uuden solmun frekvenssiarvoksi tulee sen lasten frekvenssiarvojen summa.
     * Yhdistetty puu lisätään takaisin kekoon. Kun kierrokset on suoritettu,
     * jäljellä on yksi yhtenäinen puu.
     *
     * Puun luonnin jälkeen kutsutaan metodeita luoKooditaulu ja koodaaSyote ja
     * palautetaan jälkimmäisestä saatu merkkijono TPPalvelulle.
     *
     * Lopuksi kutsutaan metodia koodaaSyote,
     *
     * @return
     */
    public String huffmanPakkaa() {
        
        n = frekvenssit.size();

        for (int i = 1; i < n; i++) {
            SolmuDemo solmu = new SolmuDemo();
            SolmuDemo x = minimikeko.poll();
            SolmuDemo y = minimikeko.poll();
            solmu.vasen = x;
            solmu.oikea = y;
            solmu.frekvenssi = x.getFrekvenssi() + y.getFrekvenssi();

            minimikeko.add(solmu);
        }

        String koodi = "";
        juuri = minimikeko.poll();
        luoKooditaulu(juuri, koodi);

        String huffmanKoodi = "";

        return huffmanKoodi;
    }

    /**
     * Muodostaa merkkien bittiesitykset Huffmanin puuta seuraamalla ja
     * tallettaa ne hajautustauluun.
     *
     * @param solmu seuraavaksi käsiteltävä solmu
     * @param koodi bittiesitys reitistä, jota juuresta seuraamalla päästään
     * käsiteltävään solmuun
     */
    public void luoKooditaulu(SolmuDemo solmu, String koodi) {

        if (solmu.vasen == null && solmu.oikea == null) {
            kooditaulu.put(solmu.merkki, koodi.toString());

        } else {

            luoKooditaulu(solmu.vasen, koodi + '0');
            luoKooditaulu(solmu.oikea, koodi + '1');
        }
    }

    /**
     * Palauttaa käyttäjän syötteen bittiesityksen hakemalla merkkejä vastaavat
     * bittijonot hajautustaulusta
     *
     * @return palauttaa käyttäjän syötettä vastaavan bittiesityksen
     */
    public String koodaaMerkkijono() {

        StringBuilder rakentaja = new StringBuilder();

        for (int i = 0; i < syote.length(); i++) {

            String s = syote.substring(i, i + 1);
            rakentaja.append(kooditaulu.get(s));
        }

        return rakentaja.toString();

    }

    /**
     * Muuttaa bittiesityksen takaisin alkuperäiseksi merkkijonoksi. Seurataan
     * bittiesityksen osoittamaa reittiä Huffmanin puun juuresta ja aina lehteen
     * päädyttäessä lisätään merkkijonoon lehden osoittama merkki.
     *
     * @param koodi alkuperäisen merkkijonon bittiesitys
     * @return alkuperäinen merkkijono
     */
    public String huffmanPura(String koodi) {
        StringBuilder rakentaja = new StringBuilder();
        SolmuDemo solmu = juuri;
        int i = 0;
        while (i < koodi.length()) {
            if (solmu.vasen == null && solmu.oikea == null) {
                rakentaja.append(solmu.merkki);
                solmu = juuri;
            } else {
                if (koodi.charAt(i) == '0') {
                    solmu = solmu.vasen;
                }
                if (koodi.charAt(i) == '1') {
                    solmu = solmu.oikea;
                }
                i++;
            }
        }
        rakentaja.append(solmu.merkki);
        return rakentaja.toString();
    }
}
