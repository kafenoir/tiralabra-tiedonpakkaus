
package tiedonpakkaus.tietorakenteet;

/**
 * Huffmanin puumallissa tarvittava solmua kuvaava luokka. Todennäköisesti
 * toteutan vielä yleisemmän Solmu-luokan, jonka muut erikoistuneemmat perivät.
 */
public class Solmu implements Comparable<Solmu> {

    byte merkki;
    String koodi;
    int frekvenssi;
    Solmu vasen;
    Solmu oikea;
    boolean pseudoEOF;

    public Solmu() {
        
        pseudoEOF = false;

    }

    public void setFrekvenssi(int frekvenssi) {
        this.frekvenssi = frekvenssi;
    }

    public void setMerkki(byte merkki) {
        this.merkki = merkki;
    }

    public void setVasen(Solmu vasen) {
        this.vasen = vasen;

    }

    public void setOikea(Solmu oikea) {
        this.oikea = oikea;

    }
    
    public void setPseudoEOF() {
        this.pseudoEOF = true;
    }
    
    public Solmu getVasen() {
        return this.vasen;
    }
    
    public Solmu getOikea() {
        return this.oikea;
    }

    public int getFrekvenssi() {
        return this.frekvenssi;
    }
    
    public byte getMerkki() {
        return this.merkki;
    }
    
    public String getKoodi() {
        return this.koodi;
    }
    
    public boolean isPseudoEOF() {
        return pseudoEOF;
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
        return frekvenssi == toinen.frekvenssi && merkki == toinen.merkki;
    }

}
