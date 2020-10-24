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
import tiedonpakkaus.koodaus.Huffman;
import tiedonpakkaus.koodaus.LZWKoodaus;

public class kirjoitaKoodattu {
    
    double koko;
    double pakatunKoko;

    public boolean kirjoitaHuffman(String nimiL, String nimiT) {
        
        Huffman huffman = new Huffman();
        boolean onnistui = false;
        
        try {

            File luettavaTiedosto = new File(nimiL);
            koko = luettavaTiedosto.length() / 1000;
            FileInputStream lukija = new FileInputStream(luettavaTiedosto);
            
            File tallennustiedosto = new File(nimiT);
            FileOutputStream kirjoittaja = new FileOutputStream(tallennustiedosto);
            DataOutputStream datavirta = new DataOutputStream(kirjoittaja);
            
            byte[] tavut = new byte[(int) luettavaTiedosto.length()];
            lukija.read(tavut);
            lukija.close();
            
            byte[] koodi = huffman.pakkaa(tavut);
            int[] frekvenssit = huffman.getFrekvenssitaulu();
            
            kirjoitaOtsake(datavirta, frekvenssit);

            datavirta.write(koodi);
            kirjoittaja.close();
            
            pakatunKoko = tallennustiedosto.length() / 1000;
            
            onnistui = true;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(kirjoitaDekoodattu.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(kirjoitaDekoodattu.class.getName()).log(Level.SEVERE, null, ex);

        }
        
        return onnistui;
    }

    private void kirjoitaOtsake(DataOutputStream datavirta, int[] frekvenssit) throws IOException {

        int koko = 0;

        for (int i = 0; i < frekvenssit.length; i++) {
            if (frekvenssit[i] != 0) {
                koko++;
            }
        }

        datavirta.writeByte(koko);

        for (int i = 0; i < frekvenssit.length; i++) {

            if (frekvenssit[i] != 0) {
                datavirta.writeByte(i);
                datavirta.writeInt(frekvenssit[i]);
            }
        }
    }

    public boolean kirjoitaLZW(String nimiL, String nimiT, int alkuPituus, int maxPituus) {
        
        boolean onnistui = false;

        try {

            File luettavaTiedosto = new File(nimiL);
            koko = luettavaTiedosto.length() / 1000;
            
            byte[] syote = new byte[(int) luettavaTiedosto.length()];
            FileInputStream lukija = new FileInputStream(new File(nimiL));

            lukija.read(syote);

            lukija.close();

            File tallennustiedosto = new File(nimiT);
            FileOutputStream kirjoittaja = new FileOutputStream(tallennustiedosto);
            LZWKoodaus lzw = new LZWKoodaus(alkuPituus, maxPituus, new BufferedOutputStream(kirjoittaja));
            lzw.koodaa(syote);
            lzw.lopeta();

            kirjoittaja.close();
            
            pakatunKoko = tallennustiedosto.length() / 1000;
            onnistui = true;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(kirjoitaDekoodattu.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(kirjoitaDekoodattu.class.getName()).log(Level.SEVERE, null, ex);

        }
        
        return onnistui;
    }
    
    public double getKoko() {
        return koko;
    }
    
    public double getPakatunKoko() {
        return pakatunKoko;
    }
}
