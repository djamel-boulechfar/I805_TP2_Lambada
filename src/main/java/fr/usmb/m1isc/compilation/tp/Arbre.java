package fr.usmb.m1isc.compilation.tp;

import org.w3c.dom.Node;

import java.util.ArrayList;

public class Arbre {

    // Attributs
    private NodeType type;
    private String value;
    private Arbre leftSon;
    private Arbre rightSon;
    // Constructeurs
    public Arbre() {}

    public Arbre(NodeType type, String value) {
        this.type = type;
        this.value = value;
        this.leftSon = null;
        this.rightSon = null;
    }

    public Arbre(NodeType type, String value, Arbre ls, Arbre rs) {
        this.type = type;
        this.value = value;
        this.leftSon = ls;
        this.rightSon = rs;
    }

    public Arbre(Arbre arbre){
        this.type = arbre.type;
        this.rightSon = arbre.rightSon;
        this.leftSon = arbre.leftSon;
        this.value = arbre.value;
    }

    // Méthodes
    @Override
    public String toString() {
        String res = "";

        //Si opération ou changement d'expression, on ouvre les parenthèses
        if (type == NodeType.PLUS || type == NodeType.MOD  || type == NodeType.SUB  || type == NodeType.MULT  || type == NodeType.DIV  || type == NodeType.LET || type == NodeType.SEMI || type == NodeType.GTE ||type == NodeType.GT || type == NodeType.AND ||type == NodeType.OR || type == NodeType.EGAL || type == NodeType.IF || type == NodeType.OUTPUT) {
            res += "(" + value;
        } else {
            res += " " + value;
        }
        //Iterations
        if (leftSon != null) {
            res += leftSon.toString();
        }
        if (rightSon != null) {
            res += rightSon.toString();
        }

        //Si opération ou changement d'expression, on ferme les parenthèses
        if (type == NodeType.PLUS || type == NodeType.MOD  || type == NodeType.SUB  || type == NodeType.MULT  || type == NodeType.DIV  || type == NodeType.LET ||type == NodeType.SEMI || type == NodeType.GTE ||type == NodeType.GT || type == NodeType.AND ||type == NodeType.OR || type == NodeType.EGAL || type == NodeType.IF || type == NodeType.OUTPUT) {
            res += ")";
        }
        return res;
    }

    public void afficheArbre() {
        if (type == NodeType.OPERATOR) {
            System.out.print("(" + value);
        } else {
            System.out.print(" " + value);
        }
        if (leftSon != null) {
            System.out.print("#ls");
            leftSon.afficheArbre();
        }
        if (rightSon != null) {
            System.out.print("#rs");
            rightSon.afficheArbre();
        }
        if (type == NodeType.OPERATOR) {
            System.out.print(")");
        }
    }

    public void test() {
        Arbre a = new Arbre(NodeType.OPERATOR, "*",
                new Arbre(NodeType.VARIABLE, "prixHt"),
                new Arbre(NodeType.INTEGER, "119"));
        a.afficheArbre();
    }

    public ArrayList<String> getVariables(ArrayList<String> variables) {
        if (this.type == NodeType.LET && !variables.contains(this.value)) {
            if (!variables.contains(this.leftSon.value)) {
                variables.add(this.leftSon.value);
            }
        }
        if (this.leftSon != null) {
            leftSon.getVariables(variables);
        }
        if (this.rightSon != null) {
            rightSon.getVariables(variables);
        }
        return variables;
    }

