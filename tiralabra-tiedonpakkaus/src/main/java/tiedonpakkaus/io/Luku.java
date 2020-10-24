package tiedonpakkaus.io;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tiedonpakkaus.koodaus.LZWDekoodaus;

public class Luku {

    int[] frekvenssit;
    String koodi;
    final int BITTIMASKI = 0xFF;
    int offset;

    public Luku() {

        offset = 8;

    }

    public byte[] lueTiedosto(String nimi) {

        byte[] tavut = new byte[0];

        try {

            File tiedosto = new File(nimi);
            tavut = new byte[(int) tiedosto.length()];
            FileInputStream lukija = new FileInputStream(tiedosto);

            lukija.read(tavut);

            lukija.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Luku.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(Luku.class.getName()).log(Level.SEVERE, null, ex);

        }

        return tavut;
    }

    public void luePakattuHuffman(String nimi) {

        frekvenssit = new int[256];

        try {

            File tiedosto = new File(nimi);
            FileInputStream lukija = new FileInputStream(tiedosto);
            DataInputStream datavirta = new DataInputStream(lukija);

            lueOtsake(datavirta);

            StringBuilder rakentaja = new StringBuilder();

            byte[] puskuri = new byte[8192];

//            while (datavirta.available() > 0) {
//                tavu = datavirta.readByte();
//                rakentaja.append(String.format("%8s", Integer.toBinaryString(tavu & BITTIMASKI)).replace(' ', '0'));
//            }
            while (true) {

                if (datavirta.available() > 8192) {
                    datavirta.readFully(puskuri);
                    for (Byte tavu : puskuri) {
                        rakentaja.append(String.format("%8s", Integer.toBinaryString(tavu & BITTIMASKI)).replace(' ', '0'));
                    }
                } else {
                    puskuri = new byte[datavirta.available()];
                    datavirta.readFully(puskuri);
                    for (Byte tavu : puskuri) {
                        rakentaja.append(String.format("%8s", Integer.toBinaryString(tavu & BITTIMASKI)).replace(' ', '0'));
                    }
                    break;
                }

            }

            lukija.close();

            koodi = rakentaja.toString();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Luku.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Luku.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void lueOtsake(DataInputStream datavirta) throws IOException {

        int koko = datavirta.readByte() & BITTIMASKI;

        for (int i = 0; i < koko; i++) {
            byte tavu = datavirta.readByte();
            int frek = datavirta.readInt();
            frekvenssit[(int) tavu & BITTIMASKI] = frek;

        }
    }

    public int[] getFrekvenssit() {
        return this.frekvenssit;
    }

    public String getKoodi() {
        return this.koodi;
    }

    public byte[] luePakattuLZW(String nimi, int koodinPituus, int maksimiKoodinPituus) {

        byte[] tavut = new byte[0];

        try {

            File tiedosto = new File(nimi);
            FileInputStream lukija = new FileInputStream(tiedosto);
            LZWDekoodaus lzw = new LZWDekoodaus(koodinPituus, maksimiKoodinPituus, lukija);
            tavut = lzw.dekoodaa();

            lukija.close();

        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        }

        return tavut;
    }
}
