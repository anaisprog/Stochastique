package controleur;

import modele.ProgrammeLineaire;

public class RecuitSimuleGenerique {
	float temperature;
	float energie;
	int palier;
	int meilleurCout;
	
	
	/*Constructeur de la classe RecuitSimuleGenerique*/
	public RecuitSimuleGenerique(float a, float b, int c, float d){
		
	}
	/*Lance le calcul de la solution avec l’algorithme du recuit simulé*/
	public void run(){
		
	}
	
	/*permet d’implémenter la mis à jour la température 
	 * recuit au fur et à mesure des itérations*/
	public void majtemperature(){
		
	}
	/* Cette fonction permet d’implémenter la méthode de
	 * voisinage​ ​ qui sera utilisé pour le recuit*/
	public void voisinage() {
		
	}
	
	/*Ensemble des Getters et Setters*/
	
	
	
	public float getTemperature() {
		return temperature;
	}
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}
	public float getEnergie() {
		return energie;
	}
	public void setEnergie(float energie) {
		this.energie = energie;
	}
	public int getPalier() {
		return palier;
	}
	public void setPalier(int palier) {
		this.palier = palier;
	}
	public int getMeilleurCout() {
		return meilleurCout;
	}
	public void setMeilleurCout(int meilleurCout) {
		this.meilleurCout = meilleurCout;
	}
	

}
