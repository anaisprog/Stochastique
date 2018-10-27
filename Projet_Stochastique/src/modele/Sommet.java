package modele;

public class Sommet {
	//Attributs membres
	//ID du sommet : chaque sommet aura un ID unique
	private static int ID=1; /*Cette variable sera attribuée a chaque sommet créée
	et incrémenté par la suite*/
	private int id;
	//Coordonnée horizontale
	private float x;
	//Coordonnée verticale
	private float y;
	//Cette variable sera vraie si le sommet a été visité
	private boolean aEteVisite = false;
	//Constructeur
	public Sommet(float x, float y) {
		
	}
	public Sommet() {
		
	}
	public float getx() {
		return x;
	}
	public float gety() {
		return y;
	}
	public boolean getaEteVisite() {
		return aEteVisite;
		
	}
	public Cout getCout() {
		return null;
	}
	public float distanceTo(Sommet s) {
		return 0;
	}

}
