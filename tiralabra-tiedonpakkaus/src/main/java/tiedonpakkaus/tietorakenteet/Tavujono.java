package tiedonpakkaus.tietorakenteet;

public class Tavujono {

    private byte[] tavut;
    private int koodi;
    private Tavujono seuraava;

    /**
     * Tavujonon konstruktori.
     *
     * @param tavut tavujonon sis‰lt‰m‰t tavut tavutaulukkona
     * @param kap sanakirjan kapasiteetti hajautusarvon luomista varten
     */
    public Tavujono(byte tavu) {

        this.tavut = new byte[1];
        this.tavut[0] = tavu;
    }
    
    public Tavujono(Tavujono jono, byte tavu) {
        
        tavut = jono.getTavut();
        byte[] tavutUusi = new byte[tavut.length + 1];
        System.arraycopy(this.tavut, 0, tavutUusi, 0, tavut.length);
        tavutUusi[tavut.length] = tavu;
        tavut = tavutUusi;
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
}
