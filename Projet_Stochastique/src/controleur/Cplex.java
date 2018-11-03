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
	private boolean find;
	private IloNumVar[][] var;
	private double[][] cout;
	
	public Cplex() 
	{
		try {
			this.model = new IloCplex();
		} catch (IloException e) {
			e.printStackTrace();
		}
	}
	

	private void run(ProgrammeLineaire prog)
	{
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
			
			IloNumVar[] u = new IloNumVar[lsommet.size()];
	        for (int i = 2; i < lsommet.size(); i++) {
	        	u[i] = model.numVar(0, Double.MAX_VALUE, "u." + i);
	        }
	        
	            
			/*Cr�ation de la fonction objectif et ajout au model*/
	        this.fct = model.linearNumExpr();
            for (int i = 1; i < lsommet.size(); i++) {
                for (int j = 1; j < lsommet.size(); j++) {
                    if (i != j) {
                        this.fct.addTerm(cout[i][j], var[i][j]);
                    }
                }
            }
            model.addMinimize(fct);
			
            /*Ajout de la premi�re contrainte de parcour*/
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
            for (int i = 2; i < lsommet.size(); i++) {
                for (int j = 2; j < lsommet.size(); j++) {
                     if (i!=j){
                        IloLinearNumExpr expr = model.linearNumExpr();
                        expr.addTerm(1, u[i]);
                        expr.addTerm(-1, u[j]);
                        expr.addTerm(lsommet.size() - 1, var[i][j]);
                        model.addLe(expr, lsommet.size() - 2);
                    }
                }
            }
	
			model.solve();
		} catch (IloException e) {
			e.printStackTrace();
		}
	}
	
}
