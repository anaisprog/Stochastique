package controleur;

import modele.ProgrammeLineaire;

public class Solveur {
	int algochoice; /*attribut permettant d'identifier quelle algorithme*/
	int nature; /*type de probl�me l’utilisateur souhaite r�soudre*/
	ProgrammeLineaire prog;
	
	
	/*Constructeur de la classe Solver prenant
	en paramètre un objet ProgrammeLineaire*/
	public Solveur(int algochoice ,int nature, ProgrammeLineaire prog){
		this.algochoice = algochoice;
		this.nature = nature;
		this.prog = prog;
	}
	
	/*m�thode permettant d'executer la r�solution du probl�me
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
