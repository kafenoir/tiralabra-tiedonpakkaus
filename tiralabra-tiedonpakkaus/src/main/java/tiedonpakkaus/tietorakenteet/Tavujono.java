package tiedonpakkaus.tietorakenteet;

public class Tavujono {

    private byte[] tavut;
    private int koodi;
    private int hajautusarvo;
    private Tavujono seuraava;

    /**
     * Tavujonon konstruktori.
     *
     * @param tavut tavujonon sisältämät tavut tavutaulukkona
     * @param kap sanakirjan kapasiteetti hajautusarvon luomista varten
     */
    public Tavujono(byte tavu, int kap) {

        this.tavut = new byte[1];
        this.tavut[0] = tavu;
        this.hajautusarvo = hajautuskoodi(kap);
    }
    
    public Tavujono(Tavujono jono, byte tavu, int kap) {
        
        this.tavut = jono.getTavut();
        byte[] tavutUusi = new byte[tavut.length + 1];
        for (int i = 0; i < tavut.length; i++) {
            tavutUusi[i] = tavut[i]; 
        }
        tavutUusi[tavut.length] = tavu;
        this.tavut = tavutUusi;
        this.hajautusarvo = hajautuskoodi(kap);
    }
    

    public void setKoodi(int koodi) {
        this.koodi = koodi;
    }

    public void setSeuraava(Tavujono tj) {

        seuraava = tj;

    }
    
    public int getKoodi() {
        
        return this.koodi;
    }

    public Tavujono getSeuraava() {

        return this.seuraava;
    }

    public byte[] getTavut() {

        return this.tavut;
    }

    public int getHajautusarvo() {

        return this.hajautusarvo;
    }

    /**
     * Tarkistaa, ovatko kaksi tavujonoa keskenään samat eli ovatko niiden
     * tavutaulukot identtiset.
     *
     * @param jono tavujono, johon verrataan
     * @return true, jos samat, false, jos ei
     */
    public boolean onSama(Tavujono jono) {

        byte[] x = this.tavut;
        byte[] y = jono.getTavut();

        if (x.length != y.length) {
            return false;
        }

        for (int i = 0; i < x.length; i++) {
            if (x[i] != y[i]) {
                return false;
            }
        }

        return true;
    }

    /**
     * Laskee tavujonolle hajautuskoodin CRC-variantilla.
     *
     * @param tavut tavujonon sisältämät tavut
     * @param kap sanakirjan kapasiteetti
     * @return
     */
    private int hajautuskoodi(int kap) {

        int isoimmat = 0;
        int h = 0;

        for (int i = 0; i < tavut.length; i++) {

            isoimmat = h & 0xf8000000;
            h <<= 5;
            h ^= (isoimmat >> 27);
            h ^= tavut[i];

        }

        h = h > 0 ? h : h + Integer.MAX_VALUE;

        return h % kap;

    }
}
