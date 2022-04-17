# TP Compilation : Génération d'arbres abstraits

## Rendu de TP de BOULECHFAR Djameleddine et JUBARD Théo

L'objectif du TP est d'utiliser les outils JFlex et CUP pour générer des arbres abstraits correspondant à un sous ensemble du langage **λ-ada**.

## TP1

Afin de répondre à la problématique posée, une classe Arbre à été créée avec les attributs suivants:
<br>
- **type** : type abstrait permettant d'identifier le type du noeud concerné (entier, opérateur d'addition, de soustraction, boucle while ...)
<br>
- **value**: la valeur correspondant au noeud. Celle-ci est une chaîne de caractères en correspondance avec le type
<br>
- **leftSon**: un arbre correspondant une partie du code 
<br>
- **rightSon** un arbre correspondant une partie du code
<br>
**/!\Attention: La valeur de leftSon et rightSon est dictée par le type de l'arbre, ainsi le fils gauche et droit n'ont pas forcément le même rôle.**
<br>
**Par exemple, si l'arbre est de type identificateur, alors le fils gauche portera le nom de la variable tandis que l'arbre droit portera la valeur**

### Exercice 1 et 2 :

Après complétion de la grammaire, un code complet correspondant au langage **λ-ada** peut être écrit et transposé sous forme d'arbre.
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

Correspond à l'arbre suivant une fois affiché :

```
(;(+ 12 5)(;(-(/ 10 2) 3)(; 99(;(mod(+(* 30 1) 4) 2)(* 3 - 4)))))
```

## TP2 et 3 

Une fois l'arbre construit, la génération de code se fait via la classe **Compilateur**. <br>
Cette classe permet l'accès à 2 méthodes :
<br>
- **generateASM**: prenant un arbre en paramètre, cette méthode permet de générer un tableau de String. Pour chacune des lignes du tableau, on retrouve une action effectué en assembleur. <br>
Cette méthode récursive prend aussi en paramètre le tableau contenant les lignes de code déjà traitées dans l'arbre. <br>
Celui-ci est parcouru une première fois afin de connaître les variables puis une seconde fois afin de générer le code des opérations. <br>
- **testASM**: méthode générant du code dans le fichier "compileTest.asm" un code générique  de test.
<br>

Après génération de l'arbre, l'appel à la méthode **generateASM** va permettre d'obtenir un fichier "fichierCompile.asm"
executable via l'utilisation de la machine à registre.

Pour décrire un peu mieux la méthode **generateASM**, celle-ci fait appel à la méthode **genererCode**
qui va, en fonction du type de chaque noeud de l'arbre, générer le code assembleur correspondant.<br>

Afin de gérer correctement les comparaisons et l'utilsiation des boucles conditionnelles,
plusieurs méthodes ont été utilisées: <br> 
- **getNumEtiquette**: méthode permettant de créer une "étiquette" soit un point de passage pour l'utilisation de conditions et ainsi 
passer aux codes correspondant à la validation de la condition ou non. <br>
- **genererCondition** et **genererConditionOu**: ces méthodes permettent de générer le code correspondant au différentes opérations conditionnelles et ainsi de sauter à l'étiquette satisfaisant (ou non) la condition. <br>

**/!\\ Remarque /!\\**
<br>
Notre compilateur est capable de gérer des conditions composées de plusieurs *AND* et de plusieurs *OR* mais n'est pas capable de gérer des conditions composées à la fois de *AND* et de *OR*.
<br>
Cela vient du fait que pour la condition *AND* le compilateur saute à l'étiquette *sinon* dès qu'il croise une condition non satisfaite, alors que pour la condition *OR*, le compilateur saute à l'étiquette contenant le code à exécuter dès qu'il croise une condition satisfaite.
<br>
De ce fait, nous n'avons pas réussi à faire un mélange de ces deux types de conditions.
<br>
Utilisées séparement, aucun problème à signaler.

**Fonctionnalités non implémentées :**
<br>
Nous n'avons pas implémenté la gestion du *NOT* et du *NIL*.
<br>
Ces éléments sont reconnus par la grammaire et l'arbre est bien construit avec, mais ils ne sont pas gérés par le générateur de code.

## Exemples

