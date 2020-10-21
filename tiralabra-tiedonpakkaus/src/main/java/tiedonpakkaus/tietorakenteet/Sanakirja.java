package tiedonpakkaus.tietorakenteet;

public class Sanakirja {

    Koodijono[] taulukko;
    int uusiIndeksi;
    byte[] purettuJono;

    public Sanakirja(int maksimiPituus) {

        taulukko = new Koodijono[1 << maksimiPituus];

        for (int i = 0; i < 256; i++) {
            taulukko[i] = new Koodijono((byte) 0, -1);
            taulukko[i].k = (byte) i;
        }
        
        uusiIndeksi = 258;
    }

    public boolean etsiKoodiJono(Koodijono j) {

        int indeksi = lisaa(j);
        if (indeksi != -1) {
            j.etuliiteIndeksi = indeksi;
            return true;
        }
        return false;
    }

    public int lisaa(Koodijono m) {
        
        Koodijono c = new Koodijono(m.getTavu(), m.getEtuliiteIndeksi());

        if (c.etuliiteIndeksi == -1) {
            int indeksi = (int) c.getTavu() & 0xFF;
            return indeksi;
        }
        
        int indeksi = taulukko[c.etuliiteIndeksi].ensimmainen;
        if(indeksi == -1) {
            taulukko[c.etuliiteIndeksi].ensimmainen = uusiIndeksi;
        } else {
            while (true) {
                
                if (m.getTavu() == taulukko[indeksi].getTavu()) {
                    return indeksi;
                }
                if(m.getTavu() < taulukko[indeksi].getTavu()) {
                    int seuraava = taulukko[indeksi].vasen;
                    if (seuraava == -1) {
                        taulukko[indeksi].vasen = uusiIndeksi;
                        break;
                    }
                    indeksi = seuraava;
                } else {
                    
                    int seuraava = taulukko[indeksi].oikea;
                    if (seuraava == -1) {
                        taulukko[indeksi].oikea = uusiIndeksi;
                        break;
                    }
                    indeksi = seuraava;
                }
            }
        }
        taulukko[uusiIndeksi] = c;
        uusiIndeksi++;
        
        return -1;

    }
    
    public int koko() {
        return uusiIndeksi;
    }
}
