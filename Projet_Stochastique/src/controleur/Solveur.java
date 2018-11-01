package controleur;

import modele.ProgrammeLineaire;

public class Solveur {
	int algochoice; /*attribut permettant d'identifier quelle algorithme*/
	int nature; /*type de probl�me l’utilisateur souhaite r�soudre*/
	ProgrammeLineaire prog;
	
	/*Constructeur de la classe Solver prenant
	en paramètre un objet ProgrammeLineaire*/
	public Solveur(int algochoice ,ProgrammeLineaire prog){
		this.algochoice = algochoice;
		this.prog = prog;
	}
	
	/*m�thode permettant d'executer la r�solution du probl�me
	avec l'algorithme choisis par l'utilisateur*/
	public void run() {
		if(algochoice == 0){
			RecuitSimuleGenerique rsg = new RecuitSimuleGenerique();
			rsg.run(prog, nature);
		} else {
			/*TODO : CPLEX cplex = new Cplex();
			 * cplex.run(prog, nature)
			 */
		}
	}
}
