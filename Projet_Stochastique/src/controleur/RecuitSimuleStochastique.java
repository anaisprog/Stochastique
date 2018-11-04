package controleur;

import modele.ProgrammeLineaire;

public class RecuitSimuleStochastique extends RecuitSimuleDeterministe {

	public RecuitSimuleStochastique(ProgrammeLineaire prog) {
		super(prog);
		
	}

	public RecuitSimuleStochastique(int e, float t, int pallier, float coef, int meilleurCout, ProgrammeLineaire prog) {
		super(e, t, pallier, coef, meilleurCout, prog);
		
	}
	
	public void run(ProgrammeLineaire prog){
		
	}
}
