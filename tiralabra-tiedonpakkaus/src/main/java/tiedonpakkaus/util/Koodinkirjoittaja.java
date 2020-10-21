package tiedonpakkaus.util;

import java.io.IOException;
import java.io.OutputStream;

public class Koodinkirjoittaja extends OutputStream {

    byte[] koodaus;
    int koodiIndeksi;
    int bittiPuskuri;
    int bittejaPuskurissa;
    int koodinPituus;

    public Koodinkirjoittaja(byte[] koodaus) {

        this.koodaus = koodaus;
        koodiIndeksi = 0;
        bittiPuskuri = 0;
        bittejaPuskurissa = 0;
    }

    @Override
    public void write(int indeksi) throws IOException {
        bittiPuskuri |= indeksi << (32 - koodinPituus - bittejaPuskurissa);
        bittejaPuskurissa += koodinPituus;

        while (bittejaPuskurissa >= 8) {
            koodaus[koodiIndeksi] = (byte) (bittiPuskuri >>> 24);
            koodiIndeksi++;
            bittiPuskuri <<= 8;
            bittejaPuskurissa -= 8;
        }
    }
    
    public void kasvataKoodinPituutta() {
        koodinPituus++;
    }
}
