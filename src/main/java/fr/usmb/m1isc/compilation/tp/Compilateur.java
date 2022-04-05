package fr.usmb.m1isc.compilation.tp;

import java.io.File;

public class Compilateur {
    private static String CHEMIN ="./src/main/compiledFiles/";

    public Compilateur(){
    }

    public void generateASM(){
        //Création de fichier
        try {
            // Recevoir le fichier
            File rep = new File(CHEMIN);
            File fichier =  new File(rep,"fichierCompile.asm");

            // Créer un nouveau fichier
            // Vérifier s'il n'existe pas
            if (fichier.createNewFile())
                System.out.println("File created");
            else
                System.out.println("File already exists");
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }
}
