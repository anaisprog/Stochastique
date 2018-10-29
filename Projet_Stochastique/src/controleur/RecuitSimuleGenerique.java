package controleur;

import modele.ProgrammeLineaire;

public class RecuitSimuleGenerique {
	private int energie = 0;
	private float temperature = 1000f;
	private int pallier = 1;
	private float coef = 0.95f;
	private int meilleurCout;
	
	
	public RecuitSimuleGenerique() {
		
	}

	public RecuitSimuleGenerique(int e, float t, int pallier, float coef, int meilleurCout) {
		this.energie = e;
		this.temperature = t;
		this.pallier = pallier;
		this.coef = coef;
		this.meilleurCout = meilleurCout;
	}

	/*Lance le calcul de la solution avec l'algorithme du recuit simul�*/
	public void run(ProgrammeLineaire prog){
		int diff = 0;
		int i = 0;
		while(this.temperature >= 0.00005 && i < 5000){
			//TODO : appliquer voisinage
			//voisinage(prog)
			
			//TODO : Calcul co�t du sc�nario avec les arcs
		}
	}
	
	/*permet d'impl�menter la mis � jour la temp�rature 
	 * recuit au fur et � mesure des it�rations*/
	public void majtemperature(){
		
	}
	/* Cette fonction permet d’implémenter la méthode de
	 * voisinage​ ​ qui sera utilisé pour le recuit*/
	public void voisinage(ProgrammeLineaire prog) {
		
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
