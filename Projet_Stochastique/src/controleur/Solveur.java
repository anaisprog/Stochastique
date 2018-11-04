package controleur;

import ilog.concert.IloException;
import modele.ProgrammeLineaire;
import vue.Interface;

public class Solveur {
	int algochoice; /*attribut permettant d'identifier quel algorithme*/
	int nature; /*type de probleme que l'utilisateur souhaite resoudre*/
	ProgrammeLineaire prog;
	
	/*Constructeur de la classe Solver prenant
	en parametre un objet ProgrammeLineaire*/
	public Solveur(int algochoice ,int nature, ProgrammeLineaire prog){
		this.algochoice = algochoice;
		this.nature = nature;
		this.prog = prog;
	}
	
	/*methode permettant d'executer la resolution du probleme
	avec l'algorithme choisi par l'utilisateur*/
	public void run() throws IloException {
		if(algochoice == 0){
			RecuitSimuleGenerique rsg = new RecuitSimuleGenerique(prog);
			rsg.run(nature);
		} else {
			MethodeIterative mi = new MethodeIterative(prog, nature);
			mi.run();
		}
	}
	
}
