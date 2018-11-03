package controleur;

import modele.Arc;
import modele.Graph;
import modele.ProgrammeLineaire;
import modele.Sommet;

public class RecuitSimuleGenerique {
	protected int energie = 0;
	protected float temperature = 1000f;
	protected int pallier = 1;
	protected float coef = 0.95f;
	protected int meilleurCout = 0;
	
	
	public RecuitSimuleGenerique() {
		
	}

	public RecuitSimuleGenerique(int e, float t, int pallier, float coef, int meilleurCout) {
		this.energie = e;
		this.temperature = t;
		this.pallier = pallier;
		this.coef = coef;
		this.meilleurCout = meilleurCout;
	}
	
	public Graph generationSolutionInitiale(Graph g)
	{
		Graph graphSolution=new Graph();
		//On boucle sur l'ensemble des sommets du graph initial
		for(int i=0; i<g.getArcs().size(); i++)
		{	
			Arc a=new Arc(g.getSommetById(i), g.getSommetById(i+1),0.d,0.f,0.f);
			graphSolution.addArc(a);
		}
		return graphSolution;	
	}
	

	/*Lance le calcul de la solution avec l'algorithme du recuit simul�*/
	public void run(ProgrammeLineaire prog, int nature){
		if(nature == 0){
			RecuitSimuleDeterministe rsd = new RecuitSimuleDeterministe();
			rsd.run(prog);
		} else {
			RecuitSimuleStochastique rss = new RecuitSimuleStochastique();
			//TODO : rss.run(prog);
		}
			
	}
	
	
	/*Ensemble des Getters et Setters*/
	public int getE() {
		return energie;
	}
	
	public void setE(int e) {
		this.energie = e;
	}
	
	public float getT() {
		return temperature;
	}
	
	public void setT(float t) {
		this.temperature = t;
	}
	
	public int getPallier() {
		return pallier;
	}
	
	public void setPallier(int pallier) {
		this.pallier = pallier;
	}
	
	public float getCoef() {
		return coef;
	}
	
	public void setCoef(float coef) {
		this.coef = coef;
	}
	
	public int getMeilleurCout() {
		return meilleurCout;
	}
	
	public void setMeilleurCout(int meilleurCout) {
		this.meilleurCout = meilleurCout;
	}
	

}
