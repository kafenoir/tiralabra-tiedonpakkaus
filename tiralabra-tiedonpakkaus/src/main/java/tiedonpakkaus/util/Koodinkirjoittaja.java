
package tiedonpakkaus.util;

import java.io.IOException;
import java.io.OutputStream;


public class Koodinkirjoittaja {
    
    int pituus;
    int puskuri;
    int lkm;
    OutputStream tuloste;
    
    public Koodinkirjoittaja(int koodinPituus, OutputStream tuloste) {
        
        this.tuloste = tuloste;
        this.pituus = koodinPituus;
    }
    
    public void kirjoita(int koodi) throws IOException {
        
        puskuri |= koodi << (32 - pituus - lkm);
        lkm += pituus;
        
        while (lkm >= 8) {
            tuloste.write(puskuri >>> 24);
            puskuri <<= 8;
            lkm -= 8;
        }
            
    }
    
    public void tyhjennä() throws IOException {
        kirjoita(0);
        
        if (tuloste != null) {
            tuloste.flush();
        }
    }
    
}
