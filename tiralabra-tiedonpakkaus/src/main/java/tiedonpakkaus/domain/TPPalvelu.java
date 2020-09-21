package tiedonpakkaus.domain;

import java.util.*;
import tiedonpakkaus.file.TiedostoSanakirja;
import tiedonpakkaus.file.TiedostonLuku;

/**
 * Sovelluslogiikasta vastaava luokka.
 */
public class TPPalvelu {

    HuffmanDemo huffmanDemo;
    Huffman huffman;
    TiedostonLuku tiedostonLuku;

    public TPPalvelu() {

        huffman = new Huffman();
        huffmanDemo = new HuffmanDemo();
        tiedostonLuku = new TiedostonLuku();

    }

    /**
     * Laskee käyttäjän antaman syötteen merkkien frekvenssit ja ajaa Huffmanin
     * koodauksen syötteelle. Palauttaa luodun bittiesityksen sekä siitä avatun
     * alkuperäisen syötteen.
     *
     * @param syote käyttäjän syöttämä merkkijono
     */
    public String suoritaHuffmanMerkkijonolle(String syote) {

        huffmanDemo.laskeFrekvenssit(syote);
        huffmanDemo.rakennaKeko();
        String koodi = huffmanDemo.huffmanPakkaa();
        System.out.println(koodi);
        String purku = huffmanDemo.huffmanPura(koodi);
        System.out.println(purku);

        return koodi;
    }

    /**
     * Tulostaa käyttäjän syötteen merkkien frekvenssit. Tarkistusta varten,
     * todennäköisesti ei lopullisessa versiossa.
     *
     * @return merkit ja niiden frekvenssit merkkijonona
     */
//    public String tulostaFrekvenssit() {
//
//        String tuloste = huffman.tulostaFrekvenssit();
//
//        return tuloste;
//    }
    public void suoritaHuffmanTiedostolle(String nimi) {

        byte[] tavut = tiedostonLuku.lueTiedosto(nimi);
        huffman.suoritaHuffman(tavut);
        
            
    }

    public HashMap<String, Integer> luoSanakirjaTiedostosta(String nimi) {

        HashMap<String, Integer> sanakirja = new HashMap<>();
        

        try {
            TiedostoSanakirja sanak = new TiedostoSanakirja(nimi);
            sanakirja = sanak.getSanakirja();

        } catch (Exception ex) {
        }

        return sanakirja;

    }
    
    public byte[] lueTiedosto(String nimi) {
        
        byte[] tavut = tiedostonLuku.lueTiedosto(nimi);
        
        for (byte tavu : tavut) {
            System.out.println(tavu);
        }
        
        return tavut;
        
    }
}
