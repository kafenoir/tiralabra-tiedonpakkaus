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
        System.out.println("Valitse toiminto:");
        System.out.println("(1) Pakkaa tiedosto (Huffman)");
        System.out.println("(2) Pura tiedosto (Huffman)");
        //System.out.println("(3) Pakkaa tiedosto (LZW)");
        //System.out.println("(4) Pura tiedosto (LZW)");
        String syote = lukija.nextLine();

        if (syote.equals("1")) {
            System.out.println("Anna pakattavan tiedoston nimi: ");
            String tiedostoL = lukija.nextLine();
            String[] osat = tiedostoL.split("\\.");
            String tiedostoT = osat[0] + "_hm.dat";
            palvelu.suoritaHuffmanTiedostolle(tiedostoL, tiedostoT);

        } else if (syote.equals("2")) {
            System.out.println("Anna purettavan tiedoston nimi: ");
            String tiedostoP = lukija.nextLine();
            palvelu.puraTiedosto(tiedostoP);

        } else if (syote.equals("3")) {

            System.out.println("Anna pakattavan tiedoston nimi: ");
            String tiedostoL = lukija.nextLine();
            String[] osat = tiedostoL.split("\\.");
            String tiedostoT = osat[0] + "_zlw.dat";
            palvelu.suoritaLZW(tiedostoL, tiedostoT);

        } else if (syote.equals("4")) {

            System.out.println("Anna purettavan tiedoston nimi: ");
            String tiedostoP = lukija.nextLine();
            palvelu.puraLZW(tiedostoP);
            

        } else {
            System.out.println("Sy�te ei kelpaa. Ohjelma lopetetaan.");
            System.exit(0);
        }

//        System.out.println("\n Merkkien frekvenssit: ");
//        System.out.println(palvelu.tulostaFrekvenssit());
    }

    public static void main(String[] args) {

        aloita();
    }

}
