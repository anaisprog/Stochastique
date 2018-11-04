package controleur;

import java.util.ArrayList;

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
	protected ProgrammeLineaire prog;
	
	public RecuitSimuleGenerique(ProgrammeLineaire prog) {
		this.prog = prog;
	}
	
	public RecuitSimuleGenerique(int e, float t, int pallier, float coef, int meilleurCout, ProgrammeLineaire prog) {
		this.energie = e;
		this.temperature = t;
		this.pallier = pallier;
		this.coef = coef;
		this.meilleurCout = meilleurCout;
		this.prog = prog;
	}
	
	
	// Fonction d'obtention d'un cycle initial
	
	
	public Graph generationSolutionInitiale(Graph g)
	{
		Graph graphSolution=new Graph();
		ArrayList<Sommet> lsommet = g.getSommets();
		
		for(Sommet s : lsommet) {
			Sommet sa;
			if(s.getid() == lsommet.size()) {
				sa = g.getSommetById(1);
			} else {
				sa = g.getSommetById(s.getid()+1);
			}
			
			double cout = g.getArcbyDA(s.getid(), sa.getid()).getCout();
			Arc a = new Arc(s, sa, cout, 0, 0);
			
			graphSolution.addArc(a);
		}
		/*//On boucle sur l'ensemble des sommets du graph initial
		for(int i=1; i<=g.getSommets().size(); i++)
		{	
			
			a=new Arc(g.getSommetById(i), g.getSommetById(i+1),g.getArcbyDA(i, i+1).getCout(),0.f,0.f);
			
			graphSolution.addArc(a);
		}*/
		return graphSolution;	
	}


	/*Lance le calcul de la solution avec l'algorithme du recuit simulé*/
	public void run(int nature){
		if(nature == 0){
			RecuitSimuleDeterministe rsd = new RecuitSimuleDeterministe(prog);
			rsd.run();
		} else {
			RecuitSimuleStochastique rss = new RecuitSimuleStochastique(prog);
			//TODO : rss.run();
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
