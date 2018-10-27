package modele;

import java.util.ArrayList;

public class Cout {
	private float moyenne;
	private float variance;
	private ArrayList<Sommet> sesSommets; // Chaque coût est associé à deux sommets
	//Constructeur
	public Cout(float moyenne, float variance) {
	}
	public Cout() {
	}
	public float getMoyenne() {
		return moyenne;
	}
	public void setMoyenne(float moyenne) {
		this.moyenne = moyenne;
	}
	public float getVariance() {
		return variance;
	}
	public void setVariance(float variance) {
		this.variance = variance;
	}
	public ArrayList<Sommet> getSesSommets() {
		return sesSommets;
	}
	public void setSesSommets(ArrayList<Sommet> sesSommets) {
		this.sesSommets = sesSommets;
	}
	
}
