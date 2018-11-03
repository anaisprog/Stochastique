package modele;

public class Sommet {
	//Attributs membres
	//ID du sommet : chaque sommet aura un ID unique
	private static int ID=0; /*Cette variable sera attribuée a chaque sommet créée
	et incrémenté par la suite*/
	private int id; 
	//Coordonnée horizontale
	private float x;
	//Coordonnée verticale
	private float y;
	//Cette variable sera vraie si le sommet a été visité
	private boolean aEteVisite = false;
	
	public Sommet() {
		this.ID++;
		this.aEteVisite = false;
	}
	
	//Constructeur
	public Sommet(float x, float y) {
		this.ID++;
		this.x = x;
		this.y = y;
		this.aEteVisite = false;
	}
	
	public int getid(){
		return this.id;
	}
	
	public float getx() {
		return this.x;
	}
	
	
	public float gety() {
		return this.y;
	}
	

	public void setx(float x) {
		this.x = x;
	}

	public void sety(float y) {
		this.y = y;
	}

	public boolean getaEteVisite() {
		return this.aEteVisite;
		
	}
	
	public void setaEteVisite(boolean aEteVisite){
		this.aEteVisite = aEteVisite;
	}
}
