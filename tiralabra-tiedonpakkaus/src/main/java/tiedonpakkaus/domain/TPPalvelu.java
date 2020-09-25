package tiedonpakkaus.domain;

import java.util.*;
import tiedonpakkaus.file.Tallennus;
import tiedonpakkaus.file.TiedostonLuku;

/**
 * Sovelluslogiikasta vastaava luokka.
 */
public class TPPalvelu {

    Huffman huffman;
    TiedostonLuku tiedostonLuku;
    Tallennus tallennus;

    public TPPalvelu() {

        huffman = new Huffman();
        tiedostonLuku = new TiedostonLuku();
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
    public void suoritaHuffmanTiedostolle(String nimiL, String nimiT) {

        byte[] tavut = tiedostonLuku.lueTiedosto(nimiL);
        byte[] huffmanKoodi = huffman.suoritaHuffman(tavut);
        HashMap<Byte, Integer> frekvenssit = huffman.getFrekvenssitaulu();
        tallennus.kirjoitaKoodi(huffmanKoodi, frekvenssit, nimiT);
    }

    /**
     * Kutsuu tiedostoihin liittyvien luokkien metodeja luku- ja
     * tallenusoperaatioita varten sekä Huffman-luokan metodeja
     * Huffman-purkamiseen.
     *
     * @param nimi purettavan tiedoston nimi
     */
    public void puraTiedosto(String nimi) {

        tiedostonLuku.luePakattuTiedosto(nimi);
        HashMap<Byte, Integer> frekvenssit = tiedostonLuku.getFrekvenssit();
        String koodi = tiedostonLuku.getKoodi();
        byte[] tavut = huffman.huffmanPura(koodi, frekvenssit);
        String[] osat = nimi.split("_");
        nimi = osat[0] + "_purettu.txt";
        tallennus.kirjoitaPurettu(tavut, nimi);
    }
}
