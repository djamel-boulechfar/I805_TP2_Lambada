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
        if (type == NodeType.PLUS || type == NodeType.MOD  || type == NodeType.SUB  || type == NodeType.MULT  || type == NodeType.DIV  || type == NodeType.LET || type == NodeType.SEMI || type == NodeType.GTE ||type == NodeType.GT || type == NodeType.AND ||type == NodeType.OR || type == NodeType.EGAL) {
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
        if (type == NodeType.PLUS || type == NodeType.MOD  || type == NodeType.SUB  || type == NodeType.MULT  || type == NodeType.DIV  || type == NodeType.LET ||type == NodeType.SEMI || type == NodeType.GTE ||type == NodeType.GT || type == NodeType.AND ||type == NodeType.OR || type == NodeType.EGAL) {
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
        // Gestion du point virgule
        if (this.type == NodeType.SEMI) {
            if (this.leftSon != null) {
                this.leftSon.genereCode(lignes);
            }
            if (this.rightSon != null) {
                this.rightSon.genereCode(lignes);
            }
        } else if (this.type == NodeType.INTEGER || this.type == NodeType.IDENT) {
            String inter = "";
            if(this.value.contains("-")){
                lignes.add("mov eax, 0");
                lignes.add("sub eax,"+this.value.substring(1));
            }else{
                lignes.add("mov eax, " + this.value);
            }


        } else if (this.type == NodeType.LET) {
            this.rightSon.genereCode(lignes);
            lignes.add("mov "+this.leftSon.value +",eax");
        } else {
            leftSon.genereCode(lignes);
            lignes.add("push eax");
            rightSon.genereCode(lignes);
            lignes.add("pop ebx");
            if (this.type == NodeType.PLUS) {
                lignes.add("add eax, ebx");
            }
            if (this.type == NodeType.SUB) {
                lignes.add("sub ebx, eax");
                lignes.add("mov eax, ebx");
            }
            if (this.type == NodeType.MULT) {
                lignes.add("mul eax, ebx");
            }
            if (this.type == NodeType.DIV) {
                lignes.add("div ebx, eax");
                lignes.add("mov eax, ebx");
            }
            if(this.type == NodeType.MOD){
                lignes.add("mov ecx, ebx");
                lignes.add("div ebx, eax");
                lignes.add("mul eax, ebx");
                lignes.add("sub ecx, eax");
                lignes.add("mov eax, ecx");
            }
        }
        return lignes;
    }

}
