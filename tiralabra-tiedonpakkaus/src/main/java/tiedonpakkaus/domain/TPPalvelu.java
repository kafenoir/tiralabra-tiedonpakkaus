package tiedonpakkaus.domain;

import java.text.DecimalFormat;
import tiedonpakkaus.io.kirjoitaKoodattu;
import tiedonpakkaus.io.kirjoitaDekoodattu;

/**
 * Sovelluslogiikasta vastaava luokka.
 */
public class TPPalvelu {

    kirjoitaDekoodattu dekoodaus;
    kirjoitaKoodattu koodaus;

    public TPPalvelu() {
        dekoodaus = new kirjoitaDekoodattu();
        koodaus = new kirjoitaKoodattu();
    }

    /**
     * Kutsuu tiedostoihin liittyvien luokkien metodeja luku- ja
     * tallenusoperaatioita varten sekä Huffman-luokan metodeja
     * Huffman-pakkaamiseen.
     *
     * @param nimiL luettavan tiedoston nimi
     * @param nimiT pakatun tiedoston nimi
     */
    public String pakkaaHuffman(String nimiL, String nimiT) {
        
        if (koodaus.kirjoitaHuffman(nimiL, nimiT)) {
            
            double aKoko = koodaus.getKoko();
            double pKoko = koodaus.getPakatunKoko();
            DecimalFormat df = new DecimalFormat("#.##");
            double pTeho = pKoko / aKoko * 100;
            String pakkausteho = df.format(pTeho);
            
            return "Pakkaaminen onnistui! Alkuperainen koko " + aKoko + " KT. Pakatun koko " + pKoko + " KT. Pakatun koko " + pakkausteho + "% alkuperaisesta.";
        } else {
            return "Pakkaaminen epaonnistui!";
        }
    }

    /**
     * Kutsuu tiedostoihin liittyvien luokkien metodeja luku- ja
     * tallenusoperaatioita varten sekä Huffman-luokan metodeja
     * Huffman-purkamiseen.
     *
     * @param nimi purettavan tiedoston nimi
     */
    public String puraHuffman(String nimi, String purkunimi) {
        
        if (dekoodaus.kirjoitaDekoodattuHuffman(nimi, purkunimi)) {
            return "Purkaminen onnistui!";
        } else {
            return "Purkaminen epaonnistui!";
        }
    }

    public String pakkaaLZW(String nimiL, String nimiT, int aloitus, int max) {
        
        if (koodaus.kirjoitaLZW(nimiL, nimiT, aloitus, max)) {
            double aKoko = koodaus.getKoko();
            double pKoko = koodaus.getPakatunKoko();
            DecimalFormat df = new DecimalFormat("#.##");
            double pTeho = pKoko / aKoko * 100;
            String pakkausteho = df.format(pTeho);
            
            return "Pakkaaminen onnistui! Alkuperainen koko " + aKoko + " KT. Pakatun koko " + pKoko + " KT. Pakatun koko " + pakkausteho + "% alkuperaisesta.";
        } else {
            return "Pakkaaminen epaonnistui!";
        }
    }

    public String puraLZW(String nimi, String purkunimi) {

        if (dekoodaus.kirjoitaDekoodattuLZW(nimi, purkunimi)) {
            return "Purkaminen onnistui!";
        } else {
            return "Purkaminen epaonnistui!";
        }
    }
}
