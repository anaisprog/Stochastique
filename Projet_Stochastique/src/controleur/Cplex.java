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

	public void run(ProgrammeLineaire prog, int nature) {
		boolean hamilton = false;

		if (nature == 0) {
			try {

				ArrayList<Sommet> lsommet = prog.getGraph().getSommets();
				ArrayList<Arc> larc = prog.getGraph().getArcs();
				this.var = new IloNumVar[lsommet.size()+1][lsommet.size()+1];
				this.cout = new double[lsommet.size()+1][lsommet.size()+1];
				
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

				while (!hamilton) {
					if (model.solve()) {
						Graph newsoluce = new Graph();
						newsoluce.setSommets(lsommet);
						for (int i = 1; i <= lsommet.size(); i++) {
							for (int j = 1; j <= lsommet.size(); j++) {
								if (i != j) {
									if (model.getValue(var[i][j]) == 1) {
										Sommet si = prog.getGraph().getSommetById(i);
										Sommet sj = prog.getGraph().getSommetById(j);
										Arc a = new Arc(si, sj, cout[i][j], 0, 0);
										newsoluce.addArc(a);
									}
								}
							}
						}
						
						ArrayList<Integer> alreadytreated = new ArrayList<>();
						for(Sommet s : lsommet){
							if(!alreadytreated.contains(s.getid())){
								ArrayList<Integer> listeparcour = new ArrayList<>();
								
								listeparcour.add(s.getid());
								alreadytreated.add(s.getid());
								
								Arc ad = newsoluce.getArcbySommetD(s.getid());
								listeparcour.add(ad.getSomA().getid());
								
								Sommet so = ad.getSomA();
								
								do {
									ad = newsoluce.getArcbySommetD(so.getid());
									so = ad.getSomA();
									
									listeparcour.add(so.getid());
									alreadytreated.add(so.getid());
								} while(so.getid() != s.getid());
								
								if(listeparcour.size() != lsommet.size()){
									System.out.println("Sous-tour détecté");
								}
							}
						}
					}
				}

				/* Contrainte de sous tours */
				/*
				 * TODO : Récupérer le IloNumVar contenant tout les chemins et
				 * recrée un Graph avec les arcs Ensuite partir de la première
				 * ville et parcourir les arcs jusqu'a retombé sur cette
				 * dernière. Si lorsqu'on retombe sur elle, on à parcouru le
				 * nombres de villes contenu dans le problème, on a un chemin
				 * hamiltonien sinon on a un sous tours et on ajoute la
				 * contrainte à CPLEX avec toutes les villes parcourus.
				 */

			} catch (IloException e) {
				e.printStackTrace();
			}
		} else {
			/*
			 * TODO : CplexStocha cs = new CplexStocha(); cs.run(prog);
			 */
		}
	}
}
