package tiedonpakkaus.tietorakenteet;

public class Jonosanakirja {

    private int koodiNro;
    private int kap;
    private Tavujono[] tavut;
 

    public Jonosanakirja(int maksimiKoodinPituus) {
        kap = (int) (1 << maksimiKoodinPituus);
    }

    public Tavujono getTavujono(int koodi) {
        return tavut[koodi];
    }

    public int getKoodiNro() {
        return koodiNro;
    }

    public boolean sisaltaaKoodin(int koodi) {

        return tavut[koodi] != null;
    }

    public void alustaSanakirja() {

        tavut = new Tavujono[kap];
        koodiNro = 0;

        for (int i = 0; i < 256; i++) {

            byte tavu = (byte) i;
            Tavujono jono = new Tavujono(tavu);
            tavut[koodiNro] = jono;
            koodiNro++;
        }

        koodiNro = 258;

    }

    public void lisaaKoodi(int koodi, byte merkki) {

        Tavujono vanha = tavut[koodi];
        Tavujono yhdiste = new Tavujono(vanha, merkki);
        tavut[koodiNro] = yhdiste;
        koodiNro++;

    }

    public boolean sisaltaa(int k) {
        return k < koodiNro;
    }

    
    public int koko() {
        return koodiNro;
    }

}
