package tiedonpakkaus.domain;

import tiedonpakkaus.tietorakenteet.Solmu;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Huffman {

    HashMap<Byte, String> kooditaulu;

    public Huffman() {
        
    }
    
    public void suoritaHuffman(byte[] tavut) {
        
        this.kooditaulu = new HashMap<>();
        HashMap<Byte, Integer> frekvenssit = laskeFrekvenssitTavuille(tavut);
        PriorityQueue<Solmu> minimikeko = rakennaKekoTavuista(frekvenssit);
        Solmu juuri = rakennaHuffmanPuu(frekvenssit, minimikeko);
        String koodi = "";
        luoKooditaulu(juuri, koodi);
        
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

        for (Byte tavu : frekvenssit.keySet()) {
            Solmu solmu = new Solmu();
            solmu.setMerkki(tavu);
            solmu.setFrekvenssi(frekvenssit.get(tavu));
            minimikeko.add(solmu);
        }
        
        return minimikeko;
    }
    
    public Solmu rakennaHuffmanPuu(HashMap<Byte, Integer> frekvenssit, PriorityQueue<Solmu> keko) {
        
        int n = frekvenssit.size();
                
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

}
