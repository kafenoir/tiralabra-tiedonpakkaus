package tiedonpakkaus.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TiedostonLuku {

    byte[] tavut;

    public TiedostonLuku() {

    }

    public byte[] lueTiedosto(String nimi) {

        try {

            File tiedosto = new File(nimi);
            this.tavut = new byte[(int) tiedosto.length()];
            FileInputStream lukija = new FileInputStream(tiedosto);

            int koko = lukija.read(tavut);

        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        }

        return tavut;
    }
}
