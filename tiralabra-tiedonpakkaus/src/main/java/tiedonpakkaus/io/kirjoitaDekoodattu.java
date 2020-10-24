package tiedonpakkaus.io;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tiedonpakkaus.koodaus.Huffman;
import tiedonpakkaus.koodaus.LZWDekoodaus;

public class kirjoitaDekoodattu {

    final int BITTIMASKI = 0xFF;

    public boolean kirjoitaDekoodattuHuffman(String nimiL, String nimiT) {

        boolean onnistui = false;
        Huffman huffman = new Huffman();

        try {
            
            File luettavaTiedosto = new File(nimiL);

            FileInputStream lukija = new FileInputStream(luettavaTiedosto);

            DataInputStream datavirta = new DataInputStream(lukija);

            int[] frekvenssit = lueOtsake(datavirta);
            String koodijono = rakennaJono(datavirta);
            lukija.close();

            byte[] tavut = huffman.pura(koodijono, frekvenssit);
            tallennus(tavut, nimiT);

            onnistui = true;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(kirjoitaDekoodattu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(kirjoitaDekoodattu.class.getName()).log(Level.SEVERE, null, ex);
        }

        return onnistui;

    }

    private int[] lueOtsake(DataInputStream datavirta) throws IOException {

        int[] frekvenssit = new int[256];

        int koko = datavirta.readByte() & BITTIMASKI;

        for (int i = 0; i < koko; i++) {
            byte tavu = datavirta.readByte();
            int frek = datavirta.readInt();
            frekvenssit[(int) tavu & BITTIMASKI] = frek;

        }

        return frekvenssit;
    }

    private String rakennaJono(DataInputStream datavirta) throws IOException {

        StringBuilder rakentaja = new StringBuilder();

        byte[] puskuri = new byte[8192];

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

        return rakentaja.toString();
    }

    public boolean kirjoitaDekoodattuLZW(String nimiL, String nimiT) {

        boolean onnistui = false;

        try {

            File tiedosto = new File(nimiL);
            FileInputStream lukija = new FileInputStream(tiedosto);
            LZWDekoodaus lzw = new LZWDekoodaus(lukija);
            byte[] tavut = lzw.dekoodaa();

            lukija.close();

            tallennus(tavut, nimiT);

            onnistui = true;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(kirjoitaDekoodattu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(kirjoitaDekoodattu.class.getName()).log(Level.SEVERE, null, ex);
        }

        return onnistui;
    }

    private void tallennus(byte[] tavut, String tiedostonimi) throws FileNotFoundException, IOException {

        File tallennustiedosto = new File(tiedostonimi);
        FileOutputStream kirjoittaja = new FileOutputStream(tallennustiedosto);
        kirjoittaja.write(tavut);
        kirjoittaja.close();

    }
}