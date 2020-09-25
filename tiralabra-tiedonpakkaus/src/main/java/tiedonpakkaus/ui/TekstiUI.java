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
        System.out.println("(1) Pakkaa tiedosto");
        System.out.println("(2) Pura tiedosto");
        String syote = lukija.nextLine();

        if (syote.equals("1")) {
            System.out.println("Anna luettavan tiedoston nimi: ");
            String tiedostoL = lukija.nextLine();
            String[] osat = tiedostoL.split("\\.");
            String tiedostoT = osat[0] + "_pakattu.dat";
            palvelu.suoritaHuffmanTiedostolle(tiedostoL, tiedostoT);

        } else if (syote.equals("2")) {
            System.out.println("Anna purettavan tiedoston nimi: ");
            String tiedostoP = lukija.nextLine();
            palvelu.puraTiedosto(tiedostoP);

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
