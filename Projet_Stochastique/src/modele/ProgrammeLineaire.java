package modele;
import java.util.*;

public class ProgrammeLineaire {
	Matrice matriceContraintes; //matrice contenant les contraintes du problème
	ArrayList<Matrice> iterations; /* Contient les différentes étapes du
	système, depuis le système initial jusqu'au système résolu.*/
	
	int nbIterations; // Nombre d’itérations effectuées.
	double meilleureSolution; /*c’est la meilleure solution retenue après
	avoir lancé l’algorithme*/
	
	boolean maximisation; /*booléen permettant de savoir s’il s’agit d’un problème
	de maximisation ou de minimisation*/
	ArrayList<Sommet> listeSommets; /*liste (ordonnée dans
le sens du parcours) des sommets visités*/
	
	
	
	/*Permet d’ajouter une contrainte au problème et donc une ligne de plus dans la matrice.*/
	public void addContraintes(ArrayList<Double> ligne) {
		
	}
	/*Permet d’ajouter la fonction objectif en prenant les contraintes*/
	public void addFctObj(ArrayList<Double> ligne) {
		
	}
	
	/* Retourne la solution du système dans une ArrayList.*/
	public ArrayList<Double> getSolution() {
		return null;
		
	}
	
	
	public void addIteration() {
	}
	

	public int getColonnes() {
		return 2;
	}
	public double getMeilleureSolution() {
		return meilleureSolution;
	}
	public void setMeilleureSolution(double meilleureSolution) {
		this.meilleureSolution = meilleureSolution;
	}
	public ArrayList<Sommet> getListeSommets() {
		return listeSommets;
	}
	public void setListeSommets(ArrayList<Sommet> listeSommets) {
		this.listeSommets = listeSommets;
	}
	
	
	
}
