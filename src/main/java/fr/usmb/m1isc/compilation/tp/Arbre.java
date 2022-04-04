package fr.usmb.m1isc.compilation.tp;

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
        if (type == NodeType.OPERATOR || type == NodeType.SEMI || type == NodeType.GTE ||type == NodeType.GT || type == NodeType.AND ||type == NodeType.OR || type == NodeType.EGAL) {
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
        if (type == NodeType.OPERATOR || type == NodeType.SEMI || type == NodeType.GTE ||type == NodeType.GT || type == NodeType.AND ||type == NodeType.OR || type == NodeType.EGAL) {
            res += ")";
        }
        return res;
    }

}
