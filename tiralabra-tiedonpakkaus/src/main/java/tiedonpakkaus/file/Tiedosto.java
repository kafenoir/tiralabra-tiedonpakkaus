
package tiedonpakkaus.file;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Tiedosto {
    
    private ArrayList<String> aineisto;
    
    public Tiedosto(String tiedosto) throws Exception {
        
        aineisto = new ArrayList<>();
        
        try {
            Scanner lukija = new Scanner(new File(tiedosto));

            while (lukija.hasNextLine()) {
                
                String osat[] = lukija.nextLine().split(" ");
                
                for (int i = 0; i < osat.length; i++) {
                    aineisto.add(osat[i]);
                } 
            }
            
        } catch (Exception e) {
            FileWriter writer = new FileWriter(new File(tiedosto));
            writer.close();
        }
    }
    
    public ArrayList<String> getAineisto() {
        return this.aineisto;
    }
    
    public void tallennaKoodiTiedostoon(ArrayList<String> koodi) throws Exception {
        
        try (FileWriter kirjoittaja = new FileWriter(new File("testipakkaus.txt"))) {
            
            int laskuri = 1;
            for (String sana: koodi) {
                kirjoittaja.write(sana);
                laskuri++;
                if (laskuri % 20 == 0) {
                    kirjoittaja.write("\n");
                }
            }
            
        }   
    } 
    
}
