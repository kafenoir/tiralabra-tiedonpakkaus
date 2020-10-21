package tiedonpakkaus.util;

import java.io.IOException;
import java.io.InputStream;
import tiedonpakkaus.tietorakenteet.Jonosanakirja;
import tiedonpakkaus.tietorakenteet.Tavujono;

public class LZWDekoodaus {

    int koodinPituus;
    int maksimiKoodinPituus;
    int koodiIndeksi;
    Jonosanakirja jonosanakirja;
    int tulosteenIndeksi;
    byte[] tuloste;
    InputStream sisaan;
    int bittiPuskuri;
    int bittejaPuskurissa;
    int viimeksiLuettu;
    

    public LZWDekoodaus(int koodinPituus, int maksimiKoodinPituus, InputStream sisaan) {
        this.koodinPituus = koodinPituus;
        this.maksimiKoodinPituus = maksimiKoodinPituus;
        this.sisaan = sisaan;
        jonosanakirja = new Jonosanakirja(maksimiKoodinPituus);
        koodiIndeksi = 0;
        tulosteenIndeksi = 0;
        viimeksiLuettu = 0;
        tuloste = new byte[(1 << 25) - 1];
    }
    
    public byte[] dekoodaa() throws IOException {

        jonosanakirja.alustaSanakirja();
        int vanha = lue();

        Tavujono jono = jonosanakirja.getTavujono(vanha);
        kirjoitaPuskuriin(jono.getTavut());

        byte merkki = (byte) vanha;
        int uusi;
        int seuraavaRaja = (1 << koodinPituus) - 2;

        while (true) {

            uusi = lue();

            System.out.println(uusi);

            if (uusi == 256) {
                break;
            }

            if (!jonosanakirja.sisaltaaKoodin(uusi)) {

                jono = jonosanakirja.getTavujono(vanha);
                jono = new Tavujono(jono, merkki);
            } else {
                jono = jonosanakirja.getTavujono(uusi);
            }

            kirjoitaPuskuriin(jono.getTavut());

            merkki = jono.getTavut()[0];
            jonosanakirja.lisaaKoodi(vanha, merkki);

            if (jonosanakirja.koko() == seuraavaRaja) {
                koodinPituus++;
                seuraavaRaja = (1 << koodinPituus) - 2;
            }

            vanha = uusi;
        }
        
        trimmaaTuloste();

        return tuloste;
    }

    private void kirjoitaPuskuriin(byte[] jono) {

        for (int i = 0; i < jono.length; i++) {
            tuloste[tulosteenIndeksi] = jono[i];
            tulosteenIndeksi++;
        }

    }

    private int lue() throws IOException {
        if (viimeksiLuettu == 256) {
            return 256;
        }

        while (bittejaPuskurissa < koodinPituus) {
            int tavu = sisaan.read();

            bittiPuskuri |= tavu << (24 - bittejaPuskurissa);
            bittejaPuskurissa += 8;
        }

        int palautus = bittiPuskuri >>> (32 - koodinPituus);
        
        bittiPuskuri = bittiPuskuri << koodinPituus;
        bittejaPuskurissa -= koodinPituus;

        viimeksiLuettu = palautus;
        return palautus;
    }
    
    private void trimmaaTuloste() {
        
        byte[] tulosteT = new byte[tulosteenIndeksi];
        System.arraycopy(tuloste, 0, tulosteT, 0, tulosteenIndeksi);
        tuloste = tulosteT;
    }

}
