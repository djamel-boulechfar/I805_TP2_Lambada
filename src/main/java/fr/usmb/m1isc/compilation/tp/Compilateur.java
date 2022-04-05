package fr.usmb.m1isc.compilation.tp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Compilateur {
    private static String CHEMIN ="./src/main/compiledFiles/";

    public Compilateur(){
    }

    public void createASM(){
        //Création de fichier
        try {
            // Recevoir le fichier
            File rep = new File(CHEMIN);
            File fichier =  new File(rep,"fichierCompile.asm");
            // Créer un nouveau fichier
            // Vérifier s'il n'existe pas
            if (fichier.createNewFile()){
                BufferedWriter writer = new BufferedWriter(new FileWriter(fichier.getAbsolutePath()));
                writer.append("DATA SEGMENT \n");
                writer.append("DATA ENDS \n");
                writer.append("CODE SEGMENT \n");
                writer.append("CODE ENDS \n");
                writer.close();
                System.out.println("File created");
            } else {
                System.out.println("File already exists");
            }


        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    public void testASM(){
        //Création de fichier
        try {
            // Recevoir le fichier
            File rep = new File(CHEMIN);
            File fichier =  new File(rep,"compileTest.asm");
            // Créer un nouveau fichier
            // Vérifier s'il n'existe pas
            if (fichier.createNewFile()){
                BufferedWriter writer = new BufferedWriter(new FileWriter(fichier.getAbsolutePath()));
                writer.append("DATA SEGMENT \n");
                writer.append("prixHt DD \n");
                writer.append("prixTtc DD \n");
                writer.append("DATA ENDS \n");
                writer.append("CODE SEGMENT \n");
                writer.append("mov eax, 200 \n");
                writer.append("mov prixHt, eax \n");
                writer.append("mov eax, prixHt \n");
                writer.append("push eax \n");
                writer.append("mov eax, 119 \n");
                writer.append("pop ebx \n");
                writer.append("mul eax, ebx \n");
                writer.append("push eax \n");
                writer.append("mov eax, 100 \n");
                writer.append("pop ebx \n");
                writer.append("div ebx, eax \n");
                writer.append("mov eax, ebx \n");
                writer.append("mov prixTtc, eax \n");
                writer.append("out eax \n");
                writer.append("CODE ENDS \n");
                writer.close();
                System.out.println("File created");
            } else {
                System.out.println("File already exists");
            }


        }
        catch (Exception e) {
            System.err.println(e);
        }
    }
}
