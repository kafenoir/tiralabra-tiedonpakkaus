package tiedonpakkaus.domain;

/**
 * Huffmanin puumallissa tarvittava solmua kuvaava luokka. Todennäköisesti
 * toteutan vielä yleisemmän Solmu-luokan, jonka muut erikoistuneemmat perivät.
 */
public class Solmu implements Comparable<Solmu> {

    String merkki;
    String koodi;
    int frekvenssi;
    Solmu vasen;
    Solmu oikea;

    public Solmu() {

    }

    public void setFrekvenssi(int frekvenssi) {
        this.frekvenssi = frekvenssi;
    }

    public void setMerkki(String merkki) {
        this.merkki = merkki;
    }

    public void setVasen(Solmu vasen) {
        this.vasen = vasen;

    }

    public void setOikea(Solmu oikea) {
        this.oikea = oikea;

    }

    public int getFrekvenssi() {
        return this.frekvenssi;
    }

    @Override
    public int compareTo(Solmu solmu2) {

        return this.frekvenssi - solmu2.frekvenssi;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Solmu)) {
            return false;
        }
        
        Solmu toinen = (Solmu) obj;
        return frekvenssi == toinen.frekvenssi && merkki.equals(toinen.merkki);
    }

}
