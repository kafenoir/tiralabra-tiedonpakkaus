package tiedonpakkaus.domain;

import tiedonpakkaus.tietorakenteet.Solmu;
import tiedonpakkaus.tietorakenteet.HuffmanKeko;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Huffman {

    HashMap<Byte, Integer> frekvenssitaulu;
    HashMap<Byte, String> kooditaulu;
    Solmu[] solmut;

    public Huffman() {

        this.kooditaulu = new HashMap<>();

    }

    public byte[] suoritaHuffman(byte[] tavut) {

        HashMap<Byte, Integer> frekvenssit = laskeFrekvenssitTavuille(tavut);
        this.frekvenssitaulu = frekvenssit;
        HuffmanKeko minimikeko = rakennaKekoTavuista(frekvenssit);
        Solmu juuri = rakennaHuffmanPuu(minimikeko);
        String tavunKoodi = "";
        luoKooditaulu(juuri, tavunKoodi);
        String koodiM = koodaaTavut(tavut);

        byte[] koodiT = koodiTavuiksi(koodiM);

        return koodiT;

    }

    public HashMap<Byte, Integer> laskeFrekvenssitTavuille(byte[] tavut) {

        HashMap<Byte, Integer> frekvenssit = new HashMap<>();

        for (byte tavu : tavut) {

            if (!frekvenssit.containsKey(tavu)) {
                frekvenssit.put(tavu, 1);
            } else {
                frekvenssit.put(tavu, frekvenssit.get(tavu) + 1);
            }

        }

        return frekvenssit;
    }

    public HuffmanKeko rakennaKekoTavuista(HashMap<Byte, Integer> frekvenssit) {

        HuffmanKeko minimikeko = new HuffmanKeko(frekvenssit.size() + 1);
        solmut = new Solmu[frekvenssit.size() + 1];
        int indeksi = 0;

        for (Byte tavu : frekvenssit.keySet()) {
            Solmu solmu = new Solmu();
            solmu.setMerkki(tavu);
            solmu.setFrekvenssi(frekvenssit.get(tavu));
            minimikeko.add(solmu);
            solmut[indeksi] = solmu;
            indeksi++;
        }

        // lis�t��n pseudoEOF (end of file character), joka osoittaa Huffman-koodin loppumisen
        Solmu pseudoEOF = new Solmu();
        pseudoEOF.setPseudoEOF();
        pseudoEOF.setFrekvenssi(1);
        minimikeko.add(pseudoEOF);
        solmut[indeksi] = pseudoEOF;

        return minimikeko;
    }

    public Solmu rakennaHuffmanPuu(HuffmanKeko keko) {

        int n = keko.getKoko();

        for (int i = 1; i < n; i++) {
            Solmu solmu = new Solmu();
            Solmu x = keko.poll();
            Solmu y = keko.poll();
            solmu.setVasen(x);
            solmu.setOikea(y);
            solmu.setFrekvenssi(x.getFrekvenssi() + y.getFrekvenssi());

            keko.add(solmu);
        }

        return keko.poll();
    }

    public void luoKooditaulu(Solmu solmu, String koodi) {

        if (solmu.getVasen() == null && solmu.getOikea() == null) {
            kooditaulu.put(solmu.getMerkki(), koodi);
            solmu.setKoodi(koodi);

        } else {

            luoKooditaulu(solmu.getVasen(), koodi + '0');
            luoKooditaulu(solmu.getOikea(), koodi + '1');
        }
    }

    public String koodaaTavut(byte[] tavut) {

        StringBuilder rakentaja = new StringBuilder();

        for (byte tavu : tavut) {

            rakentaja.append(kooditaulu.get(tavu));
        }

        //lisätään koodin loppuun pseudoEOF:n koodi
        rakentaja.append(solmut[solmut.length - 1].getKoodi());

        // lisätään täytenollia, jos viimeinen tavu vajaa
        while (rakentaja.length() % 8 != 0) {
            rakentaja.append("0");
        }

        return rakentaja.toString();

    }

    public byte[] koodiTavuiksi(String koodi) {

        byte[] koodiTavuina = new byte[koodi.length() / 8];
        int j = 0;

        for (int i = 0; i < koodiTavuina.length; i++) {

            koodiTavuina[i] = (byte) Integer.parseInt(koodi.substring(j, j + 8), 2);
            j = j + 8;
        }

        return koodiTavuina;

    }

    public byte[] huffmanPura(String koodi, HashMap<Byte, Integer> frekvenssit) {

        byte[] tavut = new byte[koodi.length()];
        HuffmanKeko keko = rakennaKekoTavuista(frekvenssit);
        Solmu juuri = rakennaHuffmanPuu(keko);
        StringBuilder rakentaja = new StringBuilder();
        Solmu solmu = juuri;
        int j = 0;
        int i = 0;
        while (i < koodi.length()) {
            if (solmu.getVasen() == null && solmu.getOikea() == null) {
                if (solmu.isPseudoEOF()) {
                    break;
                }

                tavut[j] = solmu.getMerkki();
                j++;
                solmu = juuri;
            } else {
                if (koodi.charAt(i) == '0') {
                    solmu = solmu.getVasen();
                }
                if (koodi.charAt(i) == '1') {
                    solmu = solmu.getOikea();
                }
                i++;
            }
        }

        byte[] tavutT = new byte[j];
        for (int k = 0; k < j; k++) {
            tavutT[k] = tavut[k];
        }

        return tavutT;
    }

    public HashMap<Byte, String> getKooditaulu() {

        return this.kooditaulu;
    }

    public HashMap<Byte, Integer> getFrekvenssitaulu() {

        return this.frekvenssitaulu;
    }
}
