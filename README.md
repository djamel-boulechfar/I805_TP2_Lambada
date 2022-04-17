# TP Compilation : Génération d'arbres abstraits

## Rendu de TP de BOULECHFAR Djameleddine et JUBARD Théo

L'objectif du TP est d'utiliser les outils JFlex et CUP pour générer des arbres abstraits correspondant à un sous ensemble du langage **λ-ada**.



##TP1

Afin de répondre à la problématique posé, une classe Arbre à été écrite avec les attributs suivants: <br>
-**type** : type abstrait permettant d'identifier le type du noeud concerné (Entier, addition, soustraction, boucle while ...) <br>
-**value**: la valeur correspondant au noeud. Celle-ci est une chaine de caractère en correspondance avec le type <br>
-**leftSon**: un arbre correspondant une partie du code <br>
-**rightSon** un arbre correspondant une partie du code <br>
**/!\Attention: La valeur de leftSon et rightSon est dictée par le type de l'arbre, ainsi le fils gauche et droit non pas forcément le même rôle.** <br>
**Par exemple, si l'arbre est de type identifieur, alors le fils gauche portera le nom de la variable tandis que l'arbre droit  portera la valeur**

###Exercice 1 et 2 :

Après complétion de la grammaire, un code complet correspondant au langage **λ-ada** peut être écrit et transposer sous forme d'arbre.
Par exemple, le code suivant:

```
12 + 5;             /* ceci est un commentaire */
10 / 2 - 3;  99;    /* le point-virgule sépare les expressions à évaluer */
/* l'évaluation donne toujours un nombre entier */
((30 * 1) + 4) mod 2; /* opérateurs binaires */
3 * -4;             /* attention à l'opérateur unaire */

let prixHt = 200;   /* une variable prend valeur lors de sa déclaration */
let prixTtc =  prixHt * 119 / 100;
prixTtc + 100.
```

Correspond à l'arbre suivant une fois affiché:

```
(;(+ 12 5)(;(-(/ 10 2) 3)(; 99(;(mod(+(* 30 1) 4) 2)(* 3 - 4)))))
```

##TP2 et 3 

Une fois l'arbre construit, la génération de code se fait via la classe Compilateur. <br>
Cette classe permet l'accès à 2 méthodes: <br>
-**generateASM**:prenant un arbre en paramètre, cette méthode permet de générer le fichier asm en fonction du contenue de l'arbre. <br>
Celui-ci est parcouru une première fois afin de connaître les variables puis une seconde fois afin de générer le code des opérations.
-**testASM**: méthode générant du code dans le fichier "compileTest.asm" un code générique  de test. <br>

Après génération de l'arbre, l'appel à **generateASM** va permettre d'obtenir un fichier "fichierCompile.asm"
compilable via l'utilisation de la machine à registre.









