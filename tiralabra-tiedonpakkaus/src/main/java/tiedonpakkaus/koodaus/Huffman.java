package tiedonpakkaus.koodaus;

import tiedonpakkaus.tietorakenteet.Solmu;
import tiedonpakkaus.tietorakenteet.HuffmanKeko;

public class Huffman {

    int[] frekvenssit;
    String[] koodit;
    Solmu pseudoEOF;
    final int BITTIMASKI = 0xFF;

    public Huffman() {

        this.frekvenssit = new int[256];
        this.koodit = new String[256];

    }

    public byte[] pakkaa(byte[] tavut) {

        long alku = System.nanoTime();
        laskeFrekvenssitTavuille(tavut);
        HuffmanKeko minimikeko = rakennaKekoTavuista();
        Solmu juuri = rakennaHuffmanPuu(minimikeko);
        String tavunKoodi = "";
        luoKooditaulu(juuri, tavunKoodi);
        String koodiM = koodaaTavut(tavut);

        byte[] koodiT = koodiTavuiksi(koodiM);
        long loppu = System.nanoTime();
        System.out.println("Aikaa kului " + ((loppu - alku) / 1e9) + " s");

        return koodiT;

    }

    private void laskeFrekvenssitTavuille(byte[] tavut) {

        //HashMap<Byte, Integer> frekvenssit = new HashMap<>();
        for (byte tavu : tavut) {

            frekvenssit[tavu & BITTIMASKI]++;
        }
    }

    public HuffmanKeko rakennaKekoTavuista() {

        HuffmanKeko minimikeko = new HuffmanKeko(frekvenssit.length + 1);

        for (int i = 0; i < frekvenssit.length; i++) {

            if (frekvenssit[i] != 0) {
                Solmu solmu = new Solmu();
                solmu.setMerkki((byte) i);
                solmu.setFrekvenssi(frekvenssit[i]);
                minimikeko.add(solmu);
            }

        }
        // lisätään pseudoEOF (end of file character), joka osoittaa Huffman-koodin loppumisen
        pseudoEOF = new Solmu();
        pseudoEOF.setPseudoEOF();
        pseudoEOF.setFrekvenssi(1);
        minimikeko.add(pseudoEOF);

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
            koodit[solmu.getMerkki() & BITTIMASKI] = koodi;
            solmu.setKoodi(koodi);

        } else {

            luoKooditaulu(solmu.getVasen(), koodi + '0');
            luoKooditaulu(solmu.getOikea(), koodi + '1');
        }
    }

    public String koodaaTavut(byte[] tavut) {

        StringBuilder rakentaja = new StringBuilder();

        for (byte tavu : tavut) {

            rakentaja.append(koodit[tavu & BITTIMASKI]);
        }

        //lisätään koodin loppuun pseudoEOF:n koodi
        rakentaja.append(pseudoEOF.getKoodi());

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

    public byte[] pura(String koodi, int[] frekvenssit) {
        
        this.frekvenssit = frekvenssit;

        long alku = System.nanoTime();

        byte[] tavut = new byte[koodi.length()];
        HuffmanKeko keko = rakennaKekoTavuista();
        Solmu juuri = rakennaHuffmanPuu(keko);
        StringBuilder rakentaja = new StringBuilder();
        Solmu solmu = juuri;
        int j = 0;
        int i = 0;
        while (i < koodi.length()) {
            if (solmu.getVasen() == null && solmu.getOikea() == null) {
                if (solmu.onPseudoEOF()) {
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

        long loppu = System.nanoTime();
        System.out.println("Aikaa kului " + ((loppu - alku) / 1e9) + " s");

        byte[] tavutT = new byte[j];
        System.arraycopy(tavut, 0, tavutT, 0, j);

        return tavutT;
    }

    public String[] getKooditaulu() {

        return koodit;
    }

    public int[] getFrekvenssitaulu() {

        return frekvenssit;
    }
}
