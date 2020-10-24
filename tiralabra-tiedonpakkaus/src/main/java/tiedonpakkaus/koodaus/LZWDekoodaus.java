package tiedonpakkaus.koodaus;

import java.io.IOException;
import java.io.InputStream;
import tiedonpakkaus.tietorakenteet.Jonosanakirja;
import tiedonpakkaus.tietorakenteet.Tavujono;

public class LZWDekoodaus {

    int aloituspituus;
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
    final int BITTIMASKI = 0xFF;

    public LZWDekoodaus(InputStream sisaan) {
        this.sisaan = sisaan;
        koodiIndeksi = 0;
        tulosteenIndeksi = 0;
        viimeksiLuettu = 0;
        tuloste = new byte[(1 << 24) - 1];
    }

    public byte[] dekoodaa() throws IOException {

        long alku = System.nanoTime();
        luePituudet();
        jonosanakirja = new Jonosanakirja(maksimiKoodinPituus);

        while (true) {

            jonosanakirja.alustaSanakirja();
            koodinPituus = aloituspituus;
            int seuraavaRaja = (1 << koodinPituus) - 2;

            int vanha = lue();
            Tavujono jono = jonosanakirja.getTavujono(vanha);
            kirjoitaPuskuriin(jono.getTavut());
            byte merkki = (byte) vanha;
            int uusi;

            while (true) {

                uusi = lue();

                if (uusi == 256) {
                    long loppu = System.nanoTime();
                    System.out.println("Aikaa kului " + ((loppu - alku) / 1e9) + " s");
                    trimmaaTuloste();
                    return tuloste;
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

                    if (koodinPituus == maksimiKoodinPituus) {
                        break;
                    } else {
                        koodinPituus++;
                    }
                    seuraavaRaja = (1 << koodinPituus) - 2;

                }

                vanha = uusi;
            }
        }

    }

    private void kirjoitaPuskuriin(byte[] jono) {

        for (int i = 0; i < jono.length; i++) {
            tuloste[tulosteenIndeksi] = jono[i];
            tulosteenIndeksi++;
        }
        
        if (tulosteenIndeksi > tuloste.length - 1) {
            byte[] tulosteUusi = new byte[tuloste.length * 2];
            System.arraycopy(tuloste, 0, tulosteUusi, 0, tuloste.length);
            tuloste = tulosteUusi;
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

    private void luePituudet() throws IOException {

        aloituspituus = sisaan.read() & BITTIMASKI;
        maksimiKoodinPituus = sisaan.read() & BITTIMASKI;
    }

}
