
package tiedonpakkaus.domain;


public class Merkki implements Comparable<Merkki> {
    
    String merkkijono;
    int frekvenssi;
    
    public Merkki(String merkkijono, int frekvenssi) {
        
        this.merkkijono = merkkijono;
        this.frekvenssi = frekvenssi;
    }
    
    public String haeMerkkijono() {
        
        return this.merkkijono;
    }
    
    public int haeFrekvenssi() {
        
        return this.frekvenssi;
    }

    @Override
    public int compareTo(Merkki merkki2) {
        
        return this.frekvenssi - merkki2.haeFrekvenssi();
    }
    
}
