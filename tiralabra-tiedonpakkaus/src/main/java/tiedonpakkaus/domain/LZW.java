
package tiedonpakkaus.domain;

import java.util.HashMap;

public class LZW {
    
    HashMap<String, Integer> sanakirjaA;
    HashMap<Integer, String> sanakirjaB;
    
    public LZW() {
        
    }
    
    public void alustaSanakirjat() {
        
        sanakirjaA = new HashMap<>();
        sanakirjaB = new HashMap<>();
        
        for (int i = 1; i < 256; i++) {
            sanakirjaA.put(String.valueOf((char) i), i);
            sanakirjaB.put(i, String.valueOf((char) i));
        }
        
        sanakirjaA.put("TyhjennysMerkki", 256);
        sanakirjaA.put("LopetusMerkki", 257);
        sanakirjaB.put(256, "TyhjennysMerkki");
        sanakirjaB.put(257, "LopetusMerkki");
    }
    
    public int[] rakennaSanakirja(String syote) {
        
        String mjono = "";
        char merkki = 'a';
        int indeksi = 258;
        int[] koodi = new int[syote.length()];
        int j = 0;
        
        for (int i = 0; i < syote.length(); i++) {
            merkki = syote.charAt(i);
            if (sanakirjaA.containsKey(mjono + merkki)) {
                mjono += merkki;
            } else {
                sanakirjaA.put(mjono + merkki, indeksi);
                koodi[j] = sanakirjaA.get(mjono);
                j++;
                mjono = String.valueOf(merkki);
                indeksi++;
            }
        }
        
        koodi[j] = sanakirjaA.get(mjono);
        
        int koodiT[] = new int[j+1];
        for (int i = 0; i < j + 1; i++) {
            koodiT[i] = koodi[i];
            System.out.println(koodiT[i]);
        }   
        
        return koodiT;
    }   
    
    public void puraLZW(int[] koodi) {
        
        StringBuilder rakentaja = new StringBuilder();
        alustaSanakirjat();
        
        
        int vanha = koodi[0];
        String s = sanakirjaB.get(vanha);
        rakentaja.append(s);
        String k = "";
        k += s.charAt(0); 
        int laskuri = 258;
        
        for (int i = 1; i < koodi.length; i++) {
            
            int uusi = koodi[i];
            
            if (!sanakirjaB.containsKey(uusi)) {
                s = sanakirjaB.get(vanha);
                s = s + k;
            } else {
                s = sanakirjaB.get(uusi);
            }
            
            rakentaja.append(s);
            k = "";
            k += s.charAt(0);
            sanakirjaB.put(laskuri, sanakirjaB.get(vanha) + k);
            vanha = uusi;
            laskuri++;
             
        }
        
        System.out.println(rakentaja.toString());
        
    }
}
