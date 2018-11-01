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
		this.x = x;
		this.y = y;
		this.aEteVisite = false;
	}
	
	public Sommet() {
		
	}
	
	/* Ajout d'une methode permettant de calculer la distance d'une ville vers une autre ville*/
	public double distance(Sommet dest)
	{
		return Math.sqrt((this.getx() - dest.getx()) * (this.getx() - dest.getx()) + (this.gety() - dest.gety()) * (this.gety() - dest.gety()));
	}
	
	public float getx() {
		return this.x;
	}
	
	
	public float gety() {
		return this.y;
	}
	
	public boolean getaEteVisite() {
		return this.aEteVisite;
		
	}
	
	public void setaEteVisite(boolean aEteVisite){
		this.aEteVisite = aEteVisite;
	}
}
