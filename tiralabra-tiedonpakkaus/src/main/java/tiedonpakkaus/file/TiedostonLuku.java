package tiedonpakkaus.file;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TiedostonLuku {

    HashMap<Byte, Integer> frekvenssit;
    String koodi;

    public TiedostonLuku() {

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

        } catch (IOException ex) {

        }

        return tavut;
    }

    public void luePakattuTiedosto(String nimi) {

        this.frekvenssit = new HashMap<>();

        try {

            File tiedosto = new File(nimi);
            FileInputStream lukija = new FileInputStream(tiedosto);
            DataInputStream datavirta = new DataInputStream(lukija);

            lueOtsake(datavirta);

            StringBuilder rakentaja = new StringBuilder();

            byte tavu;
            int tavuI;

            while (datavirta.available() > 0) {
                tavu = datavirta.readByte();
                rakentaja.append(String.format("%8s", Integer.toBinaryString(tavu & 0xFF)).replace(' ', '0'));
            }

            datavirta.close();
            lukija.close();

            koodi = rakentaja.toString();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(TiedostonLuku.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TiedostonLuku.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void lueOtsake(DataInputStream datavirta) throws IOException {

        int koko = datavirta.readInt();

        for (int i = 0; i < koko; i++) {
            byte tavu = (byte) datavirta.readInt();
            int frek = datavirta.readInt();
            frekvenssit.put(tavu, frek);

        }

    }

    public HashMap<Byte, Integer> getFrekvenssit() {
        return this.frekvenssit;
    }

    public String getKoodi() {
        return this.koodi;
    }

    public byte[] luePakattuZLW(String nimi) {

        byte[] tavut = new byte[0];
        int i = 0;

        try {

            File tiedosto = new File(nimi);
            tavut = new byte[(int) tiedosto.length()];
            FileInputStream lukija = new FileInputStream(tiedosto);
            DataInputStream datavirta = new DataInputStream(lukija);
            while (datavirta.available() > 0) {
                tavut[i] = datavirta.readByte();
                i++;
            }
            
            datavirta.close();
            lukija.close();


        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        }

        return tavut;
    }
    
    public void lueTiedostoMerkkijonona() {
        
        
    }
}
