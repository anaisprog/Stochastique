package controleur;

import java.util.ArrayList;

import ilog.concert.*;
import ilog.cplex.*;
import modele.Arc;
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

	private void run(ProgrammeLineaire prog){
		try {
			
			ArrayList<Sommet> lsommet = prog.getGraph().getSommets();
			this.var = new IloNumVar[lsommet.size()][lsommet.size()];
			this.cout = new double[lsommet.size()][lsommet.size()];
			
			/*Calcul des couts des arcs entre chaque sommet*/
			for (int i = 1; i < lsommet.size(); i++) {
	            for (int j = 1; j < lsommet.size(); j++) {
	            	Arc a = new Arc();
	            	a.setSomD(lsommet.get(i));
	            	a.setSomA(lsommet.get(j));
	                this.cout[i][j] = a.calculDistance();
	            }
	        }
			
			/*Ajout des variables des chemins au model*/
			for(int i = 0; i < lsommet.size(); i++){
				for (int j = 1; j < lsommet.size(); j++) {
                    if (i != j) {
                    	var[i][j] = model.boolVar("x." + i + "." + j);
                    }
				}
			}
	        
	            
			/*Création de la fonction objectif et ajout au model*/
	        this.fct = model.linearNumExpr();
            for (int i = 1; i < lsommet.size(); i++) {
                for (int j = 1; j < lsommet.size(); j++) {
                    if (i != j) {
                        this.fct.addTerm(cout[i][j], var[i][j]);
                    }
                }
            }
            model.addMinimize(fct);
			
            /*Ajout de la première contrainte de parcour*/
            for (int j = 1; j < lsommet.size(); j++) {
                IloLinearNumExpr expr = model.linearNumExpr();
                for (int i = 1; i < lsommet.size(); i++) {
                    if (i != j) {
                        expr.addTerm(1, var[i][j]);
                    }
                }
                model.addEq(expr, 1);
            }
            
            /*Ajout de la seconde contrainte de parcour*/
            for (int i = 1; i < lsommet.size(); i++) {
                IloLinearNumExpr expr = model.linearNumExpr();
                for (int j = 1; j < lsommet.size(); j++) {
                    if (j != i) {
                        expr.addTerm(1, var[i][j]);
                    }
                }
                model.addEq(expr, 1);
            }
            
            /*Contrainte de sous tours*/
            /*TODO :
             * Récupérer le IloNumVar contenant tout les chemins et recrée un Graph avec les arcs
             * Ensuite partir de la première ville et parcourir les arcs jusqu'a retombé sur cette dernière.
             * Si lorsqu'on retombe sur elle, on à parcouru le nombres de villes contenu dans le problème, 
             * on a un chemin hamiltonien sinon on a un sous tours et on ajoute la contrainte à CPLEX
             * avec toutes les villes parcourus.
             */
	
			model.solve();
		} catch (IloException e) {
			e.printStackTrace();
		}
	}
	
}
