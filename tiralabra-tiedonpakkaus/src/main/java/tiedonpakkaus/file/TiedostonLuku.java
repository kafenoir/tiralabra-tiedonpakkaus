package tiedonpakkaus.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TiedostonLuku {


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
}
