package tiedonpakkaus.tietorakenteet;

public class Koodijono {

    int etuliiteIndeksi;

    int ensimmainen;

    int vasen;
    int oikea;

    byte k;

    public Koodijono(byte uusi, int eI) {
        
        etuliiteIndeksi = eI;
        
        ensimmainen = -1;
        vasen = -1;
        oikea = -1;
        
        k = uusi;

    }
    
    
    public int getEtuliiteIndeksi() {
        return etuliiteIndeksi;
    }
    
    public void setEtuliiteIndeksi(byte i) {
        etuliiteIndeksi = (int) i & 0xFF;
    }
    
    public void setTavu(byte t) {
        this.k = t;
    }
    
    public byte getTavu() {
        return this.k;
    }

}
