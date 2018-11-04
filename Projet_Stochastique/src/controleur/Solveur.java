package controleur;

import ilog.concert.IloException;
import modele.ProgrammeLineaire;
import vue.Interface;

public class Solveur {
	int algochoice; /*attribut permettant d'identifier quel algorithme*/
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
	public void run() throws IloException {
		if(algochoice == 0){
			RecuitSimuleGenerique rsg = new RecuitSimuleGenerique(prog);
			rsg.run(nature);
		} else {
			MethodeIterative mi = new MethodeIterative(prog, nature);
			mi.run();
		}
	}
	
	public void majInterface(String info){
		
	}
}
