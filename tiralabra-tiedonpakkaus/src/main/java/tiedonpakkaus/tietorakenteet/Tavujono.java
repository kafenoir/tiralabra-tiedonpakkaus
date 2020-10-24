package tiedonpakkaus.tietorakenteet;

public class Tavujono {

    private byte[] tavut;

    /**
     * Tavujonon konstruktori.
     *
     * @param tavu asetettava tavu
     */
    public Tavujono(byte tavu) {

        this.tavut = new byte[1];
        this.tavut[0] = tavu;
    }

    /**
     * Tavujonon toinen konstruktori. Luo uuden tavujonon konkatenoimalla
     * annetun etuliitetavujonon ja tavun.
     *
     * @param jono etuliitetavujono
     * @param tavu tavujonoon lisättävä tavu
     */
    public Tavujono(Tavujono jono, byte tavu) {

        tavut = jono.getTavut();
        byte[] tavutUusi = new byte[tavut.length + 1];
        System.arraycopy(this.tavut, 0, tavutUusi, 0, tavut.length);
        tavutUusi[tavut.length] = tavu;
        tavut = tavutUusi;
    }

    public byte[] getTavut() {

        return this.tavut;
    }
}
