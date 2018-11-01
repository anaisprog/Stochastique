package modele;

public class Arc {
	private Sommet somD;
	private Sommet somA;
	
	private float moyenne;
	private float variance;
	
	public Arc(){
		
	}
	
	public Arc(Sommet somD, Sommet somA, float moyenne, float variance) {
		super();
		this.somD = somD;
		this.somA = somA;
		this.moyenne = moyenne;
		this.variance = variance;
	}
	
	/*Calcul la distance entre les deux sommets dans le cas ou elle n'est pas renseign� dans le fichier data*/
	public double calculDistance() {
		return Math.sqrt((this.somD.getx() - this.somA.getx()) * (this.somD.getx() - this.somA.gety()) + (this.somD.gety() - this.somA.gety()) * (this.somD.gety() - this.somA.gety()));
	}
	
	public Sommet getSomD() {
		return somD;
	}


	public void setSomD(Sommet somD) {
		this.somD = somD;
	}


	public Sommet getSomA() {
		return somA;
	}


	public void setSomA(Sommet somA) {
		this.somA = somA;
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
	
	
}
