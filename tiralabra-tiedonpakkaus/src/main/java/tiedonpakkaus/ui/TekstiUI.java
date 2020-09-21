package tiedonpakkaus.ui;

import java.util.*;
import tiedonpakkaus.domain.TPPalvelu;

/**
 * Tilapäinen tekstikäyttöliittymä.
 */
public class TekstiUI {

    public static void aloita() {

        Scanner lukija = new Scanner(System.in);
        TPPalvelu palvelu = new TPPalvelu();

        System.out.println("Tervetuloa!");
        System.out.println("Luetaanko (m)erkkijono vai (t)iedosto?");
        String syote = lukija.nextLine();

        if (syote.equals("m")) {
            System.out.println("Syötä merkkijono: ");
            syote = lukija.nextLine();
            System.out.println("Luodaan ja puretaan Huffman-koodi: ");
            palvelu.suoritaHuffmanMerkkijonolle(syote);
            
        } else if (syote.equals("t")) {
            System.out.println("Anna tiedostonimi: ");
            String tiedosto = lukija.nextLine();
            palvelu.lueTiedosto(tiedosto);
        } else {
            System.out.println("Syöte ei kelpaa. Ohjelma lopetetaan.");
            System.exit(0);
        }

//        System.out.println("\n Merkkien frekvenssit: ");
//        System.out.println(palvelu.tulostaFrekvenssit());
    }

    public static void main(String[] args) {

        aloita();
    }

}
