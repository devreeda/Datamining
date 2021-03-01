# Rapport IA TP2 – Data Mining sur les données HearthStone
Voici un court rapport du TP2 sur le datamining recouvrant la transformation du dataset pour pouvoir être  utilisé par l'algorithme LCM de la librairie SPMF et l'interprétation des résultats.

## Transformation du dataset de parties en dataset de decks
Nous sommes parties d'un dataset de parties de la forme suivante : 
```
0 Begin 0
0 MTwilightWhelp 1
0 OTheCoin 1
```
Le but était de traiter les données afin de récupérer une liste de la forme : 
```
37 49 65 92 250 317 421 430 446
1 106 166 218 219 251 288 305 316 398
14 45 128 176 200 227 237 258 263 323 386
```
avec chaque nombre représentant un type de carte. Pour cela, nous avons écrit des algorithmes coupant les String afin de récupérer la partie intéressante : le nom de la carte. Nous avons récupérer aussi les numéros de parties et quel joueur utilisait quelle carte, afin de faire une liste de decks de type `List<Set<String>>` permettant de récupérer chaque deck sous la forme d'un TreeSet de String (le TreeSet permettant d'obtenir un ensemble trié de données, condition nécessaire par la suite pour l'utilisation de l'algorithme LCM). Il suffisait enfin d'écrire ces données récupérées dans un fichier `decks.txt` afin qu'il soit traité directement par l'algorithme.

## Traitement des données par l'algorithme LCM
Une fois les données récupérées, il nous suffit de les traiter par l'algorithme LCM directement utilisable depuis la librairie SPMF. Afin d'obtenir les résultats, il nous est demandé de fournir un pourcentage correspondant au minsup. Le LCM va nous fournir les différents "itemsets fréquents fermés", c'est à dire les itemset fréquents selon le minsup fourni et fermés parce que l'on n'affiche pas les itemset inclus dans un superset de même support afin de réduire les données inutiles. Après plusieurs essais, nous avons choisi un minsup à 6% car nous avons trouvé les résultats étant les plus pertinants à interpréter. Le fait d'avoir des itemsets fréquents fermés nous fournis les decks les plus  fréquemmment utilisés dans les parties afin d'en ressortir des combos de cartes intéressants. 
![](https://i.imgur.com/bQ16sLh.png)

Il faut cependant faire attention avec les données. En effet, si l'on regarde la carte la plus fréquente, elle ne nous apprend pas grand chose sur le jeu. Cette carte est la "pièce" et quand on se renseigne sur celle-ci, on se rend compte qu'elle est donnée à tout joueur ne commençant pas la partie (afin d'aider à rattraper son retard). Cependant on découvre des cartes qui se démarquent des autres, notamment le Piloted Schredder ou le Dr. Boom qui après plusieurs recherches s'avère effectivement être une carte puissante du jeu. Si l'on poursuit dans les itemsets fréquents on retrouve un combo souvent utilisé qui est le duo "Fireblast-Frostbolt", deux cartes quasiment utilisés à chaque fois ensemble (plus que séparément) et qui apparaissent dans des itemsets fréquents plus complexes de plus de 3 cartes comme "Fireblast-Frostbolt-ManaWyrm" qui apparaît 25 fois.