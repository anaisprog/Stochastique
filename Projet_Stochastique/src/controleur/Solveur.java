package controleur;

import modele.ProgrammeLineaire;

public class Solveur {
	int algochoice; /*attribut permettant d'identifier quelle algorithme*/
	int nature; /*type de problème lâ€™utilisateur souhaite résoudre*/
	ProgrammeLineaire prog;
	
	
	/*Constructeur de la classe Solver prenant
	en paramÃ¨tre un objet ProgrammeLineaire*/
	public Solveur(int algochoice ,int nature, ProgrammeLineaire prog){
		this.algochoice = algochoice;
		this.nature = nature;
		this.prog = prog;
	}
	
	/*méthode permettant d'executer la résolution du problème
	avec l'algorithme choisis par l'utilisateur*/
	public void run() {
		if(algochoice == 0){
			RecuitSimuleGenerique rsg = new RecuitSimuleGenerique(prog);
			rsg.run(nature);
		} else {
			MethodeIterative mi = new MethodeIterative(prog, nature);
			mi.run();
		}
	}
}
