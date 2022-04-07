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

    public void afficheArbre() {
        if (type == NodeType.OPERATOR) {
            System.out.print("(" + value);
        } else {
            System.out.print(" " + value);
        }
        if (leftSon != null) {
            leftSon.afficheArbre();
        }
        if (rightSon != null) {
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

    @Override
    public String toString() {
        String res = "";

        //Si opération ou changement d'expression, on ouvre les parenthèses
        if (type == NodeType.PLUS || type == NodeType.MOD  || type == NodeType.SUM || type == NodeType.MOD  ||type == NodeType.MULT  || type == NodeType.DIV  ||type == NodeType.LET || type == NodeType.SEMI || type == NodeType.GTE ||type == NodeType.GT || type == NodeType.AND ||type == NodeType.OR || type == NodeType.EGAL) {
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
        if (type == NodeType.OPERATOR || type == NodeType.LET ||type == NodeType.SEMI || type == NodeType.GTE ||type == NodeType.GT || type == NodeType.AND ||type == NodeType.OR || type == NodeType.EGAL) {
            res += ")";
        }
        return res;
    }

    public ArrayList<String> getVariables(ArrayList<String> variables) {
        if (this.type == NodeType.LET && !variables.contains(this.value)) {
            variables.add(this.leftSon.value);
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
        if (this.type == NodeType.SEMI) {
            if (this.leftSon != null) {
                this.leftSon.genereCode(lignes);
            }
            if (this.rightSon != null) {
                this.rightSon.genereCode(lignes);
            }
        }
        if (this.type == NodeType.PLUS) {
            //if (this.leftSon.type == NodeType.INTEGER && this.rightSon.type == NodeType.INTEGER) {
                lignes.add("mov eax, " + this.leftSon.value);
                lignes.add("add eax, " + this.rightSon.value);
            //}
        }
        if (this.type == NodeType.LET) {
            if (rightSon.type == NodeType.INTEGER) {
                lignes.add("mov eax, " + this.rightSon.value);
                lignes.add("mov " + this.leftSon.value + ", eax");
            }
        }






        /*if(this.type == NodeType.OPERATOR){

        }
        if (rightSon != null) {
            rightSon.genereCode(lignes);
        }*/
        return lignes;
    }

}