### PGCD
Le code suivant (exemple du PGCD):
```
let a = input;
let b = input;
while (0 < b)
do (let aux=(a mod b); let a=b; let b=aux );
output a
.
```
Renvoie le code assembleur suivant :
```
DATA SEGMENT 
	 a DD
	 b DD
	 aux DD
DATA ENDS 
CODE SEGMENT 
	in eax
	mov a, eax
	in eax
	mov b, eax
debut_while_1 : 
	mov eax, 0
	push eax
	mov eax, b
	pop ebx
	sub eax, ebx
	jle etiq_cond_not_ok_1
	mov eax, 1
	jmp etiq_action_while_1
etiq_cond_not_ok_1 : 
	mov eax, 0
etiq_action_while_1 : 
	jz fin_while_1
	mov eax, a
	push eax
	mov eax, b
	pop ebx
	mov ecx, ebx
	div ebx, eax
	mul eax, ebx
	sub ecx, eax
	mov eax, ecx
	mov aux, eax
	mov eax, b
	mov a, eax
	mov eax, aux
	mov b, eax
	jmp debut_while_1
fin_while_1 : 
	mov eax, a
	out eax
CODE ENDS 
```
Qui lorsqu'il est exécuté, permet d'obtenir les résultats suivants :

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
Ce qui correspond bien aux résultats attendus !

### Nombre le plus grand

Un autre exemple de code pourrait être la comparaison entre 2 nombres afin de savoir lequel des 2 est le plus grand :
```
let a = input;
let b = input;
if(a<b) then b else a;
output eax.
```
Voici le code assembleur obtenu :
```
DATA SEGMENT 
	 a DD
	 b DD
DATA ENDS 
CODE SEGMENT 
	in eax
	mov a, eax
	in eax
	mov b, eax
	mov eax, a
	push eax
	mov eax, b
	pop ebx
	sub eax, ebx
	jle etiq_sinon_1
	mov eax, b
	jmp etiq_fin_1
etiq_cond_ok_1 : 
	mov eax, b
	jmp etiq_fin_1
etiq_sinon_1 :
	mov eax, a
etiq_fin_1 : 
	mov eax, eax
	out eax
CODE ENDS 
```
On retrouve alors les résultats suivants :

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

### Calcul de prix
On pourrait aussi imaginer un exemple plus complexe dans lequel on souhaite appliquer des tarifs diffèrents selon l'âge et la taille du client.
L'entrée est ainsi fixée à 15€ pour les moins de 16 ans et les moins de 1m50. Si aucune condition n'est satisfaite, le prix est de 20€.

On utilise le code suivant:

```
let age = input;
let taille = input;
if( (taille<150) or (age<16)) then 16 else 20;
output eax.
```
Le code assembleur suivant est ainsi généré :
```
DATA SEGMENT 
	 age DD
	 taille DD
DATA ENDS 
CODE SEGMENT 
	in eax
	mov age, eax
	in eax
	mov taille, eax
	mov eax, taille
	push eax
	mov eax, 150
	pop ebx
	sub eax, ebx
	jg etiq_cond_ok_1
	mov eax, age
	push eax
	mov eax, 16
	pop ebx
	sub eax, ebx
	jg etiq_cond_ok_1
	jmp etiq_sinon_1
	mov eax, 16
	jmp etiq_fin_1
etiq_cond_ok_1 : 
	mov eax, 16
	jmp etiq_fin_1
etiq_sinon_1 :
	mov eax, 20
etiq_fin_1 : 
	mov eax, eax
	out eax
CODE ENDS 
```
On tombe alors sur les résultats suivants :
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
il faut alors changer la condition.

```
let age = input;
let taille = input;
if( (taille<150) and (age<16)) then 16 else 20;
output eax.
```
Le nouveau code assembleur est donc le suivant :
```
DATA SEGMENT 
	 age DD
	 taille DD
DATA ENDS 
CODE SEGMENT 
	in eax
	mov age, eax
	in eax
	mov taille, eax
	mov eax, taille
	push eax
	mov eax, 150
	pop ebx
	sub eax, ebx
	jle etiq_sinon_1
	mov eax, age
	push eax
	mov eax, 16
	pop ebx
	sub eax, ebx
	jle etiq_sinon_1
	mov eax, 16
	jmp etiq_fin_1
etiq_cond_ok_1 : 
	mov eax, 16
	jmp etiq_fin_1
etiq_sinon_1 :
	mov eax, 20
etiq_fin_1 : 
	mov eax, eax
	out eax
CODE ENDS 
```
On tombe alors sur les résultats suivants :

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


## Utilisation de l'outil

Afin de pouvoir utiliser le compilateur, voici les consignes à suivre :
<br>
- **1 - Ecrire le code :** dans un premier temps, saisissez le code à compiler dans le fichier "expression.txt" ou alors lancez directement le fichier main pour écrire le code dans le terminal <br>
- **2 - Compilation :** une fois écrit, vous pourrez trouver le code généré dans le fichier "fichierCompile.asm".<br>
- **3 - Execution:** vous pourrez enfin executer le code via l'utilisation de la machine à registres en vous plaçant dans le dossier ***/src/main/compiledFiles*** (à partir de la racine du projet) et en executant la commande suivante:

```
java -jar vm-0.9.jar fichierCompile.asm
```








