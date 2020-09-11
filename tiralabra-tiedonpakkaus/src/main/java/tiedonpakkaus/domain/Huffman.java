package tiedonpakkaus.domain;

import java.util.HashMap;
import java.util.PriorityQueue;

public class Huffman {

    HashMap<String, Integer> frekvenssit;
    HashMap<String, String> kooditaulu;
    PriorityQueue<Solmu> minimikeko;
    int n;
    String syote;
    Solmu juuri;

    public Huffman() {

        frekvenssit = new HashMap<>();
        minimikeko = new PriorityQueue<>();
        n = 0;
        kooditaulu = new HashMap<>();
    }

    public void laskeFrekvenssit(String syote) {

        this.syote = syote;

        for (int i = 0; i < syote.length(); i++) {

            String s = syote.substring(i, i + 1);

            if (!frekvenssit.containsKey(s)) {
                frekvenssit.put(s, 1);
                n++;
            } else {
                int frekvenssi = frekvenssit.get(s);
                frekvenssit.put(s, frekvenssi + 1);
            }
        }

    }

    public String tulostaFrekvenssit() {

        StringBuilder rakentaja = new StringBuilder();

        for (String kirjain : frekvenssit.keySet()) {
            rakentaja.append(kirjain + ": " + frekvenssit.get(kirjain) + "\n");
        }

        String frekvenssiTuloste = rakentaja.toString();

        return frekvenssiTuloste;
    }

    public void rakennaKeko() {

        for (String kirjain : frekvenssit.keySet()) {
            Solmu solmu = new Solmu();
            solmu.setMerkki(kirjain);
            solmu.setFrekvenssi(frekvenssit.get(kirjain));
            minimikeko.add(solmu);
        }
    }

    public String huffmanPakkaa() {

        for (int i = 1; i < n; i++) {
            Solmu solmu = new Solmu();
            Solmu x = minimikeko.poll();
            Solmu y = minimikeko.poll();
            solmu.vasen = x;
            solmu.oikea = y;
            solmu.frekvenssi = x.getFrekvenssi() + y.getFrekvenssi();

            minimikeko.add(solmu);
        }

        String koodi = "";
        juuri = minimikeko.poll();
        luoKooditaulu(juuri, koodi);
        String huffmannKoodi = koodaaSyote();
        return huffmannKoodi;
    }

    public void luoKooditaulu(Solmu solmu, String koodi) {

        if (solmu.vasen == null && solmu.oikea == null) {
            kooditaulu.put(solmu.merkki, koodi.toString());

        } else {

            luoKooditaulu(solmu.vasen, koodi + '0');
            luoKooditaulu(solmu.oikea, koodi + '1');
        }
    }

    public String koodaaSyote() {

        StringBuilder rakentaja = new StringBuilder();

        for (int i = 0; i < syote.length(); i++) {

            String s = syote.substring(i, i + 1);
            rakentaja.append(kooditaulu.get(s));
        }

        return rakentaja.toString();

    }

    public String huffmanPura(String koodi) {

        StringBuilder rakentaja = new StringBuilder();

        Solmu solmu = juuri;
        int i = 0;

        while (i < koodi.length()) {
            
            if (solmu.vasen == null && solmu.oikea == null) {

                rakentaja.append(solmu.merkki);
                solmu = juuri;

            } else {

                if (koodi.charAt(i) == '0') {
                    solmu = solmu.vasen;

                }
                if (koodi.charAt(i) == '1') {
                    solmu = solmu.oikea;
                }
                
                i++;
            }
            
        }
        rakentaja.append(solmu.merkki);

        return rakentaja.toString();
    }
}
