package tiedonpakkaus.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import tiedonpakkaus.util.LZWKoodaus;

public class Tallennus {

    public Tallennus() {

    }

    public void kirjoitaHuffman(byte[] koodi, HashMap<Byte, Integer> frekvenssit, String nimi) {

        try {

            File tiedosto = new File(nimi);
            FileOutputStream kirjoittaja = new FileOutputStream(tiedosto);
            DataOutputStream datavirta = new DataOutputStream(kirjoittaja);

            kirjoitaOtsake(datavirta, frekvenssit);

            for (int i = 0; i < koodi.length; i++) {
                datavirta.writeByte(koodi[i]);
            }

            datavirta.flush();

            datavirta.close();
            kirjoittaja.close();

        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        }
    }

    public void kirjoitaOtsake(DataOutputStream datavirta, HashMap<Byte, Integer> frekvenssit) throws IOException {

        int koko = frekvenssit.size();
        datavirta.writeInt(koko);

        for (Byte tavu : frekvenssit.keySet()) {

            int frek = frekvenssit.get(tavu);
            datavirta.writeInt((int) tavu);
            datavirta.writeInt(frek);
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

        } catch (IOException ex) {

        }

    }
}
