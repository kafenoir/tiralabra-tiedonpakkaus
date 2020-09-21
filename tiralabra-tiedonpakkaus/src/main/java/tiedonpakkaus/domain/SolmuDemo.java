package tiedonpakkaus.domain;

/**
 * Huffmanin puumallissa tarvittava solmua kuvaava luokka. Todennäköisesti
 * toteutan vielä yleisemmän Solmu-luokan, jonka muut erikoistuneemmat perivät.
 */
public class SolmuDemo implements Comparable<SolmuDemo> {

    String merkki;
    String koodi;
    int frekvenssi;
    SolmuDemo vasen;
    SolmuDemo oikea;

    public SolmuDemo() {

    }

    public void setFrekvenssi(int frekvenssi) {
        this.frekvenssi = frekvenssi;
    }

    public void setMerkki(String merkki) {
        this.merkki = merkki;
    }

    public void setVasen(SolmuDemo vasen) {
        this.vasen = vasen;

    }

    public void setOikea(SolmuDemo oikea) {
        this.oikea = oikea;

    }

    public int getFrekvenssi() {
        return this.frekvenssi;
    }

    @Override
    public int compareTo(SolmuDemo solmu2) {

        return this.frekvenssi - solmu2.frekvenssi;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SolmuDemo)) {
            return false;
        }
        
        SolmuDemo toinen = (SolmuDemo) obj;
        return frekvenssi == toinen.frekvenssi && merkki.equals(toinen.merkki);
    }

}
