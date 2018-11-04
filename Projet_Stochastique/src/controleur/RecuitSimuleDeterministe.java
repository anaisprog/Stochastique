package controleur;

import java.util.ArrayList;

import modele.Arc;
import modele.Graph;
import modele.ProgrammeLineaire;
import modele.Sommet;

public class RecuitSimuleDeterministe extends RecuitSimuleGenerique {

	public RecuitSimuleDeterministe(ProgrammeLineaire prog) {
		super(prog);
	}
	
	public RecuitSimuleDeterministe(int e, float t, int pallier, float coef, int meilleurCout, ProgrammeLineaire prog) {
		super(e, t, pallier, coef, meilleurCout, prog);
		
	}
	
	public void run(){
		int diff = 0;
		int i = 0;
		int newe = 0;
		int compteur = 0;
		float proba = 0;
		Graph solutionactuelle = super.generationSolutionInitiale(prog.getGraph());
		
		this.energie = solutionactuelle.cout();
		
		System.out.println(energie);
		
		while(this.temperature >= 0.00005 && i < 5000){
			Graph newsoluce = voisinage(solutionactuelle);
			newe = newsoluce.cout();
			diff = newe - this.energie;
			/*System.out.println(energie);
			System.out.println(newe);*/
			
			if(diff < 0){
				compteur++;
				if(compteur >= this.pallier){
					this.temperature = (this.temperature * this.coef);
					compteur = 0;
				}
				
				solutionactuelle = newsoluce;
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
					
					solutionactuelle = newsoluce;
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
	
		boolean amelioration = true;
		double distance_i_ip1=0;
		double distance_j_jp1=0;
		double distance_i_j=0;
		double distance_ip1_jp1=0;
		while(amelioration==true) {
			amelioration=false;
			for(int i=1; i<= graph.getSommets().size(); i++) {
				for(int j=1; j<= graph.getSommets().size(); j++) {
					if(i==1) { //On a pas de i-1
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
								a1.setCout(prog.getGraph().getArcbyDA(a1.getSomD().getid(), a1.getSomA().getid()).getCout());
								a2.setSomD(sip1);
								a2.setCout(prog.getGraph().getArcbyDA(a2.getSomD().getid(), a2.getSomA().getid()).getCout());
							}
								
							}
						}
						/*else {
							
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
					}*/
					
					
				}
			}
		}
			
		return solution;
	}
	
}
