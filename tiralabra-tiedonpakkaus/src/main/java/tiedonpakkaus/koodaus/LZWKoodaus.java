package tiedonpakkaus.koodaus;

import java.io.IOException;
import java.io.OutputStream;
import tiedonpakkaus.tietorakenteet.Koodijono;
import tiedonpakkaus.tietorakenteet.Sanakirja;

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

    public void lopeta() throws IOException {
        ulos.write(0);

        if (ulos != null) {
            ulos.flush();
        }

    }
    
    public void kirjoitaPituudet() throws IOException {
        
       
        ulos.write(aloituspituus);
        ulos.write(maksimiKoodinPituus);
        
    }

}
