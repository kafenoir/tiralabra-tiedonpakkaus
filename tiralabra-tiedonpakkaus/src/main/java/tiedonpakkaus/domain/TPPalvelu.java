package tiedonpakkaus.domain;

import tiedonpakkaus.koodaus.Huffman;
import java.util.*;
import tiedonpakkaus.io.Tallennus;
import tiedonpakkaus.io.Luku;

/**
 * Sovelluslogiikasta vastaava luokka.
 */
public class TPPalvelu {

    Huffman huffman;
    Luku tiedostonLuku;
    Tallennus tallennus;

    public TPPalvelu() {

        huffman = new Huffman();
        tiedostonLuku = new Luku();
        tallennus = new Tallennus();

    }

    /**
     * Kutsuu tiedostoihin liittyvien luokkien metodeja luku- ja
     * tallenusoperaatioita varten sekä Huffman-luokan metodeja
     * Huffman-pakkaamiseen.
     *
     * @param nimiL luettavan tiedoston nimi
     * @param nimiT pakatun tiedoston nimi
     */
    public void pakkaaHuffman(String nimiL, String nimiT) {

        byte[] tavut = tiedostonLuku.lueTiedosto(nimiL);
        byte[] huffmanKoodi = huffman.pakkaa(tavut);
        int[] frekvenssit = huffman.getFrekvenssitaulu();
        tallennus.kirjoitaHuffman(huffmanKoodi, frekvenssit, nimiT);
    }

    /**
     * Kutsuu tiedostoihin liittyvien luokkien metodeja luku- ja
     * tallenusoperaatioita varten sekä Huffman-luokan metodeja
     * Huffman-purkamiseen.
     *
     * @param nimi purettavan tiedoston nimi
     */
    public void puraHuffman(String nimi, String purkunimi) {

        tiedostonLuku.luePakattuHuffman(nimi);
        int[] frekvenssit = tiedostonLuku.getFrekvenssit();
        String koodi = tiedostonLuku.getKoodi();
        byte[] tavut = huffman.pura(koodi, frekvenssit);
        tallennus.kirjoitaPurettu(tavut, purkunimi);
    }

    public void pakkaaLZW(String nimiL, String nimiT, int aloitus, int max) {

        tallennus.kirjoitaLZW(nimiL, nimiT, aloitus, max);

    }

    public void puraLZW(String nimi, String purkunimi, int aloitus, int max) {

        byte[] tavut = tiedostonLuku.luePakattuLZW(nimi, aloitus, max);
        tallennus.kirjoitaPurettu(tavut, purkunimi);
    }
}
