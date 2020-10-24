# Testausdokumentti

## Yksikkötestit

Yksikkötestit lähinnä Huffman-luokalle. LZW-testejä en valitettavasti ehtinyt kirjoittaa kuin yhden.

## Pakkaustehon testausta

Pakkaustestit The Canterbury Corpuksesta haetuille benchmark-tiedostoille. LZW-kohdalla suluissa käytetty bittipituuden vaihteluväli (HUOM! ei välttämättä optimi kyseisille tiedostoille).


Bible.txt 5519 KT
Huffman : 3196 KT, 57% alkuperäisestä
LZW (9, 21): 1768 KT, 32% alkuperäisestä

Kennedy.xls 1006 KT 
Huffman: 1123 KT, 112% alkuperäisestä
LZW (9, 19):  314 KT, 31% alkuperäisestä

Alphabet.txt 98KT
Huffman: 59 KT, 60 % alkuperäisestä
LZW: (9, 19): 3 KT,  3% alkuperäisestä

