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
        frekvenssit.put((byte) 98, 4);
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
        HashMap<Byte, String> kooditaulu = huffman.getKooditaulu();

        String a = kooditaulu.get((byte) 97);
        String b = kooditaulu.get((byte) 98);
        String c = kooditaulu.get((byte) 99);

        assertEquals("0", a);
        assertEquals("11", b);
        assertEquals("101", c);

    }

    @Test
    public void koodinMerkkijonoesitysOikea() {

        // luodaan pseudoEOF-solmu ja asetetaan sille Huffman-koodi
        huffman.solmut = new Solmu[4];
        huffman.solmut[3] = new Solmu();
        huffman.solmut[3].setFrekvenssi(1);
        huffman.solmut[3].setPseudoEOF();
        huffman.solmut[3].setKoodi("100");

        // asetetaan tavujen Huffman-koodit
        huffman.kooditaulu.put((byte) 97, "0");
        huffman.kooditaulu.put((byte) 98, "11");
        huffman.kooditaulu.put((byte) 99, "101");

        // luodaan testimerkkijono ja sitä vastaava tavutaulukko
        String kirjaimet = "aaabbbcaacb";

        byte[] tavut = new byte[11];
        int merkki = 0;

        for (int i = 0; i < kirjaimet.length(); i++) {

            merkki = (int) kirjaimet.charAt(i);
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

}
