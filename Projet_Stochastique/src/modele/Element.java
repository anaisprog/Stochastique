package modele;

public class Element {
	int i; // num de ligne
	int j; // num de colonne
	double valeur; // valeur de l'element 
	
	public Element(int i, int j) {
		this.i = i;
		this.j = j;
		
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	public double getValeur() {
		return valeur;
	}

	
}
