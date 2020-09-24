package tiedonpakkaus.domain;

import java.util.HashMap;
import java.util.PriorityQueue;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import tiedonpakkaus.tietorakenteet.Solmu;

public class HuffmanTest {

    Huffman huffman;
    Solmu[] solmut;

    @Before
    public void setUp() {

        huffman = new Huffman();
        solmut = new Solmu[6];

        for (int i = 0; i < 6; i++) {
            solmut[i] = new Solmu();
        }

        solmut[0].setMerkki((byte) 97);
        solmut[0].setFrekvenssi(5);
        solmut[1].setMerkki((byte) 98);
        solmut[1].setFrekvenssi(3);
        solmut[2].setMerkki((byte) 99);
        solmut[2].setFrekvenssi(2);
        solmut[3].setFrekvenssi(1);
        solmut[3].setPseudoEOF();
        
    }

    @Test
    public void tavuillaOikeatFrekvenssit() {

        String kirjaimet = "abcaab";

        byte[] tavut = new byte[6];
        int merkki = 0;

        for (int i = 0; i < kirjaimet.length(); i++) {

            merkki = (int) kirjaimet.charAt(i);
            tavut[i] = (byte) merkki;
        }

        HashMap<Byte, Integer> frek = huffman.laskeFrekvenssitTavuille(tavut);

        int a = frek.get((byte) 97);
        int b = frek.get((byte) 98);
        int c = frek.get((byte) 99);

        assertEquals(3, a);
        assertEquals(2, b);
        assertEquals(1, c);
    }

    @Test
    public void keossaOikeatSolmutOikeassaJarjestyksessa() {

        HashMap<Byte, Integer> frekvenssit = new HashMap<>();
        frekvenssit.put((byte) 97, 5);
        frekvenssit.put((byte) 98, 3);
        frekvenssit.put((byte) 99, 2);
        
        PriorityQueue<Solmu> keko = huffman.rakennaKekoTavuista(frekvenssit);
        
        Solmu pseudoEOF = keko.poll();
        Solmu c = keko.poll();
        Solmu b = keko.poll();
        Solmu a = keko.poll();
        
        assertEquals(solmut[3], pseudoEOF);
        assertEquals(solmut[2], c);
        assertEquals(solmut[1], b);
        assertEquals(solmut[0], a);
        
    }
    
    @Test
    public void puunJuuriOnOikea() {
        HashMap<Byte, Integer> frekvenssit = new HashMap<>();
        frekvenssit.put((byte) 97, 16);
        frekvenssit.put((byte) 98, 5);
        frekvenssit.put((byte) 99, 9);
        
        PriorityQueue<Solmu> keko = huffman.rakennaKekoTavuista(frekvenssit);
        
        Solmu juuri = huffman.rakennaHuffmanPuu(frekvenssit, keko);
        
        Solmu vertailu = new Solmu();
        vertailu.setFrekvenssi(31);

        assertEquals(vertailu, juuri);
    }
   
}
