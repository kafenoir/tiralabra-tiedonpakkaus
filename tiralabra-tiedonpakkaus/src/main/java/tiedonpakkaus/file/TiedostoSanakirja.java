package tiedonpakkaus.file;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Scanner;

public class TiedostoSanakirja {

    private HashMap<String, Integer> sanakirja;

    public TiedostoSanakirja(String tiedosto) throws Exception {

        sanakirja = new HashMap<>();

        try {
            Scanner lukija = new Scanner(new File(tiedosto));

            while (lukija.hasNextLine()) {

                String[] osat = lukija.nextLine().split("\t");

                sanakirja.put(osat[1], Integer.valueOf(osat[2]));

            }

        } catch (Exception e) {

        }

    }

    public HashMap<String, Integer> getSanakirja() {
        return this.sanakirja;
    }

}
