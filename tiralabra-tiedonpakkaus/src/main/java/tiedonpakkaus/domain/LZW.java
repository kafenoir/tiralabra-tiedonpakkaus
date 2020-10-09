
package tiedonpakkaus.domain;

import java.util.BitSet;
import java.util.HashMap;

public class LZW {
    
    HashMap<String, Integer> sanakirjaA;
    HashMap<Integer, String> sanakirjaB;
    
    public LZW() {
        
    }
    
    public byte[] pakkaaLZW(String syote) {
        
        alustaSanakirjat();
        int[] koodi = rakennaSanakirja(syote);
        byte[] tavut = koodiTavuiksi(koodi);
        
        return tavut;
        
    }
    
    public void puraLZW(byte[] tavut) {
        
        alustaSanakirjat();
        int[] koodi = tavutKoodiksi(tavut);
        purku(koodi);
    }
    
    /**
     * Luo sanakirjan sanakirjaA, jossa avaimina ASCII-merkit 0-255 ja arvoina niiden kokonaislukuarvot, sek�
     * sanakirjan sanakirjaB, jossa arvot ja avaimet ovat vaihdettu kesken��n. Lis�t��n kumpaankin my�s tyhjennysmerkki ja 
     * lopetusmerkki arvoilla(avaimilla) 256 ja 257. 
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
     * Koodataan sy�te LZW-menetelm�ll�. 
     * Muuttujassa mjono pidet��n k�sitelt�v�n� olevaa sy�tteen osaa ja muuttujaan merkki asetetaan sy�tteen seuraava yksitt�inen merkki. 
     * Joka kierroksella katsotaan, l�ytyyk� sanakirjasta avainta mjono + merkki vastaava arvo.
     * L�ytyy: lis�t��n merkki mjonoon. 
     * Ei l�ydy: Tehd��n sanakirjaan uusi merkint� avaimella mjono + merkki ja arvoksi annetaan 
     * j�rjestyksess� seuraava kokonaisluku (numerointi l�htee siit�, mihin alustettu sanakirja loppui, eli 258). 
     * Haetaan sanakirjasta mjonoa vastaava arvo ja lis�t��n se tulosteeseen (koodi[]).
     * 
     * @param syote Tiedostosta luettu koodattava sy�te
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
                sanakirjaA.put(mjono + merkki, indeksi);
                koodi[j] = sanakirjaA.get(mjono);
                j++;
                mjono = merkki;
                indeksi++;
            }
            merkki = "";
        }
        
        koodi[j] = sanakirjaA.get(mjono);
        
        int koodiT[] = new int[j+2];
        for (int i = 0; i < j + 1; i++) {
            koodiT[i] = koodi[i];
            System.out.println(koodiT[i]);
        }   
        
        koodiT[j + 1] = 256;
        
        return koodiT;
    }   
    
    /**
     * LZW-dekoodaamisessa pohjana k�ytet��n samaa 256-merkin sanakirjaa kuin koodaamisessa sill� erotuksella, ett� avaimet ja arvot on vaihdettu kesken��n. 
     * Muuttujassa vanha pidet��n edellisen kierroksen koodia ja muuttujassa uusi seuraavaksi k�sitelt�v�� koodia. Tulostetta muodostetaan hakemalla koodeja 
     * vastaavia merkkijonoja sanakirjasta, laajentaen samalla sanakirjaa samalla periaatteella kuin koodaamisvaiheessa.
     * Jos uutta koodia vastaava merkkijono l�ytyy sanakirjasta, se asetetetaan ty�merkkijonoksi (mjono) ja lis�t��n tulosteeseen. 
     * T�ll�in sanakirjaan lis�t��n edellisen koodin merkkijono + ty�merkkijonon ensimm�inen merkki.
     * Jos uutta koodia vastaavaa merkkijonoa ei l�ydy sanakirjasta, asetetaan tulostettavaksi ty�merkkijonoksi 
     * edellisen koodin merkkijono + edellisen merkkijonon ensimm�inen merkki. Sama yhdistelm� lis�t��n sanakirjaan.
     * T�m� perustuu siihen, ett� jos uutta koodia vastaavaa merkkijonoa ei l�ydy sanakirjasta, t�ytyy sen olla t�ll� kierroksella sanakirjaan lis�tt�v� arvo, jolloin sen ensimm�isen merkin
     * t�ytyy olla sama kuin k�sitelt�v�n� olevan merkkijonon ensimm�inen merkki.
     * 
     * @param koodi tiedostosta luettu koodi taulukossa
     */
    
    public void purku(int[] koodi) {
        
        StringBuilder rakentaja = new StringBuilder();
        alustaSanakirjat();
        
        
        int vanha = koodi[0];
        String mjono = sanakirjaB.get(vanha);
        rakentaja.append(mjono);
        String merkki = "";
        merkki += mjono.charAt(0); 
        int laskuri = 258;
        
        for (int i = 1; i < koodi.length; i++) {
            
            int uusi = koodi[i];
            
            if(koodi[i] == 256) {
                break;
            }
            
            if (!sanakirjaB.containsKey(uusi)) {
                mjono = sanakirjaB.get(vanha);
                mjono = mjono + merkki;
            } else {
                mjono = sanakirjaB.get(uusi);
            }
            
            rakentaja.append(mjono);
            merkki = "";
            merkki += mjono.charAt(0);
            sanakirjaB.put(laskuri, sanakirjaB.get(vanha) + merkki);
            vanha = uusi;
            laskuri++;
             
        }
        
        System.out.println(rakentaja.toString());
        
    }
    
    public byte[] koodiTavuiksi(int[] koodi) {
        
        StringBuilder rakentaja = new StringBuilder();
        
        for (int i = 0; i < koodi.length; i++) {
            
            rakentaja.append(String.format("%12s", Integer.toBinaryString(koodi[i])).replace(' ', '0'));  
        }
        
        while (rakentaja.length() % 8 != 0) {
            rakentaja.append('0');
        }
        
        byte[] tavut = new byte[rakentaja.length() / 8];
        int j = 0;
        
        for (int i = 0; i < tavut.length; i++) {
            tavut[i] = (byte) Integer.parseInt(rakentaja.substring(j, j + 8), 2);
            j = j + 8;
            System.out.println(tavut[i]);
        }
                
        System.out.println(tavut.length);
        
        System.out.println(rakentaja.toString());
        
        return tavut;
        
    }
    
    public int[] tavutKoodiksi(byte[] tavut) {
        
        StringBuilder rakentaja = new StringBuilder();
        
        
        for (int i = 0; i < tavut.length; i++) {
            rakentaja.append(String.format("%8s",Integer.toBinaryString((int) tavut[i])).replace(' ', '0'));
        }
        
        int[] koodi = new int[rakentaja.length() / 12];
        
        System.out.println(rakentaja.toString());
        
        int j = 0;
        for (int i = 0; i < koodi.length; i++) {
            koodi[i] = Integer.parseInt(rakentaja.substring(j, j + 12), 2);
            j = j + 12;
            System.out.println(koodi[i]);
            
        }
        
        return koodi;
        
    }
}
