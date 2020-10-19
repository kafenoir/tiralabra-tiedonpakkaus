
package tiedonpakkaus.tietorakenteet;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SanakirjaTest {
    
    Sanakirja sanakirja;
    
    @Before
    public void setUp() {
    }

    @Test
    public void sanakirjaAlustetaanOikein() {
        
        sanakirja = new Sanakirja();
        sanakirja.alustaSanakirja();
        
        assertEquals(256, sanakirja.koko);
        assertEquals(4999, sanakirja.kap);
        
        int summa = 0;
        for (int i = 0; i < sanakirja.koodit.length; i++) {
            if (sanakirja.koodit[i] != null) {
                summa++;
            }
        }
        
        assertEquals(256, summa);
        
    }
    
    @Test
    public void sanakirjastaHaetaanOikeatKoodit() {
        
        sanakirja = new Sanakirja();
        sanakirja.alustaSanakirja();
        
        byte[] tavut = new byte[1];
        tavut[0] = (byte) 42;
        Tavujono tJono = new Tavujono(tavut, 4096);
        
        tavut = new byte[2];
        tavut[0] = (byte) 42;
        tavut[1] = (byte) 88;
        Tavujono tJono2 = new Tavujono(tavut, 4096);
        
        int koodi = sanakirja.sisaltaakoTavujonon(tJono);
        int koodi2 = sanakirja.sisaltaakoTavujonon(tJono2);
        
        assertEquals(42, koodi);
        assertEquals(256, koodi2);
        
    }
}
