package tiedonpakkaus.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HuffmanDemoTest {

    HuffmanDemo huffman;

    @Before
    public void setUp() {

        this.huffman = new HuffmanDemo();

    }

    @Test
    public void frekvenssitaulussaOikeatFrekvenssit() {

        huffman.laskeFrekvenssit("cab aab");

        int a = huffman.frekvenssit.get("a");
        int b = huffman.frekvenssit.get("b");
        int c = huffman.frekvenssit.get("c");
        int vali = huffman.frekvenssit.get(" ");
        int summa = 0;
        for (String merkki : huffman.frekvenssit.keySet()) {
            summa++;
        }

        assertEquals(3, a);
        assertEquals(2, b);
        assertEquals(1, c);
        assertEquals(1, vali);
        assertEquals(4, summa);

    }

    @Test
    public void keossaOikeatSolmutOikeassaJarjestyksessa() {

        huffman.laskeFrekvenssit("ccaab abab");
        huffman.rakennaKeko();

        SolmuDemo valiSolmu = huffman.minimikeko.poll();
        SolmuDemo cSolmu = huffman.minimikeko.poll();

        SolmuDemo valiSolmuVertaa = new SolmuDemo();
        valiSolmuVertaa.setFrekvenssi(1);
        valiSolmuVertaa.setMerkki(" ");

        SolmuDemo cSolmuVertaa = new SolmuDemo();
        cSolmuVertaa.setFrekvenssi(2);
        cSolmuVertaa.setMerkki("c");

        assertEquals(valiSolmuVertaa, valiSolmu);
        assertEquals(cSolmuVertaa, cSolmu);

    }

    @Test
    public void pakkausPalauttaaOikeanKoodin() {
        
        huffman.laskeFrekvenssit("abcdef");
        huffman.frekvenssit.put("a", 45);
        huffman.frekvenssit.put("b", 13);
        huffman.frekvenssit.put("c", 12);
        huffman.frekvenssit.put("d", 16);
        huffman.frekvenssit.put("e", 9);
        huffman.frekvenssit.put("f", 5);

        huffman.rakennaKeko();
        String i = huffman.huffmanPakkaa();

        String a = huffman.kooditaulu.get("a");
        String c = huffman.kooditaulu.get("c");
        String e = huffman.kooditaulu.get("e");
        
        assertEquals("0", a);
        assertEquals("100", c);
        assertEquals("1101", e);
         
    }
    
    @Test
    public void palautettuKoodiVastaaSyotetta() {
        
        huffman.kooditaulu.put("a", "0");
        huffman.kooditaulu.put("b", "111");
        huffman.kooditaulu.put("c", "100");
        
        huffman.syote = "acb";
        String koodi = huffman.koodaaMerkkijono();
        
        assertEquals("0100111", koodi);
        
    }
    
    @Test
    public void purkuPalauttaaAlkuperaisenSyotteen() {
        
        String syote = "00011";
        
        SolmuDemo juuriSolmu = new SolmuDemo();
        SolmuDemo frekvenssiSolmu = new SolmuDemo();
        SolmuDemo aSolmu = new SolmuDemo();
        aSolmu.setMerkki("a");
        SolmuDemo bSolmu = new SolmuDemo();
        bSolmu.setMerkki("b");
        SolmuDemo cSolmu = new SolmuDemo();
        cSolmu.setMerkki("c");
        
        juuriSolmu.setVasen(frekvenssiSolmu);
        juuriSolmu.setOikea(cSolmu);
        frekvenssiSolmu.setVasen(aSolmu);
        frekvenssiSolmu.setOikea(bSolmu);
        
        huffman.juuri = juuriSolmu;
        String purettuKoodi = huffman.huffmanPura(syote);
        
        assertEquals("abc", purettuKoodi);
        
    }

}
