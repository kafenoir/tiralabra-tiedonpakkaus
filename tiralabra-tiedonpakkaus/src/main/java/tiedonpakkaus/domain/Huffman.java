package tiedonpakkaus.domain;

import tiedonpakkaus.tietorakenteet.Solmu;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Huffman {

    HashMap<Byte, String> kooditaulu;
    Solmu[] solmut;

    public Huffman() {

    }

    public byte[] suoritaHuffman(byte[] tavut) {

        this.kooditaulu = new HashMap<>();
        HashMap<Byte, Integer> frekvenssit = laskeFrekvenssitTavuille(tavut);
        PriorityQueue<Solmu> minimikeko = rakennaKekoTavuista(frekvenssit);
        Solmu juuri = rakennaHuffmanPuu(frekvenssit, minimikeko);
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

    public PriorityQueue<Solmu> rakennaKekoTavuista(HashMap<Byte, Integer> frekvenssit) {

        PriorityQueue<Solmu> minimikeko = new PriorityQueue<>();
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

        // lisätään pseudoEOF (end of file character), joka osoittaa Huffman-koodin loppumisen
        Solmu pseudoEOF = new Solmu();
        pseudoEOF.setPseudoEOF();
        pseudoEOF.setFrekvenssi(1);
        minimikeko.add(pseudoEOF);
        solmut[indeksi] = pseudoEOF;

        return minimikeko;
    }

    public Solmu rakennaHuffmanPuu(HashMap<Byte, Integer> frekvenssit, PriorityQueue<Solmu> keko) {

        int n = frekvenssit.size() + 1;

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

        int n = koodi.length();
        byte[] koodiTavuina = new byte[koodi.length()];
        int laskuri = n;
        int j = 0;

        for (int i = 0; i < n; i++) {

            koodiTavuina[i] = (byte) Integer.parseInt(koodi.substring(j, j + 8));

            j = j + 8;
        }

        return koodiTavuina;

    }

    public HashMap<Byte, String> getKooditaulu() {

        return this.kooditaulu;
    }
}
