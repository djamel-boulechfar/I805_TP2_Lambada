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
-**generateASM**:prenant un arbre en paramètre, cette méthode permet de générer un tableau de String. Pour chacune des lignes du tableau, on retrouve une action effectué en assembleur. <br>
Cette méthode récursive prend aussi en paramètre le tableau contenant les lignes de codes déjà traité dans l'arbre. <br>
Celui-ci est parcouru une première fois afin de connaître les variables puis une seconde fois afin de générer le code des opérations.
-**testASM**: méthode générant du code dans le fichier "compileTest.asm" un code générique  de test. <br>

Après génération de l'arbre, l'appel à **generateASM** va permettre d'obtenir un fichier "fichierCompile.asm"
compilable via l'utilisation de la machine à registre.

Pour décrire un peu mieux la méthode generateASM, celle-ci fait appel à la méthode **genereCode**
qui va en fonction du type de chaque noeud de l'arbre, généré le code assembleur correspondant.<br>

Afin de gérer correctement les comparaisons et l'utilsiation des boucles conditionnels,
plusieurs méthodes ont été utilisés: <br> 
-**getNumEtiquette**: méthode permettant de crée une "étiquette" soit un point de passage pour l'utilisation de condition et ainsi 
passé aux codes correspondant à la validation de la condition ou non. <br>
-**genererCondition** et **genererConditionOu**: ces méthodes permettent le passage par les conditionnel et ainsi sauter à l'étiquette satisfaisant la condition. <br>

**/!\ Attention:** L'état du compilateur permet de gérer des conditions composés de plusieurs And ou plusieurs OR mais pas le mélange de ces 2 conditions. <br>
Le passage par les AND se basant sur l'utilisation des étiquettes dès qu'une condition n'est pas remplit tandis que le OR se basant sur le passage à l'étiquette de fin dès qu'une condition est remplie, <br>
le mariage de ces 2 types de conditions n'est pas possible. <br>
Utilisé séparement, aucun problème n'est a signalé !

##Exemples

###PGCD
Le code suivant (exemple du pgcd):
```
let a = input;
let b = input;
while (0 < b)
do (let aux=(a mod b); let a=b; let b=aux );
output a
.
```
renvoi les résultats suivants:

```
>15  
>9
>>>>3
>>>>>>>>>>>>>>>>>>>>>> That's all
==================================
>15
>5
>>>>5
>>>>>>>>>>>>>>>>>>>>>> That's all
==================================
>64
>20
>>>>4
>>>>>>>>>>>>>>>>>>>>>> That's all
```
Ceux-ci correspondent bien aux résultats attendus !

###Nombre le plus grand

Un autre exemple de code pourrait être la comparaison entre 2 nombres afin de savoir lequelle des 2 est le plus grand:
```
let a = input;
let b = input;
if(a<b) then b else a;
out eax
.
```
On retrouve alors les résultats suivants:

```
>64
>20
>>>>>>>>>>>>>>>>>>>>>> That's all
PS C:\Users\theoj\Documents\GitHub\I805_TP2_Lambada\src\main\compiledFiles> java -jar vm-0.9.jar fichierCompile.asm
>64
>32
>>>>64
>>>>>>>>>>>>>>>>>>>>>> That's all
PS C:\Users\theoj\Documents\GitHub\I805_TP2_Lambada\src\main\compiledFiles> java -jar vm-0.9.jar fichierCompile.asm
>64
>65
>>>>65
>>>>>>>>>>>>>>>>>>>>>> That's all
```

###Calcul de prix
On pourrait aussi imaginé un exemple plus complexe dans lequelle ou souhaite appliqué des tarifs diffèrents selon l'age et la taille du client.
L'entrée est ainsi fixé à 15€ pour les moins de 16ans et les moins de 1m50. Si aucune condition n'est satisfaite, le prix est de 20€

On utilise le code suivant:

```
let age = input;
let taille = input;
if( (taille<150) or (age<16)) then 16 else 20;
output eax.
```

On tombe alors sur les résultats suivants:

```
//J'ai 18 ans et mesure 1m20, je m'attends donc à payer 16€ 
>18
>120
>>>>16
>>>>>>>>>>>>>>>>>>>>>> That's all
//J'ai 15 ans et mesure 1m80, je m'attends donc à payer 16€ 
>15
>180
>>>>16
>>>>>>>>>>>>>>>>>>>>>> That's all
//J'ai 19 ans et mesure 1m80, je m'attends donc à payer 20€
>19
>180
>>>>20
>>>>>>>>>>>>>>>>>>>>>> That's all
```

Imaginons maintenant que cette remise ne s'applique qu'aux individus remplissant les 2 conditions,
il faut alors changer la condition

```
let age = input;
let taille = input;
if( (taille<150) and (age<16)) then 16 else 20;
output eax.
```

On tombe alors sur les résultats suivants:

```
//Je mesure 1m20 et ai 18 ans, je payerais donc 20€
>18
>120
>>>>20
>>>>>>>>>>>>>>>>>>>>>> That's all
//Je mesure 1m80 et ai 18 ans, je payerais donc 20€
>15
>180
>>>>20
//Je mesure 1m49 et ai 15 ans, je payerais donc 16€
>15
>149
>>>>16
>>>>>>>>>>>>>>>>>>>>>> That's all
```


##Utilisation de l'outil

Afin de pouvoir utilisé le compilateur, voici les consignes à suivre: <br>
**1-écrire le code:** dans un premier temps, saisisser le code à compiler dans le fichier "expression.txt" ou alors lancé directement le fichier main pour écrire le code dans le terminal <br>
**2-Compilation:** une fois écrit, vous pourrez trouver le code générer dans le fichier "fichierCompile.asm". <br>
**3-Execution:** vous pourrez enfine executer le code via l'utilisation de la machine à registre en vous placant dans le fichier **compiledFiles** <br>
et en rentrant la ligne suivante:

```
java -jar vm-0.9.jar fichierCompile.asm
```








