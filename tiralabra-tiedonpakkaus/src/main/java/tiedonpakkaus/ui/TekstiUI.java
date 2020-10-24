package tiedonpakkaus.ui;

import java.util.*;
import tiedonpakkaus.domain.TPPalvelu;

/**
 * Tilapäinen testikäyttöliittymä.
 */
public class TekstiUI {

    static Scanner lukija;

    private static void aloita() {

        lukija = new Scanner(System.in);
        TPPalvelu palvelu = new TPPalvelu();

        System.out.println("Tervetuloa!");
        System.out.println("Valitse toiminto:");
        System.out.println("(1) Pakkaa tiedosto (Huffman)");
        System.out.println("(2) Pura tiedosto (Huffman)");
        System.out.println("(3) Pakkaa tiedosto (LZW)");
        System.out.println("(4) Pura tiedosto (LZW)");
        String syote = lukija.nextLine();
        String tuloste = "";

        if (syote.equals("1")) {

            String luettavanNimi = syotaNimi();
            String tallennettavanNimi = getPakkausnimi(luettavanNimi, 1);

            tuloste = palvelu.pakkaaHuffman(luettavanNimi, tallennettavanNimi);
            System.out.println(tuloste);

        } else if (syote.equals("2")) {
            String luettavanNimi = syotaNimi();
            String tallennettavanNimi = getPurkunimi(luettavanNimi, 1);
            tuloste = palvelu.puraHuffman(luettavanNimi, tallennettavanNimi);
            System.out.println(tuloste);

        } else if (syote.equals("3")) {

            String luettavanNimi = syotaNimi();
            String tallennettavanNimi = getPakkausnimi(luettavanNimi, 2);
            int[] rajat = syotaBittirajat();
            tuloste = palvelu.pakkaaLZW(luettavanNimi, tallennettavanNimi, rajat[0], rajat[1]);
            System.out.println(tuloste);

        } else if (syote.equals("4")) {

            String luettavanNimi = syotaNimi();
            String tallennettavanNimi = getPurkunimi(luettavanNimi, 2);
            tuloste = palvelu.puraLZW(luettavanNimi, tallennettavanNimi);
            System.out.println(tuloste);

        } else {
            System.out.println("Syote ei kelpaa. Ohjelma lopetetaan.");
            System.exit(0);
        }
    }

    public static void main(String[] args) {

        aloita();
    }

    private static String syotaNimi() {

        System.out.println("Anna tiedoston nimi: ");
        String nimi = lukija.nextLine();

        return nimi;
    }

    private static String getPakkausnimi(String nimi, int m) {

        String pakkausnimi = "";
        String[] osat = nimi.split("\\.");

        if (m == 1) {
            pakkausnimi = osat[0] + ".hm";
        } else {
            pakkausnimi = osat[0] + ".lzw";
        }

        return pakkausnimi;
    }

    private static String getPurkunimi(String nimi, int m) {
        
        String purkunimi = "";
        String[] osat = nimi.split("\\.");

        System.out.println("Anna tiedostopaate");
        
        if(m == 1) {
            purkunimi = osat[0] + "_hmp" + "." + lukija.nextLine();
        } else {
            purkunimi = osat[0] + "_lzwp" + "." + lukija.nextLine();
        }

        return purkunimi;
    }

    private static int[] syotaBittirajat() {

        while (true) {
            System.out.println("Syota aloituspituus (min 9): ");
            int koodinPituus = Integer.parseInt(lukija.nextLine());

            System.out.println("Syota enimmaispituus(max 32): ");
            int maxKoodinPituus = Integer.parseInt(lukija.nextLine());

            if (koodinPituus >= 9 && maxKoodinPituus <= 32 && koodinPituus <= maxKoodinPituus) {

                return new int[]{koodinPituus, maxKoodinPituus};
            } else {
                System.out.println("Aloituspituuden ja enimmaispituuden tulee olla valilla 9-32, enimmaispituus tulee olla vahintaan yhta suuri kuin aloituspituus");
            }
        }
    }

}
