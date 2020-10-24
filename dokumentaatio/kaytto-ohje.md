# Käyttö-ohje

Pakattavien tiedostojen tulee olla ohjelman juurikansiossa. 

Tekstikäyttöliittymässä valitaan Huffman- tai LZW-pakkaus. Käyttäjän tulee syöttää pakattavan tiedoston nimi.

LZW:lle tulee lisäksi antaa koodauksessa käytettävän bittipituuden aloituspituus (vähintään 9) ja maksimipituus (enintään 32). Ohjelma aloittaa LZW-koodien kirjoittamisen aloituspituudella ja kasvattaa pituutta yhdellä aina kun seuraavaa koodia ei ole mahdollista kirjoittaa käytössä olevalla pituudella. Jos maksimipituus on saavutettu ja sillä mahdolliset koodin arvot on käytetty, ohjelma alustaa koodisanakirjan ja koodien kirjoittaminen jatkuu aloituspituudella.

Huffman-pakatut tiedostot tallentuvat juurikansioon päätteellä .hm ja LZW-tiedostot päätteellä .lzw.

Myös purettavien tiedostojen tulee olla juurikansiossa ja purkaminen pitää luonnollisesti suorittaa samalla algoritmilla kuin pakkaaminen. Käyttäjän pitää myös antaa ohjelmalle purettavan tiedoston alkuperäinen tiedostopääte ilman pistettä (esim. jos tiedoston pääte ennen pakkausta oli txt, syötä ```txt```. LZW-vaihteluväli on kirjoitettu pakattuun tiedostoon, joten sitä ei tarvitse syöttää purettaessa.

