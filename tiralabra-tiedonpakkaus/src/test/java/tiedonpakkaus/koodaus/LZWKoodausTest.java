package tiedonpakkaus.koodaus;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LZWKoodausTest {

    LZWKoodaus lzw;
    int aloitusPituus;
    int maxPituus;
    byte syote[];
    int[] tulos;
    int tuloksenIndeksi = 0;
    int tavunIndeksi = 0;
    int puskuri = 0;
    int lkm = 0;
    byte[] koodattu;
    ByteArrayOutputStream ulos;

    @Before
    public void setUp() {

        ulos = new ByteArrayOutputStream();
        tulos = new int[10];
    }

    @Test
    public void kirjoitetaanUlosKunJonoLoytyy() throws IOException {

        lzw = new LZWKoodaus(9, 10, ulos);

        syote = new byte[5];
        syote[0] = 65;
        syote[1] = 66;
        syote[2] = 65;
        syote[3] = 66;
        syote[4] = 66;

        lzw.koodaa(syote);

        tulos = new int[10];
        koodattu = ulos.toByteArray();

        tavunIndeksi = 2;
        luku(9);

        assertEquals(65, tulos[0]);
        assertEquals(66, tulos[1]);
        assertEquals(258, tulos[2]);
    }

    public void luku(int kp) {

        while (tavunIndeksi < koodattu.length) {

            while (lkm < kp) {
                int tavu = koodattu[tavunIndeksi] & 0xFF;
                tavunIndeksi++;

                puskuri |= tavu << (24 - lkm);
                lkm += 8;
            }

            int palautus = puskuri >>> (32 - kp);

            puskuri = puskuri << kp;
            lkm -= kp;

            tulos[tuloksenIndeksi] = palautus;
            tuloksenIndeksi++;
        }
    }
}
