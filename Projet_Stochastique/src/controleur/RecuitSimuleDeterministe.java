package controleur;

import java.util.ArrayList;

import modele.Arc;
import modele.ProgrammeLineaire;

public class RecuitSimuleDeterministe extends RecuitSimuleGenerique {

	public RecuitSimuleDeterministe() {
		
	}
	
	public RecuitSimuleDeterministe(int e, float t, int pallier, float coef, int meilleurCout) {
		super(e, t, pallier, coef, meilleurCout);
		
	}
	
	public void run(ProgrammeLineaire prog){
		int diff = 0;
		int i = 0;
		int newe = 0;
		int compteur = 0;
		float proba = 0;
		
		this.energie = prog.cout();
		
		
		while(this.temperature >= 0.00005 && i < 5000){
			//TODO : finir méthode voisinage
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
	
	/*Cette fonction execute la méthode de voisinageâge , â€‹ parâ€‹ â€‹ dÃ©faut 2-opt*/
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
}
