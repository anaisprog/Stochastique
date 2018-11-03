package controleur;

import java.util.ArrayList;

import modele.Arc;
import modele.Graph;
import modele.ProgrammeLineaire;
import modele.Sommet;

public class RecuitSimuleDeterministe extends RecuitSimuleGenerique {

	public RecuitSimuleDeterministe() {
		
	}
	
	public RecuitSimuleDeterministe(int e, float t, int pallier, float coef, int meilleurCout) {
		super(e, t, pallier, coef, meilleurCout);
		
	}
	
	public void run(ProgrammeLineaire prog){
		int diff = 0;
		int i = 0;
		int newe = 0;
		int compteur = 0;
		float proba = 0;
		
		this.energie = super.generationSolutionInitiale(prog.getGraph()).cout();
		
		
		while(this.temperature >= 0.00005 && i < 5000){
			//TODO : finir m�thode voisinage
			Graph newsoluce = voisinage(super.generationSolutionInitiale(prog.getGraph()));
			newe = newsoluce.cout();
			diff = newe - this.energie;
			
			if(diff < 0){
				compteur++;
				if(compteur >= this.pallier){
					this.temperature = (this.temperature * this.coef);
					compteur = 0;
				}
					
				this.energie = newe;
				
				if(newe < meilleurCout)
					this.meilleurCout = newe;
				
			} else {
				 proba = (float) Math.exp(-diff/this.temperature);
					
				if(Math.random() < proba){
					compteur++;
					if(compteur >= this.pallier){
						this.temperature = (this.temperature * this.coef);
						compteur = 0;
					}
						
					this.energie = newe;
					if(newe < meilleurCout)
						this.meilleurCout = newe;
				}	
			}
		}
	}
	
	/*Cette fonction execute la m�thode de voisinage�ge , ​ par​ ​ défaut 2-opt*/
	public Graph voisinage(Graph graph) {
		Graph solution=graph;
		ArrayList<Arc> larcs = graph.getArcs();
	
		boolean ammelioration = true;
		double distance_i_ip1=0;
		double distance_j_jp1=0;
		double distance_i_j=0;
		double distance_ip1_jp1=0;
		ArrayList<Sommet> lsommets = solution.getSommets();
		while(ammelioration==true) {
			ammelioration=false;
			for(Sommet s:lsommets)
			{	
				Arc ai= solution.getArcbySommetD(s.getid());
				Arc aim = solution.getArcbySommetA(s.getid());
				for(Sommet so:lsommets) {
					if(so.getid()!=s.getid()) {
						Arc aj= solution.getArcbySommetD(so.getid());
						Arc ajm = solution.getArcbySommetA(so.getid());
						if((so.getid()!=ai.getSomA().getid()) && (aim.getSomD().getid())!=(so.getid())) {
							
						}
					}
					
				}
						
			
			}
		}
			
		return solution;
	}
	
}
