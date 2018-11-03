package controleur;

import java.util.ArrayList;

import ilog.concert.*;
import ilog.cplex.*;
import modele.Arc;
import modele.Graph;
import modele.ProgrammeLineaire;
import modele.Sommet;

public class Cplex {
	private IloCplex model;
	private IloLinearNumExpr fct;
	private IloNumVar[][] var;
	private double[][] cout;
	
	public Cplex() {
		try {
			this.model = new IloCplex();
		} catch (IloException e) {
			e.printStackTrace();
		}
	}

	public void run(ProgrammeLineaire prog, int nature){
		if(nature == 0){
			try {
				
				ArrayList<Sommet> lsommet = prog.getGraph().getSommets();
				this.var = new IloNumVar[lsommet.size()][lsommet.size()];
				this.cout = new double[lsommet.size()][lsommet.size()];
				
				/*Ajout des variables des chemins au model*/
				for(int i = 1; i <= lsommet.size(); i++){
					for (int j = 1; j <= lsommet.size(); j++) {
	                    if (i != j) {
	                    	var[i][j] = model.boolVar("x." + i + "." + j);
	                    }
					}
				}
		        
				/*Création de la fonction objectif et ajout au model*/
		        this.fct = model.linearNumExpr();
	            for (int i = 1; i <= lsommet.size(); i++) {
	                for (int j = 1; j <= lsommet.size(); j++) {
	                    if (i != j) {
	                        this.fct.addTerm(cout[i][j], var[i][j]);
	                    }
	                }
	            }
	            model.addMinimize(fct);
				
	            /*Ajout de la première contrainte de parcour*/
	            for (int j = 1; j <= lsommet.size(); j++) {
	                IloLinearNumExpr expr = model.linearNumExpr();
	                for (int i = 1; i <= lsommet.size(); i++) {
	                    if (i != j) {
	                        expr.addTerm(1, var[i][j]);
	                    }
	                }
	                model.addEq(expr, 1);
	            }
	            
	            /*Ajout de la seconde contrainte de parcour*/
	            for (int i = 1; i <= lsommet.size(); i++) {
	                IloLinearNumExpr expr = model.linearNumExpr();
	                for (int j = 1; j <= lsommet.size(); j++) {
	                    if (j != i) {
	                        expr.addTerm(1, var[i][j]);
	                    }
	                }
	                model.addEq(expr, 1);
	            }
	            
	           
				if(model.solve()){
					Graph newsoluce = new Graph();
					for (int i = 1; i <= lsommet.size(); i++){
						for(int j = 1; j <= lsommet.size(); j++){
							if(i != j){
								if(model.getValue(var[i][j]) == 1){
									Sommet si = prog.getGraph().getSommetById(i);
									Sommet sj = prog.getGraph().getSommetById(j);
									Arc a = new Arc();
								}
							}
						}
					}
				}
				
				
				 /*Contrainte de sous tours*/
	            /*TODO :
	             * Récupérer le IloNumVar contenant tout les chemins et recrée un Graph avec les arcs
	             * Ensuite partir de la première ville et parcourir les arcs jusqu'a retombé sur cette dernière.
	             * Si lorsqu'on retombe sur elle, on à parcouru le nombres de villes contenu dans le problème, 
	             * on a un chemin hamiltonien sinon on a un sous tours et on ajoute la contrainte à CPLEX
	             * avec toutes les villes parcourus.
	             */
				
			} catch (IloException e) {
				e.printStackTrace();
			}
		} else {
			/* TODO :
			 * CplexStocha cs = new CplexStocha();
			 * cs.run(prog);
			 */
		}
	} 
}
