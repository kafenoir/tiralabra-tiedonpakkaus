
package tiedonpakkaus.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Tallennus {
    
    public Tallennus() {
        
    }
    
    public void kirjoita(String nimi, String koodi, HashMap<Byte, String> kooditaulu) {
        
        try {

            File tiedosto = new File(nimi);
            FileOutputStream kirjoittaja = new FileOutputStream(tiedosto);
            
            int laskuri = koodi.length();
            
            while (laskuri > 7) {
                
                kirjoittaja.write(Integer.parseInt(koodi.substring(0, 7)));
            }

            

        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        }
    }
    
    
    
}
