package tiedonpakkaus.domain;

import java.util.*;
import tiedonpakkaus.file.Tiedosto;

/**
 * Sovelluslogiikasta vastaava luokka.
 */
public class TPPalvelu {

    Huffman huffman;
    Tiedosto tiedosto;

    public TPPalvelu() {

        huffman = new Huffman();

    }

    /**
     * Laskee käyttäjän antaman syötteen merkkien frekvenssit ja ajaa Huffmanin
     * koodauksen syötteelle. Palauttaa luodun bittiesityksen sekä siitä avatun
     * alkuperäisen syötteen.
     *
     * @param syote käyttäjän syöttämä merkkijono
     */
    public String suoritaHuffmanMerkkijonolle(String syote) {

        huffman.laskeFrekvenssit(syote);
        huffman.rakennaKeko();
        String koodi = huffman.huffmanPakkaa(1);
        System.out.println(koodi);
        String purku = huffman.huffmanPura(koodi);
        System.out.println(purku);

        return koodi;
    }

    /**
     * Tulostaa käyttäjän syötteen merkkien frekvenssit. Tarkistusta varten,
     * todennäköisesti ei lopullisessa versiossa.
     *
     * @return merkit ja niiden frekvenssit merkkijonona
     */
//    public String tulostaFrekvenssit() {
//
//        String tuloste = huffman.tulostaFrekvenssit();
//
//        return tuloste;
//    }
    public void suoritaHuffmanTiedostolle(String nimi) {

        ArrayList<String> aineisto = luoAineistoTiedostosta(nimi); 
        HashMap<String, Integer> sanakirja = luoSanakirjaTiedostosta("sanakirja.txt");
        
         
//        huffman.asetaFrekvenssit(sanakirja);
//        huffman.rakennaKeko();
//        huffman.asetaAineisto(aineisto);
//        huffman.huffmanPakkaa(2);
//        boolean onnistui = luoPakattuTiedosto(huffman.koodilista);
    }

    public ArrayList<String> luoAineistoTiedostosta(String nimi) {

        ArrayList<String> aineisto = new ArrayList<>();

        try {
            tiedosto = new Tiedosto(nimi);
            aineisto = tiedosto.getAineisto();

        } catch (Exception ex) {
        }

        return aineisto;

    }

    public HashMap<String, Integer> luoSanakirjaTiedostosta(String nimi) {

        HashMap<String, Integer> sanakirja = new HashMap<>();
        

        try {
            Tiedosto tiedosto = new Tiedosto(nimi);
            sanakirja = tiedosto.getSanakirja();

        } catch (Exception ex) {
        }

        return sanakirja;

    }

    public boolean luoPakattuTiedosto(ArrayList<String> koodi) {

        boolean onnistui = false;

        try {
            tiedosto.tallennaKoodiTiedostoon(koodi);
            onnistui = true;

        } catch (Exception ex) {
        }

        return onnistui;
    }
}
