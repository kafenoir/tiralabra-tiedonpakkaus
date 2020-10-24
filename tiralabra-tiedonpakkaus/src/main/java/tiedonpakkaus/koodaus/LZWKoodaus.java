package tiedonpakkaus.koodaus;

import java.io.IOException;
import java.io.OutputStream;
import tiedonpakkaus.tietorakenteet.Koodijono;
import tiedonpakkaus.tietorakenteet.Sanakirja;

/**
 * LZW-koodauksesta vastaava luokka
 */

public class LZWKoodaus {

    byte[] syote;
    byte[] koodattu;
    int maksimiKoodinPituus;
    int aloituspituus;
    int koodinPituus;
    int bittiIndeksi;
    int bittiPuskuri;
    int bittejaPuskurissa;
    int koodiIndeksi;
    OutputStream ulos;
    
    /**
     * Konstruktori saa bittipituuden(leveyden) alkuarvon, maksimiarvon sek�
     * tallennustiedoston tulostusvirran
     *
     * @param aloituspituus
     * @param maxPituus
     * @param ulos
     */

    public LZWKoodaus(int aloituspituus, int maxPituus, OutputStream ulos) {
        this.aloituspituus = aloituspituus;
        koodinPituus = aloituspituus;
        maksimiKoodinPituus = maxPituus;
        this.ulos = ulos;
        bittiIndeksi = 0;
        bittiPuskuri = 0;
        bittejaPuskurissa = 0;
        koodiIndeksi = 0;
    }
    
    /**
     * Algoritmi etsii j�rjestyksess� pidempi� alijonoja, kunnes se kohtaa
     * sellaisen, jota ei l�ydy sanakirjasta. T�ss� kohtaa lis�t��n tulosteeseen
     * etuliitteen (viimeinen tunnettu alijono) indeksi ja sanakirjaan
     * etuliitteen indeksin + viimeksi luetun tavun yhdistelm�. Jos nykyisell�
     * bittipituudella ei ole mahdollista lis�t� uutta indeksi�, kasvatetaan
     * bittipituutta yhdell�. Jos bittipituus on maksimissa ja viimeinen
     * mahdollinen indeksi on k�ytetty, resetoidaan sanakirja. Lopuksi
     * kirjoitetaan koodin p��ttymisen merkitsev� eof-arvo.
     *
     * @param syote
     * @throws IOException
     */

    public void koodaa(byte[] syote) throws IOException {

        long alku = System.nanoTime();
        
        kirjoitaPituudet();

        this.syote = syote;
        int koko = syote.length;
        koodattu = new byte[koko + koko / 8];
        int eof = 256;

        Sanakirja sanakirja = new Sanakirja();
        sanakirja.alustaSanakirja(maksimiKoodinPituus);
        Koodijono nykyinen = new Koodijono((byte) 0, -1);

        int seuraavaRaja = (1 << koodinPituus) - 1;

        for (int i = 0; i < koko; i++) {

            nykyinen.setTavu(syote[i]);
            if (!sanakirja.etsiKoodiJono(nykyinen)) {
                kirjoita(nykyinen.getEtuliiteIndeksi());
                nykyinen.setEtuliiteIndeksi(nykyinen.getTavu());

                if (sanakirja.koko() == seuraavaRaja) {

                    if (koodinPituus == maksimiKoodinPituus) {

                        koodinPituus = aloituspituus;
                        sanakirja.alustaSanakirja(maksimiKoodinPituus);

                    } else {
                        koodinPituus++;
                    }
                    seuraavaRaja = (1 << koodinPituus) - 1;
                }
            }
        }

        kirjoita(nykyinen.getEtuliiteIndeksi());
        if (sanakirja.koko() == seuraavaRaja - 1) {
            koodinPituus++;
        }

        kirjoita(eof);
        long loppu = System.nanoTime();
        System.out.println("Aikaa kului " + ((loppu - alku) / 1e9) + " s");

    }
    
     /**
     * 32-bittinen puskuri, jonka avulla jaetaan ja kirjoitetaan vaihtelevanpituiset indeksiarvot tavuina.
     * @param indeksi
     * @throws IOException 
     */

    public void kirjoita(int indeksi) throws IOException {
        bittiPuskuri |= indeksi << (32 - koodinPituus - bittejaPuskurissa);
        bittejaPuskurissa += koodinPituus;

        while (bittejaPuskurissa >= 8) {
            ulos.write(bittiPuskuri >>> 24);
            koodiIndeksi++;
            bittiPuskuri <<= 8;
            bittejaPuskurissa -= 8;
        }
    }
    
    /**
     * Tyhjennet��n puskuroidun tulostevirran puskuri.
     * @throws IOException 
     */

    public void lopeta() throws IOException {
        ulos.write(0);

        if (ulos != null) {
            ulos.flush();
        }

    }
    
    /**
     * Kirjoitetaan tiedoston alkuun bittipituuden vaihteluv�li
     * @throws IOException 
     */
    
    public void kirjoitaPituudet() throws IOException {
        
       
        ulos.write(aloituspituus);
        ulos.write(maksimiKoodinPituus);
        
    }

}