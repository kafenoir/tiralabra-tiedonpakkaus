
package tiedonpakkaus.domain;

import java.util.*;

public class TPPalvelu {
    
    Huffman huffman;
    
    public TPPalvelu() {
        
        huffman = new Huffman();
        
    }
    
    public void suoritaHuffman(String syote) {
        
        huffman.laskeFrekvenssit(syote);
        huffman.rakennaKeko();
        String koodi = huffman.huffmanPakkaa();
        System.out.println(koodi);
        String purku = huffman.huffmanPura(koodi);
        System.out.println(purku);
    }
    
    public String tulostaFrekvenssit() {
        
        String tuloste = huffman.tulostaFrekvenssit();
        
        return tuloste;
    }
}
