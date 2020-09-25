package tiedonpakkaus.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Tallennus {

    public Tallennus() {

    }

    public void kirjoita(byte[] koodi, String nimi) {

        try {

            File tiedosto = new File(nimi);
            FileOutputStream kirjoittaja = new FileOutputStream(tiedosto);

            kirjoittaja.write(koodi);
            
            kirjoittaja.close();

        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        }
    }
}
