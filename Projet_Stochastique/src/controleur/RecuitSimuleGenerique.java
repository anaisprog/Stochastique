package controleur;

import java.util.ArrayList;

import modele.Arc;
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
		int newe = 0;
		int compteur = 0;
		float proba = 0;
		
		this.energie = prog.cout();
		
		
		while(this.temperature >= 0.00005 && i < 5000){
			//TODO : finir m�thode voisinage
			ProgrammeLineaire newprog = voisinage(prog);
			newe = newprog.cout();
			
			diff = newe - this.energie;
			
			if(diff < 0){
				compteur++;
				if(compteur >= this.pallier){
					this.temperature = (this.temperature * this.coef);
					compteur = 0;
				}
					
				this.energie = newe;
				
				if(newe < meilleurCout)
					this.meilleurCout = newe;
				
			} else {
				 proba = (float) Math.exp(-diff/this.temperature);
					
				if(Math.random() < proba){
					compteur++;
					if(compteur >= this.pallier){
						this.temperature = (this.temperature * this.coef);
						compteur = 0;
					}
						
					this.energie = newe;
					if(newe < meilleurCout)
						this.meilleurCout = newe;
				}	
			}
		}
	}
	
	/* Cette fonction permet d’implémenter la méthode de
	 * voisinage​ ​ qui sera utilisé pour le recuit*/
	public ProgrammeLineaire voisinage(ProgrammeLineaire prog) {
		ProgrammeLineaire newprog = prog;
		ArrayList<Arc> larcs = prog.getGraph().getArcs();
		
		int randD = 0;
		int randF = 0;
		int b = 0;
		int r = 0;
		boolean cond = false;
		
		while(!cond){
			randD = (int)(Math.random() * ((larcs.size() - 1) + 1));
			
			do {
                randF = (int)(Math.random() * ((larcs.size() - 1) + 1));
			} while(randD == randF || Math.abs(randD - randF) > 10);
			
			if(randD > randF){
                int temp = randD;
                randD = randF;
                randD = temp;
			}
			
			/*TODO :for(int i = randD ; i < randF ; i++){
				b = (Math.random() < 0.6) ? 0 : 1;
                r = (int) (Math.random() * (4) + 1);
                
                if(b == 1){  
                    larcs.get(i).setX(larcs.get(i).getX()+1);
    			} else {
                    stations.get(i).setX(stations.get(i).getX()-1);
    			}
			}*/
		}
		return newprog;
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
