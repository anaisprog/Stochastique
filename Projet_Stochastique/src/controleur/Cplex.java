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

	public void createModel(ProgrammeLineaire prog, int nature) {
		if (nature == 0) {
			try {

				ArrayList<Sommet> lsommet = prog.getGraph().getSommets();
				ArrayList<Arc> larc = prog.getGraph().getArcs();
				this.var = new IloNumVar[lsommet.size() + 1][lsommet.size() + 1];
				this.cout = new double[lsommet.size() + 1][lsommet.size() + 1];

				for (Arc a : larc) {
					cout[a.getSomD().getid()][a.getSomA().getid()] = a.getCout();
				}

				/* Ajout des variables des chemins au model */
				for (int i = 1; i <= lsommet.size(); i++) {
					for (int j = 1; j <= lsommet.size(); j++) {
						if (i != j) {
							var[i][j] = model.boolVar("x." + i + "." + j);
						}
					}
				}

				/* Création de la fonction objectif et ajout au model */
				this.fct = model.linearNumExpr();
				for (int i = 1; i <= lsommet.size(); i++) {
					for (int j = 1; j <= lsommet.size(); j++) {
						if (i != j) {
							this.fct.addTerm(cout[i][j], var[i][j]);
						}
					}
				}
				model.addMinimize(fct);

				/* Ajout de la première contrainte de parcour */
				for (int j = 1; j <= lsommet.size(); j++) {
					IloLinearNumExpr expr = model.linearNumExpr();
					for (int i = 1; i <= lsommet.size(); i++) {
						if (i != j) {
							expr.addTerm(1, var[i][j]);
						}
					}
					model.addEq(expr, 1);
				}

				/* Ajout de la seconde contrainte de parcour */
				for (int i = 1; i <= lsommet.size(); i++) {
					IloLinearNumExpr expr = model.linearNumExpr();
					for (int j = 1; j <= lsommet.size(); j++) {
						if (j != i) {
							expr.addTerm(1, var[i][j]);
						}
					}
					model.addEq(expr, 1);
				}
				
			} catch (IloException e) {
				e.printStackTrace();
	
			}
		} else {
			/*
			 * TODO : CplexStocha cs = new CplexStocha(); cs.run(prog);
			 */
		}
	}
	
	public void solve(){
		try {
			model.solve();
			model.output().println("Solution status = " + model.getStatus());
			model.output().println("Solution value  = " + model.getObjValue());
		} catch (IloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public IloCplex getModel() {
		return model;
	}

	public void setModel(IloCplex model) {
		this.model = model;
	}

	public IloLinearNumExpr getFct() {
		return fct;
	}

	public void setFct(IloLinearNumExpr fct) {
		this.fct = fct;
	}

	public IloNumVar[][] getVar() {
		return var;
	}

	public void setVar(IloNumVar[][] var) {
		this.var = var;
	}

	public double[][] getCout() {
		return cout;
	}

	public void setCout(double[][] cout) {
		this.cout = cout;
	}
}
