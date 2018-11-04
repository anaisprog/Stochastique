package controleur;

import java.util.ArrayList;

import modele.Arc;
import modele.Graph;
import modele.ProgrammeLineaire;
import modele.Sommet;
import vue.Interface;

public class RecuitSimuleDeterministe extends RecuitSimuleGenerique {

	public RecuitSimuleDeterministe(ProgrammeLineaire prog) {
		super(prog);
	}
	
	public RecuitSimuleDeterministe(ProgrammeLineaire pgm, int e, float t, int pallier, float coef, int meilleurCout) {
		super(pgm, e, t, pallier, coef, meilleurCout);
		
	}

	public void run()
	{
		String info;
		int diff = 0;
		int i = 0;
		int newe = 0;
		int compteur = 0;
		float proba = 0;
		
		int engen = 0;
		int pos = 0;
		
		/*Calcul de la solution initial*/
		Graph solutionactuelle;
		solutionactuelle = super.generationSolutionInitiale(prog.getGraph());
		
		this.energie = solutionactuelle.cout();
		this.meilleurCout = energie;
		
		info = "Température initial : " + this.temperature + " | coef : " + this.coef + " | energie : " + this.energie + " | cout de la solution initial : " + this.meilleurCout + "\n";
		Interface.majAffichage(info);
		
		/*Condition d'arret du recuit*/
		while(this.temperature >= 0.00005 && i < 5000){
			/*Calcul de la solution après voisinage*/
			Graph newsoluce = voisinage(solutionactuelle);
			newe = newsoluce.cout();
			diff = newe - this.energie;
			
			info = i + " | cout : " + this.energie + " | meilleurcout : " + this.meilleurCout + " | température : " + this.temperature
					+ " | pallier : " + this.pallier + " | coutvoisinage : " + newe;
			Interface.majAffichage(info);
			
			/*Si la différence entre le cout de la nouvelle solution et la solution actuelle est inférieure à 0*/
			if(diff < 0){
				info = "\tSolution après voisinage meilleur. Acceptée";
				Interface.majAffichage(info);
				
				/*On accepte la solution. On incrémente le compteur pour le pallier de temperature*/
				compteur++;
				/*Mis a jour de la temperature*/
				if(compteur >= this.pallier){
					this.temperature = (this.temperature * this.coef);
					compteur = 0;
				}
				
				solutionactuelle = newsoluce;
				this.energie = newe;
				
				if(newe < meilleurCout){
					this.meilleurCout = newe;
				}
				
			} else {
				info = "\tSolution après voisinage moins bonne. Calcul de la probabilité de gibbs boltzmann";
				Interface.majAffichage(info);
				
				engen++;
				/*Calcul de la probabilite de gibbs boltzmann*/
				proba = (float) Math.exp(-diff/this.temperature);
				
				/*Si l'on tire un chiffre inferieur à la proba, alors on accepte le changement*/
				if(Math.random() < proba){
					info = "\tAcceptée";
					pos++;
					compteur++;
					
					if(compteur >= this.pallier){
						this.temperature = (this.temperature * this.coef);
						compteur = 0;
					}
					
					solutionactuelle = newsoluce;
					this.energie = newe;
					
					if(newe < meilleurCout){
						this.meilleurCout = newe;
					}
				} else {
					info = "\tRefusée";
				}
				
				Interface.majAffichage(info);
			}
			
			i++;
		}
		System.out.println("Meilleur cout final : " + this.meilleurCout);
		info = "Meilleur cout final calculé : " + this.meilleurCout;
		Interface.majAffichage(info);
		
	}
	
	/*Cette fonction execute la methode de voisinage, par defaut 2-opt*/
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
							}
						}
					}
				}
			}
		}
		return solution;
	}
}
