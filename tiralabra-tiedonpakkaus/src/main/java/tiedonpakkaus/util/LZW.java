
package tiedonpakkaus.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import tiedonpakkaus.tietorakenteet.Sanakirja;
import tiedonpakkaus.tietorakenteet.Tavujono;

public class LZW {
    
    Sanakirja sanakirjaA;
    int koodinPituus;
    Koodinkirjoittaja kirjoittaja;
    
    public void suoritaLZW(byte[] syote, int koodinPituus, OutputStream virta) throws IOException {
        
        this.koodinPituus = koodinPituus;
        sanakirjaA = new Sanakirja(koodinPituus);
        sanakirjaA.alustaSanakirja();
        rakennaTuloste(syote, virta);
    }
    
    
    public void rakennaTuloste(byte[] syote, OutputStream virta) throws IOException {

        
        int kap = sanakirjaA.getKap();
        byte merkki = syote[0];
        Tavujono mjono = new Tavujono(merkki, kap);
        int indeksi = 258;
        Koodinkirjoittaja kirjoittaja = new Koodinkirjoittaja(koodinPituus, new BufferedOutputStream(virta));

        for (int i = 0; i < syote.length; i++) {

            if (i != syote.length - 1) {
                merkki = syote[i + 1];
            }
            int uusi = sanakirjaA.sisaltaaTavujonon(mjono, merkki);
            if (uusi != -1) {
                mjono = new Tavujono(mjono, merkki, kap);
            } else {

                kirjoittaja.kirjoita(sanakirjaA.getKoodi(mjono));
                System.out.println(sanakirjaA.getKoodi(mjono));
                sanakirjaA.lisaaTavujono(new Tavujono(mjono, merkki, kap));
                mjono = new Tavujono(merkki, kap);

            }
 
        }

        kirjoittaja.kirjoita(mjono.getKoodi());
        kirjoittaja.kirjoita(256);
        kirjoittaja.tyhjennä();
        

    }
    
}
