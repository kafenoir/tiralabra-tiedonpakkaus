# Määrittelydokumentti

Työssä toteutetaan Huffmanin koodaus sekä Lempel-Ziv-Welch-pakkausalgoritmi (LZW). Nämä algoritmit valikoituvat projektiin, koska niitä käytetään yleisesti häviöttömässä tiedonpakkaamisessa. Tilansäästötavoitteena on päästä 40-60% alkuperäisestä tiedoston koosta. Toteutettava ohjelma kykenee myös purkamaan pakatun tiedoston.

## Syötteet

Ohjelman syötteenä käytetään suurikokoisia tekstitiedostoja. Näitä ovat esimerkiksi julkisesti saatavilla olevat englanninkieliset korpukset.

## Tavoitellut aikavaativuudet

Huffmanin algoritmi yhdistelee merkkien frekvenssejä kuvaavat puut käyttäen kekoa. Jokaisen yhdistämisoperaation aikavaativuus on O(log n) ja yhdistämisiä tehdään n - 1, joten kokonaisaikavaativuuden tulisi olla O(n log n).

LZW käy n merkkiä sisältävän syötteen kertaalleen läpi ja jokainen operaatio tapahtuu vakioajassa. Sen kokonaisaikavaativuuden tulisi siis olla O(n).

## Lähteet

Cormen, Thomas H. (2009). Introduction to Algorithms. Cambridge, Mass. : MIT Press.

## Muuta

Opinto-ohjelma: TKT

Kieli: Suomi




