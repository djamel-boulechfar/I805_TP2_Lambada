package fr.usmb.m1isc.compilation.tp;

import java_cup.runtime.Symbol;

import java.io.FileReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception  {
	LexicalAnalyzer yy;
	if (args.length > 0)
	    yy = new LexicalAnalyzer(new FileReader(args[0])) ;
	else
	    yy = new LexicalAnalyzer(new InputStreamReader(System.in)) ;
	@SuppressWarnings("deprecation")
	parser p = new parser (yy);
	//p.parse();
		Symbol s = p.parse();
		Arbre arbre = (Arbre) s.value;
		(new Compilateur()).generateASM(arbre);
		System.out.println(arbre.toString());
    }
}
