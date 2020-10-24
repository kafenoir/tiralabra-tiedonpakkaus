package tiedonpakkaus.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tiedonpakkaus.koodaus.LZWKoodaus;

public class Tallennus {

    public void kirjoitaHuffman(byte[] koodi, int[] frekvenssit, String nimi) {

        try {

            File tiedosto = new File(nimi);
            FileOutputStream kirjoittaja = new FileOutputStream(tiedosto);
            DataOutputStream datavirta = new DataOutputStream(kirjoittaja);

            kirjoitaOtsake(datavirta, frekvenssit);

            datavirta.write(koodi);
            kirjoittaja.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Luku.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(Luku.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public void kirjoitaOtsake(DataOutputStream datavirta, int[] frekvenssit) throws IOException {

        int koko = 0;

        for (int i = 0; i < frekvenssit.length; i++) {
            if (frekvenssit[i] != 0) {
                koko++;
            }
        }

        byte[] frek = new byte[3];

        datavirta.writeByte(koko);

        for (int i = 0; i < frekvenssit.length; i++) {

            if (frekvenssit[i] != 0) {
                datavirta.writeByte(i);
                datavirta.writeInt(frekvenssit[i]);
            }
        }

    }

    public void kirjoitaPurettu(byte[] tavut, String nimi) {

        try {

            File tiedosto = new File(nimi);
            FileOutputStream kirjoittaja = new FileOutputStream(tiedosto);
            kirjoittaja.write(tavut);
            kirjoittaja.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Tallennus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Tallennus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void kirjoitaLZW(String nimiL, String nimiT, int alkuPituus, int maxPituus) {

        try {

            File tiedosto = new File(nimiL);
            byte[] syote = new byte[(int) tiedosto.length()];
            FileInputStream lukija = new FileInputStream(new File(nimiL));

            lukija.read(syote);

            lukija.close();

            FileOutputStream kirjoittaja = new FileOutputStream(new File(nimiT));
            LZWKoodaus lzw = new LZWKoodaus(alkuPituus, maxPituus, new BufferedOutputStream(kirjoittaja));
            lzw.koodaa(syote);
            lzw.lopeta();

            kirjoittaja.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Luku.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(Luku.class.getName()).log(Level.SEVERE, null, ex);

        }

    }
}
