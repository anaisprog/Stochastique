package modele;

public class Sommet {
	//Attributs membres
	//ID du sommet : chaque sommet aura un ID unique
	private static int cmpt=1; /*Cette variable sera attribuee a chaque sommet creee
	et incremente par la suite*/
	private int id;
	//Coordonnee horizontale
	private float x;
	//Coordonnee verticale
	private float y;

	
	public Sommet() {
		this.id = cmpt++;
	}
	
	//Constructeur
	public Sommet(float x, float y) {
		this.id = cmpt++;
		this.x = x;
		this.y = y;
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
}
