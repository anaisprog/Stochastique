package controleur;

import java.util.ArrayList;

import ilog.concert.IloException;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import ilog.cplex.IloCplex.UnknownObjectException;
import modele.Arc;
import modele.Graph;
import modele.ProgrammeLineaire;
import modele.Sommet;

public class MethodeIterative {
	private ProgrammeLineaire prog;
	private int nature;

	public MethodeIterative(ProgrammeLineaire prog, int nature) {
		this.prog = prog;
		this.nature = nature;
	}

	public void run() throws IloException {
		if (nature == 0) {
			Cplex cplex = new Cplex(prog, nature);
			cplex.solve();
			
			/*
			ArrayList<Sommet> lsommet = prog.getGraph().getSommets();
			IloCplex model = cplex.getModel();
			IloNumVar[][] var = cplex.getVar();
			double[][] cout = cplex.getCout();
			
			Graph newsoluce = new Graph();
			newsoluce.setSommets(lsommet);
			for (int i = 1; i <= lsommet.size(); i++) {
				for (int j = 1; j <= lsommet.size(); j++) {
					if (i != j) {
						try {
							if (model.getValue(var[i][j]) == 1) {
								Sommet si = prog.getGraph().getSommetById(i);
								Sommet sj = prog.getGraph().getSommetById(j);
								Arc a = new Arc(si, sj, cout[i][j], 0, 0);
								newsoluce.addArc(a);
							}
						} catch (UnknownObjectException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IloException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			
			for(Arc a : newsoluce.getArcs()){
				System.out.println("Arc entre " + a.getSomD().getid() + " et " + a.getSomA().getid()
						+ " | cout = " + a.getCout());
			}
			*/
			
			boolean st = contrainteSousTour(cplex);

			if (st) {
				System.out.println("Contrainte de sous-tours ajout� au model");
				cplex.solve();
			}
		} else {

		}

	}

	public boolean contrainteSousTour(Cplex cplex) {
		ArrayList<Sommet> lsommet = prog.getGraph().getSommets();
		IloCplex model = cplex.getModel();
		IloNumVar[][] var = cplex.getVar();
		double[][] cout = cplex.getCout();
		boolean st = false;
		int nbst = 0;
		
		try {
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
				for (Sommet s : lsommet) {
					
					if (!contain(alreadytreated, s.getid())) {
						ArrayList<Integer> listeparcour = new ArrayList<>();
						
						listeparcour.add(s.getid());
						alreadytreated.add(s.getid());
						Arc ad = newsoluce.getArcbySommetD(s.getid());
						Sommet so = ad.getSomA();
						
						listeparcour.add(so.getid());
						alreadytreated.add(so.getid());
						

						do {
							ad = newsoluce.getArcbySommetD(so.getid());
							so = ad.getSomA();

							listeparcour.add(so.getid());
							alreadytreated.add(so.getid());
						} while (so.getid() != s.getid());

						if ((listeparcour.size()-1) != lsommet.size()) {
							nbst++;
							st = true;
							System.out.println(listeparcour);
							
							/*
							 * TODO : Ajout des contraintes au model
							 * (cplex.setModel(model));
							 * 
							 */

							listeparcour.clear();
						}
					}
				}

				System.out.println( nbst + " sous-tours d�tect�");
			}
		} catch (UnknownObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return st;
	}
	
	private boolean contain(ArrayList<Integer> lint, int id){
		for(Integer i : lint){
			if(id == i.intValue()){
				
				return true;
			}
		}
		
		return false;
	}
}
