
package tiedonpakkaus.domain;

import java.util.*;

/**
 * Sovelluslogiikasta vastaava luokka.
 */

public class TPPalvelu {
    
    Huffman huffman;
    
    public TPPalvelu() {
        
        huffman = new Huffman();
        
    }
    
    /**
     * Laskee käyttäjän antaman syötteen merkkien frekvenssit ja ajaa Huffmanin koodauksen syötteelle. 
     * Palauttaa luodun bittiesityksen sekä siitä avatun alkuperäisen syötteen.
     * 
     * @param syote käyttäjän syöttämä merkkijono
     */
    
    public void suoritaHuffman(String syote) {
        
        huffman.laskeFrekvenssit(syote);
        huffman.rakennaKeko();
        String koodi = huffman.huffmanPakkaa();
        System.out.println(koodi);
        String purku = huffman.huffmanPura(koodi);
        System.out.println(purku);
    }
    
    /**
     * Tulostaa käyttäjän syötteen merkkien frekvenssit.
     * Tarkistusta varten, todennäköisesti ei lopullisessa versiossa.
     * @return merkit ja niiden frekvenssit merkkijonona
     */
    
    public String tulostaFrekvenssit() {
        
        String tuloste = huffman.tulostaFrekvenssit();
        
        return tuloste;
    }
}
