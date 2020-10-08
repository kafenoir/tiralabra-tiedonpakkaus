package tiedonpakkaus.tietorakenteet;

public class HuffmanKeko {

    Solmu[] alkiot;
    int kapasiteetti;
    int koko;

    public HuffmanKeko(int kap) {

        koko = 0;
        kapasiteetti = kap;
        alkiot = new Solmu[kap];
    }

    public int vanhempi(int i) {
        return (i - 1) / 2;
    }

    public int vasen(int i) {
        return 2 * i + 1;
    }

    public int oikea(int i) {
        return 2 * i + 2;
    }

    public void vaihda(Solmu[] t, int i, int j) {
        Solmu temp = t[i];
        t[i] = t[j];
        t[j] = temp;
    }

    public void add(Solmu k) {

        koko++;
        int i = koko - 1;
        alkiot[i] = k;

        while (i != 0 && alkiot[vanhempi(i)].compareTo(alkiot[i]) > 0) {
            vaihda(alkiot, i, vanhempi(i));
            i = vanhempi(i);
        }
    }
    
    public Solmu poll() {
        
        if (koko <= 0) {
            return null;
        }
        if (koko == 1) {
            koko--;
            return alkiot[0];
        }
        
        Solmu juuri = alkiot[0];
        alkiot[0] = alkiot[koko - 1];
        koko--;
        
        varmistaKekoehto(0);
        
        return juuri;
    }
    
    public void varmistaKekoehto(int i) {
        
        int vasen = vasen(i);
        int oikea = oikea(i);
        int pienin = i;
        
        if(vasen < koko && alkiot[vasen].compareTo(alkiot[pienin]) < 0) {
            pienin = vasen;
        }
        if (oikea < koko && alkiot[oikea].compareTo(alkiot[pienin]) < 0) {
            pienin = oikea;
        }
        if (pienin != i) {
            vaihda(alkiot, i, pienin);
            varmistaKekoehto(pienin);
        }
    }
    
    public int getKoko() {
        return this.koko;
    }

}
