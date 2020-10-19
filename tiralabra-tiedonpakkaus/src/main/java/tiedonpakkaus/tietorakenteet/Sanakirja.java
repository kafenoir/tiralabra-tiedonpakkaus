package tiedonpakkaus.tietorakenteet;

/**
 * LZW-koodauksessa käytettävä sanakirja.
 *
 * @author Antti
 */
public class Sanakirja {

    Tavujono[] koodit;
    int[] etuliitteet;
    byte[] merkit;
    int koodiNro;
    int maxKoko;
    int kap;

    public Sanakirja(int koodinPituus) {

        kap = (int) Math.pow(2, koodinPituus + 1);
        maxKoko = (int) Math.pow(2, koodinPituus) - 1;
//        etuliitteet = new int[kap];
//        merkit = new byte[kap];
        koodit = new Tavujono[kap];

    }

    public int getKap() {
        return kap;
    }

    public int getKoodi(Tavujono uusi) {
        Tavujono tallennettu = koodit[uusi.getHajautusarvo()];
        return tallennettu.getKoodi();
    }

    public void alustaSanakirja() {

        koodit = new Tavujono[kap];
        koodiNro = 0;

        for (int i = 0; i < 256; i++) {

            byte tavu = (byte) i;
            Tavujono jono = new Tavujono(tavu, kap);
            lisaaTavujono(jono);
        }

    }

    public void lisaaTavujono(Tavujono jono) {
        jono.setKoodi(koodiNro);

        Tavujono linkki = koodit[jono.getHajautusarvo()];

        if (linkki != null) {

            while (linkki.getSeuraava() != null) {

                linkki = linkki.getSeuraava();
            }

            linkki.setSeuraava(jono);

        } else {
            koodit[jono.getHajautusarvo()] = jono;
        }

        koodiNro++;
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
    public int sisaltaaTavujonon(Tavujono jono, byte merkki) {

        Tavujono yhdiste = new Tavujono(jono, merkki, kap);

        if (koodit[yhdiste.getHajautusarvo()] != null) {

            Tavujono vertailu = koodit[yhdiste.getHajautusarvo()];

            if (yhdiste.onSama(vertailu)) {
                return vertailu.getKoodi();
            }

            while (vertailu.getSeuraava() != null) {

                vertailu = vertailu.getSeuraava();

                if (yhdiste.onSama(vertailu)) {
                    return vertailu.getKoodi();
                }
            }

            return -1;

        }

        return -1;

    }

}