    public ArrayList<String> genereCode(ArrayList<String> lignes) {
        if (this.type == NodeType.SEMI) { // Gestion du point virgule
            if (this.leftSon != null) {
                this.leftSon.genereCode(lignes);
            }

            if (this.rightSon != null) {
                this.rightSon.genereCode(lignes);
            }
        }

        else if (this.type == NodeType.INTEGER || this.type == NodeType.IDENT) { // Gestion des entiers et des identifiants
            if (this.value.contains("-")) { // Gestion du moins unaire
                lignes.add("\tmov eax, 0");
                lignes.add("\tsub eax," + this.value.substring(1));
            } else {
                lignes.add("\tmov eax, " + this.value);
            }
        }

        else if (this.type == NodeType.LET) { // Gestion de la déclaration de variables
            if(this.rightSon.type == NodeType.INPUT){
                lignes.add("\tin eax");
            }else{
                this.rightSon.genereCode(lignes);
            }

            lignes.add("\tmov " + this.leftSon.value + ", eax");
        }

        else if (this.type == NodeType.IF) {
            /*if(this.leftSon.type == NodeType.AND){
                String etiq_fin_and = getNumEtiquette("etiq_fin_and",lignes);
                String etiq_sinon = getNumEtiquette("etiq_sinon", lignes);
                this.leftSon.genererCondition(etiq_fin_and,lignes);
                this.rightSon.leftSon.genereCode(lignes);
                String etiq_fin = getNumEtiquette("etiq_fin", lignes);
                lignes.add("\tjmp " + etiq_fin);
                lignes.add(etiq_sinon + " :");
                this.rightSon.rightSon.genereCode(lignes);
                lignes.add(etiq_fin + " :");
            } else{*/
                String etiq_sinon = getNumEtiquette("etiq_sinon", lignes);
                this.leftSon.genererCondition(etiq_sinon, lignes);
                this.rightSon.leftSon.genereCode(lignes);
                String etiq_fin = getNumEtiquette("etiq_fin", lignes);
                lignes.add("\tjmp " + etiq_fin);
                lignes.add(etiq_sinon + " :");
                this.rightSon.rightSon.genereCode(lignes);
                lignes.add(etiq_fin + " :");
            //}

        }

        else if (this.type == NodeType.OUTPUT) {
            lignes.add("\tmov eax, " + this.leftSon.value);
            lignes.add("\tout eax");

        }
        else if(this.type == NodeType.WHILE){

            //Generation des étiquettes
            String etiqDebWhile = getNumEtiquette("debut_while",lignes);
            lignes.add(etiqDebWhile +" : ");
            String etiqFinWhile = getNumEtiquette("fin_while",lignes);
            String etiqCondNotOK  = getNumEtiquette("etiq_cond_not_ok",lignes);
            String etiqActionWhile = getNumEtiquette("etiq_action_while",lignes);

            //Generation du code
            this.leftSon.genererCondition(etiqCondNotOK,lignes);
            lignes.add("\tmov eax, 1");
            lignes.add("\tjmp "+etiqActionWhile);
            lignes.add(etiqCondNotOK+" : ");
            lignes.add("\tmov eax, 0");
            lignes.add(etiqActionWhile+" : ");
            lignes.add("\tjz "+etiqFinWhile);
            this.rightSon.genereCode(lignes);
            lignes.add("\tjmp "+etiqDebWhile);
            lignes.add(etiqFinWhile+" : ");

        }

        else {
            // Evaluation et stockage des valeurs des arbres gauche et droit
            this.leftSon.genereCode(lignes);
            lignes.add("\tpush eax");
            this.rightSon.genereCode(lignes);
            lignes.add("\tpop ebx");

            if (this.type == NodeType.PLUS) { // Gestion de l'addition
                lignes.add("\tadd eax, ebx");
            }

            if (this.type == NodeType.SUB) { // Gestion de la soustraction
                lignes.add("\tsub ebx, eax");
                lignes.add("\tmov eax, ebx");
            }

            if (this.type == NodeType.MULT) { // Gestion de la multiplication
                lignes.add("\tmul eax, ebx");
            }

            if (this.type == NodeType.DIV) { // Gestion de la division
                lignes.add("\tdiv ebx, eax");
                lignes.add("\tmov eax, ebx");
            }

            if (this.type == NodeType.MOD) { // Gestion du modulo (recupération du nombre de divisions possibles puis soustraction pour obtenir le reste)
                lignes.add("\tmov ecx, ebx");
                lignes.add("\tdiv ebx, eax");
                lignes.add("\tmul eax, ebx");
                lignes.add("\tsub ecx, eax");
                lignes.add("\tmov eax, ecx");
            }
        }

        return lignes;
    }

    // Genere du code qui saute à l'étiquette donnée si la condition n'est pas satisfaite
    public void genererCondition (String etiquetteSaut, ArrayList<String> lignes) {
        if (this.type == NodeType.AND ) {
            this.leftSon.genererCondition(etiquetteSaut,lignes);
            this.rightSon.genererCondition(etiquetteSaut,lignes);
        }
        else if(this.type == NodeType.OR){

        }
        else {
            this.leftSon.genereCode(lignes);
            lignes.add("\tpush eax");
            this.rightSon.genereCode(lignes);
            lignes.add("\tpop ebx");
            lignes.add("\tsub eax, ebx");

            if (this.type == NodeType.GTE) {
                lignes.add("\tjl " + etiquetteSaut); // Si eax < ebx alors on saute au sinon
            }

            if (this.type == NodeType.GT) {
                lignes.add("\tjle " + etiquetteSaut); // Si eax < ebx alors on saute au sinon
            }

            if (this.type == NodeType.EGAL) {
                lignes.add("\tjnz " + etiquetteSaut); // Si eax - ebx != 0 on saute au sinon
            }
        }
    }

    public String getNumEtiquette(String etiquette, ArrayList<String> lignes) {
        int nbOccurences = 1; // Pour avoir des etiquettes qui commencent à 1
        String etiquetteFinale = etiquette + "_" + Integer.toString(nbOccurences);
        for (int i = 0; i < lignes.size(); i++) {
            String ligneCourante = lignes.get(i);
            if (ligneCourante.equals(etiquetteFinale + " :")) {
                nbOccurences++;
                etiquetteFinale = etiquette + "_" + Integer.toString(nbOccurences);
            }
        }
        return etiquetteFinale;
    }

}
