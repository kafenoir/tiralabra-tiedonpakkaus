package tiedonpakkaus.tietorakenteet;

/**
 * Sanakirjan tavujonoa kuvaava luokka. Koodatessa tavujonot tallennetaan
 * muodossa etuliitteen indeksi (edeltävä alijono) + tavu k. Saman etuliitteen
 * jatkeena voi olla useampia tavuja, nämä on koodattu puurakenteeksi, jossa
 * vasemmalle ja oikealle laskeutumalla, joko löydetään etuliitettä ja tavua
 * vastaava jono tai ei, jolloin se lisätään.
 *
 */
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
