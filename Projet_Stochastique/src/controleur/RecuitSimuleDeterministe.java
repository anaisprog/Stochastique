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
		while(ammelioration==true) {
			ammelioration=false;
			for(int i=0; i<graph.getSommets().size(); i++) {
				for(int j=0; j<graph.getSommets().size(); j++) {
					if(i==0 ) { //On a pas de i-1
						if(i!=j && j!=i+1 ) { //On verifie les conditions de l'algorithme
							distance_i_ip1 = graph.getArcbyDA(i, i+1).getCout();
							distance_j_jp1 = graph.getArcbyDA(i, i+1).getCout();
							distance_i_j =  graph.getArcbyDA(i, j).getCout();
							distance_ip1_jp1 = graph.getArcbyDA(i+1, j+1).getCout();
						//	Si distance(xi, xi+1) + distance(xj, xj+1) > distance(xi, xj) + distance(xi+1, xj+1) alors
							
							if((distance_i_ip1 + distance_j_jp1 ) > (distance_i_j + distance_ip1_jp1)) {
								Sommet sip1 = solution.getSommetById(i+1);
								Sommet sj = solution.getSommetById(j);
								
								Arc a1 = solution.getArcbyDA(i, i+1);
								Arc a2 = solution.getArcbyDA(j, j+1);
								
								a1.setSomA(sj);
								a2.setSomD(sip1);
							}
								
							}
						}
						else {
							
							if(i!=j && j!=i+1 && j!=i-1) { //On verifie les conditions de l'algorithme
								distance_i_ip1 = graph.getArcbyDA(i, i+1).getCout();
								distance_j_jp1 = graph.getArcbyDA(i, i+1).getCout();
								distance_i_j =  graph.getArcbyDA(i, j).getCout();
								distance_ip1_jp1 = graph.getArcbyDA(i+1, j+1).getCout();
							
						}
							if((distance_i_ip1 + distance_j_jp1 ) > (distance_i_j + distance_ip1_jp1)) {
								Sommet sip1 = solution.getSommetById(i+1);
								Sommet sj = solution.getSommetById(j);
								
								Arc a1 = solution.getArcbyDA(i, i+1);
								Arc a2 = solution.getArcbyDA(j, j+1);
								
								a1.setSomA(sj);
								a2.setSomD(sip1);
							}
					}
					
					
				}
			}
		}
			
		return solution;
	}
	
}
