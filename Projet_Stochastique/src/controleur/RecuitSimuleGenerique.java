package controleur;

import java.util.ArrayList;

import modele.Arc;
import modele.Graph;
import modele.ProgrammeLineaire;
import modele.Sommet;
/*
 * Djenna : je pense qu'on devrait rendre cette classe générique et y inclure les methodes suivantes :
 * 	- l'algorithime general du recuit
 * 	- refus de la probabilite de boltzman
 * 	ainsi que des methodes abstractes qui seront definies dans les cas stocha et deterministe
 * 	- calcul de la solution initiale
 * 	- calcule du voisin
 * 	- et autres choses au besoin : temperature ...
 * 
 * Ensuite il faudrait faire un recuit stochastique :
 * - methode de l'opt-2
 * 
 * Recuit deterministe :
 * 
 */
public class RecuitSimuleGenerique 
{
	protected ProgrammeLineaire probleme;
	protected int energie = 0;
	protected float temperature = 100f;
	protected int pallier = 1;
	protected float coef = 0.95f;
	protected int meilleurCout = 0;
	protected Graph solutionCourante;
	protected ProgrammeLineaire prog;
	
	public RecuitSimuleGenerique(ProgrammeLineaire prog) 
	{
		this.prog = prog;
	}


	public RecuitSimuleGenerique(ProgrammeLineaire prog, int e, float t, int pallier, float coef, int meilleurCout) 
	{
		this.energie = e;
		this.temperature = t;
		this.pallier = pallier;
		this.coef = coef;
		this.meilleurCout = meilleurCout;
		this.prog = prog;
	}
	
	public Graph generationSolutionInitiale(Graph g)
	{
		Graph graphSolution = new Graph();
		graphSolution.setSommets(g.getSommets());
		
		ArrayList<Sommet> lsommet = graphSolution.getSommets();
		
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


	/*Lance le calcul de la solution avec l'algorithme du recuit simul�*/
	public void run(int nature)
	{
		if(nature == 0){
			RecuitSimuleDeterministe rsd = new RecuitSimuleDeterministe(prog);
			rsd.run(nature);
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
