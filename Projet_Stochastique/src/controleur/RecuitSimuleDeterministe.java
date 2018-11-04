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
	
	public RecuitSimuleDeterministe(ProgrammeLineaire pgm, int e, float t, int pallier, float coef, int meilleurCout) {
		super(pgm, e, t, pallier, coef, meilleurCout);
		
	}
	
	public void run(ProgrammeLineaire prog)
	{
		int diff = 0;
		int i = 0;
		int newe = 0;
		int compteur = 0;
		float proba = 0;
		Graph solutionactuelle;
		solutionactuelle = super.generationSolutionInitiale(prog.getGraph());
		
		this.energie = solutionactuelle.cout();
		this.meilleurCout = energie;
		
		
		while(this.temperature >= 0.00005 && i < 5000){
			
			//TODO : finir methode voisinage
			Graph newsoluce = voisinage(solutionactuelle);
			newe = newsoluce.cout();
			diff = newe - this.energie;

			if(diff < 0){
				compteur++;
				if(compteur >= this.pallier){
					this.temperature = (this.temperature * this.coef);
					compteur = 0;
				}
				
				solutionactuelle = newsoluce;
				this.energie = newe;
				
				if(newe < meilleurCout){
					this.meilleurCout = newe;
					System.out.println("new meilleur cout : " + this.meilleurCout);
				}
				
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
					if(newe < meilleurCout){
						this.meilleurCout = newe;
						System.out.println("new meilleur cout : " + this.meilleurCout);
					}
				}	
			}
			i++;
		}
		
		System.out.println("MEILLEUR COUT FINAL : " + this.meilleurCout);
	}
	
	/*Cette fonction execute la methode de voisinage, ​ par​ ​ d�faut 2-opt*/
	public Graph voisinage(Graph graph) {
		
		Graph solution = new Graph();
		solution.setArcs(graph.getArcs());
		solution.setSommets(graph.getSommets());
		
		ArrayList<Arc> larcs = graph.getArcs();
		
		boolean amelioration = true;

		ArrayList<Sommet> lsommets = solution.getSommets();
		
		while(amelioration == true) {
			amelioration = false;
			for(Sommet i : lsommets){	
				Arc ai = solution.getArcbySommetD(i.getid());
				Arc aim = solution.getArcbySommetA(i.getid());
				
				for(Sommet j : lsommets) {
					/*Condition j != i*/
					if(j.getid() != i.getid()) {
						Arc aj = solution.getArcbySommetD(j.getid());
						
						/*Condition j != i+1 && j != i-1*/
						if((j.getid() != ai.getSomA().getid()) && (aim.getSomD().getid()) != (j.getid())) {
							
							Arc aij = super.prog.getGraph().getArcbyDA(i.getid(), j.getid());
							Arc aipjp = super.prog.getGraph().getArcbyDA(ai.getSomA().getid(), aj.getSomA().getid());
							
							double coutO = ai.getCout() + aj.getCout();
							double coutT = aij.getCout() + aipjp.getCout();
							
							if(coutO > coutT){
								
								
								int idip = ai.getSomA().getid();
								int soma = j.getid();
								ArrayList<Arc> larcinverse = new ArrayList<>();
								
								do {
								Arc a = solution.getArcbySommetA(soma);
								
								larcinverse.add(a);
								
								soma = a.getSomD().getid();
									
								} while(idip != soma);
								
								Sommet ip = ai.getSomA();
								
								solution.getArcbySommetD(i.getid()).setCout(aij.getCout());
								solution.getArcbySommetD(i.getid()).setSomA(j);
								
								solution.getArcbySommetD(j.getid()).setCout(aipjp.getCout());
								solution.getArcbySommetD(j.getid()).setSomD(ip);
						
								
								for(Arc a : larcinverse){
									Sommet dp = a.getSomA();
									Sommet ar = a.getSomD();
									
									a.setSomD(dp);
									a.setSomA(ar);
									
									a.setCout(prog.getGraph().getArcbyDA(a.getSomD().getid(), a.getSomA().getid()).getCout());
								}
								amelioration = true;
								
								/*System.out.println("AMELIORATION");
								for(Arc a : solution.getArcs()){
									System.out.println("Arc entre " + a.getSomD().getid() + " et " + a.getSomA().getid()
									+ " | cout = " + a.getCout());
									}
									System.out.println("--------------------------------------------------");*/
							}
						}
					}
				}
			}
		}
		return solution;
	}
}
