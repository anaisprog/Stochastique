package controleur;

import modele.ProgrammeLineaire;

public class RecuitSimuleStochastique extends RecuitSimuleDeterministe {

	public RecuitSimuleStochastique() {
		super();
		
	}

	public RecuitSimuleStochastique(ProgrammeLineaire pgm, int e, float t, int pallier, float coef, int meilleurCout) {
		super(pgm, e, t, pallier, coef, meilleurCout);
		
	}
	
	public void run(ProgrammeLineaire prog){
		
	}
}
