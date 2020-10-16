package tiedonpakkaus.tietorakenteet;

/**
 * LZW-koodauksessa käytettävä sanakirja.
 *
 * @author Antti
 */
public class Sanakirja {

    Tavujono[] tavujonot;
    int koko;
    int kap;
    int indeksi;

    public Sanakirja() {

        
    }
    
    public void alustaSanakirja() {
        
        this.koko = 0;
        this.kap = 4999;

        this.tavujonot = new Tavujono[kap];

        for (int i = 0; i < 256; i++) {

            byte tavu = (byte) i;
            byte[] tavut = new byte[1];
            tavut[0] = tavu;
            Tavujono jono = new Tavujono(tavut, kap);
            lisaaTavujono(jono);
        }
        
    }

    public void lisaaTavujono(Tavujono jono) {
        jono.setKoodi(koko);
        tavujonot[jono.getHajautusarvo()] = jono;
        koko++;

        if (koko > kap * 0.85) {

            kap = kap * 2;
            kasvataSanakirjaa();

        }
    }

    private void kasvataSanakirjaa() {
        Tavujono[] uusiSanakirja = new Tavujono[kap];

        for (int i = 0; i < tavujonot.length; i++) {
            uusiSanakirja[i] = tavujonot[i];
        }
    }

    /**
     * Tarkistaa, sisältääkö sanakirja annetun Tavujono-olion. Katsotaan
     * löytyykö hajautusarvon osoittamasta indeksistä jo annettu Tavujono-olio.
     * Jos löytyy, palautetaan sen koodi. Jos ei, tallennetaan se sanakirjaan
     * seuraavalla vapaalla koodilla ja palautetaan se.
     *
     * @param jono Etsittävä tavujono
     * @return tavujonoa vastaava koodi
     */
    public int sisaltaakoTavujonon(Tavujono jono) {

        if (tavujonot[jono.getHajautusarvo()] != null) {

            Tavujono vertailu = tavujonot[jono.getHajautusarvo()];

            while (true) {

                if (jono.onSama(vertailu)) {
                    return vertailu.getKoodi();
                }

                if (vertailu.getSeuraava() == null) {

                    vertailu.setSeuraava(jono);
                    jono.setKoodi(koko);
                    koko++;
                    return jono.getKoodi();
                }

                vertailu = vertailu.getSeuraava();
            }
        } else {

            lisaaTavujono(jono);
            return jono.getKoodi();
        }

    }
    


}
