
package tiedonpakkaus.ui;

import java.util.*;
import tiedonpakkaus.domain.TPPalvelu;

public class TekstiUI {
    
    
    public static void aloita() {
        
        Scanner lukija = new Scanner(System.in);
        TPPalvelu palvelu = new TPPalvelu();
        
        System.out.println("Syötä merkkijono: ");
        String syote = lukija.nextLine();
        System.out.println("Luodaan ja puretaan Huffman-koodi: ");
        palvelu.suoritaHuffman(syote);
        System.out.println("\n Merkkien frekvenssit: ");
        System.out.println(palvelu.tulostaFrekvenssit());
    }   
    
    public static void main(String[] args) {
        
        
        aloita();
    }
    
}
