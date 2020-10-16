package tiedonpakkaus.tietorakenteet;

public class Tavujono {

    private byte[] tavut;
    private int koodi;
    private int hajautusarvo;
    private Tavujono seuraava;

    /**
     * Tavujonon konstruktori.
     *
     * @param tavut tavujonon sis‰lt‰m‰t tavut tavutaulukkona
     * @param kap sanakirjan kapasiteetti hajautusarvon luomista varten
     */
    public Tavujono(byte[] tavut, int kap) {

        this.tavut = tavut;
        this.hajautusarvo = hajautuskoodi(this.tavut, kap);
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
     * Tarkistaa, ovatko kaksi tavujonoa kesken‰‰n samat eli ovatko niiden
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
     * @param tavut tavujonon sis‰lt‰m‰t tavut
     * @param kap sanakirjan kapasiteetti
     * @return
     */
    private static int hajautuskoodi(byte[] tavut, int kap) {

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
