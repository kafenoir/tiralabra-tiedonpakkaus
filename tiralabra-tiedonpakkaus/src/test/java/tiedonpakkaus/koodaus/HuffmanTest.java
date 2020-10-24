package tiedonpakkaus.koodaus;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import tiedonpakkaus.tietorakenteet.HuffmanKeko;
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
        solmut[1].setFrekvenssi(4);
        solmut[2].setMerkki((byte) 99);
        solmut[2].setFrekvenssi(2);
        solmut[3].setFrekvenssi(1);
        solmut[3].setPseudoEOF();

    }

    @Test
    public void tavuillaOikeatFrekvenssit() {

        String kirjaimet = "abcaab";

        byte[] tavut = new byte[6];

        for (int i = 0; i < kirjaimet.length(); i++) {

            int merkki = (int) kirjaimet.charAt(i);
            tavut[i] = (byte) merkki;
        }

        huffman.laskeFrekvenssitTavuille(tavut);

        int a = huffman.frekvenssit[97];
        int b = huffman.frekvenssit[98];
        int c = huffman.frekvenssit[99];

        assertEquals(3, a);
        assertEquals(2, b);
        assertEquals(1, c);
    }

    @Test
    public void keossaOikeatSolmutOikeassaJarjestyksessa() {

        huffman.frekvenssit[97] = 5;
        huffman.frekvenssit[98] = 4;
        huffman.frekvenssit[99] = 2;

        HuffmanKeko keko = huffman.rakennaKekoTavuista();

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

        huffman.frekvenssit[97] = 16;
        huffman.frekvenssit[98] = 5;
        huffman.frekvenssit[99] = 9;

        HuffmanKeko keko = huffman.rakennaKekoTavuista();

        Solmu juuri = huffman.rakennaHuffmanPuu(keko);

        Solmu vertailu = new Solmu();
        vertailu.setFrekvenssi(31);

        assertEquals(vertailu, juuri);
    }

    @Test
    public void kooditaulunKooditOikeat() {

        // luodaan Huffman-puu manuaalisesti
        Solmu juuri = new Solmu();
        Solmu fSolmu = new Solmu();
        Solmu fSolmu2 = new Solmu();
        juuri.setFrekvenssi(13);
        fSolmu.setFrekvenssi(7);
        fSolmu2.setFrekvenssi(3);
        juuri.setVasen(solmut[0]);
        juuri.setOikea(fSolmu);
        fSolmu.setVasen(fSolmu2);
        fSolmu.setOikea(solmut[1]);
        fSolmu2.setVasen(solmut[3]);
        fSolmu2.setOikea(solmut[2]);

        // luodaan kooditaulu puusta
        huffman.luoKooditaulu(juuri, "");
        String[] koodit = huffman.getKooditaulu();

        String a = koodit[97];
        String b = koodit[98];
        String c = koodit[99];

        assertEquals("0", a);
        assertEquals("11", b);
        assertEquals("101", c);

    }

    @Test
    public void koodinMerkkijonoesitysOikea() {

        // luodaan pseudoEOF-solmu ja asetetaan sille Huffman-koodi
        huffman.pseudoEOF = new Solmu();
        huffman.pseudoEOF.setKoodi("100");

        // asetetaan tavujen Huffman-koodit
        huffman.koodit[97] = "0";
        huffman.koodit[98] = "11";
        huffman.koodit[99] = "101";

        // luodaan testimerkkijono ja sitä vastaava tavutaulukko
        String kirjaimet = "aaabbbcaacb";

        byte[] tavut = new byte[11];

        for (int i = 0; i < kirjaimet.length(); i++) {

            int merkki = (int) kirjaimet.charAt(i);
            tavut[i] = (byte) merkki;
        }

        // koodataan
        String koodi = huffman.koodaaTavut(tavut);

        // lopullisen koodin tulee vastata jonoa aaabbbcaacb + pseudoEOF + kaksi täytenollaa
        assertEquals("000111111101001011110000", koodi);
    }

    @Test
    public void merkkijonostaOikeatTavut() {

        String merkkijono = "000111111101001011110000";

        byte[] tavut = huffman.koodiTavuiksi(merkkijono);

        assertEquals((byte) 31, tavut[0]);
        assertEquals((byte) 240, tavut[2]);

    }
    
    @Test
    public void dekoodausOikeiksiTavuiksi() {
        
        String merkkijono = "000111111101001011110000";
        int[] frek = new int[256];
        frek[97] = 5;
        frek[98] = 4;
        frek[99] = 2;
        
        byte[] tavut = huffman.pura(merkkijono, frek);
        
        StringBuilder sb = new StringBuilder();
        
        for(byte tavu: tavut) {
            sb.append((char) tavu);
        }
        
        String dekoodaus = sb.toString();
        
        assertEquals("aaabbbcaacb", dekoodaus);
        
    }

}
