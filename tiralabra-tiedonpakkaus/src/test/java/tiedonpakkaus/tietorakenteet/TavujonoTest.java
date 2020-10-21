package tiedonpakkaus.tietorakenteet;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TavujonoTest {

    @Before
    public void setUp() {
    }

    @Test
    public void onSamaTrueJosTavutSamat() {

        byte[] a = new byte[3];
        byte[] b = new byte[3];
        byte[] c = new byte[3];
        byte[] d = new byte[2];
        
     

        for (int i = 0; i < 3; i++) {

            a[i] = (byte) i;
            b[i] = (byte) i;
            c[i] = (byte) (2 - i);
            
        }
        
        d[0] = (byte) 0;
        d[1] = (byte) 1;
        
        Tavujono tavujono1 = new Tavujono(a, 4096);
        Tavujono tavujono2 = new Tavujono(b, 1048576);
        Tavujono tavujono3 = new Tavujono(c, 4096);
        Tavujono tavujono4 = new Tavujono(d, 4096);
        
        assertTrue(tavujono1.onSama(tavujono2));
        assertFalse(tavujono1.onSama(tavujono3));
        assertFalse(tavujono1.onSama(tavujono4));
    }
}
