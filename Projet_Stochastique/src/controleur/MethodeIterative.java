package controleur;

import java.util.ArrayList;
import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import ilog.cplex.IloCplex.UnknownObjectException;
import modele.Arc;
import modele.Graph;
import modele.ProgrammeLineaire;
import modele.Sommet;
import vue.Interface;

public class MethodeIterative {
	private ProgrammeLineaire prog;
	private int nature;

	public MethodeIterative(ProgrammeLineaire prog, int nature) {
		this.prog = prog;
		this.nature = nature;
	}
	
	/*
	 * Cette methode lance la resolution en fonction de la nature du
	 * probleme.
	 */
	public void run() throws IloException {
		
		//Instancie la classe Cplex avec le programme lineaire et la nature
		Cplex cplex = new Cplex(prog, nature);
		boolean st = false;
		String info;
		int i = 0;
		
		/*Tant que l'on a des sous tours dans le modèle*/
		do {
			//Lancement du solve du model de cplex
			cplex.solve();
			
			//Récupère un booléen en fonction de la présence de sous tours après le solve
			st = contrainteSousTour(cplex);
			info = "Contrainte de sous-tours ajouté au modèle";
			Interface.majAffichage(info);
			i++;
		} while (st && i!= 5);

	}
	
	/*
	 * Permet de gérer les sous tours obtenu après résolution par cplex
	 */
	public boolean contrainteSousTour(Cplex cplex) {
		ArrayList<Sommet> lsommet = prog.getGraph().getSommets();
		IloCplex model = cplex.getModel();
		IloNumVar[][] var = cplex.getVar();
		double[][] cout = cplex.getCout();
		boolean st = false;
		int nbst = 0;

		try {
			if (model.solve()) {
				/*Création d'un nouveau graph pour la solution obtenu via Cplex*/
				Graph newsoluce = new Graph();
				newsoluce.setSommets(lsommet);
				
				/*On parcours le tableau de var et on cree les arcs correspondants qu'on ajoute à notre graph*/
				for (int i = 1; i <= lsommet.size(); i++) {
					for (int j = 1; j <= lsommet.size(); j++) {
						if (i != j) {
							if (model.getValue(var[i][j]) == 1) {
								Sommet si = prog.getGraph().getSommetById(i);
								Sommet sj = prog.getGraph().getSommetById(j);
								Arc a = new Arc(si, sj, cout[i][j], 0, 0);
								newsoluce.addArc(a);
								System.out.println("Arc entre " + a.getSomD().getid() + " et " + a.getSomA().getid()
										+ " | cout = " + a.getCout());
							}
						}
					}
				}

				/*Détection des sous tours et ajout des contraintes*/
				ArrayList<Integer> alreadytreated = new ArrayList<>();
				for (Sommet s : lsommet) {
					/*Si le sommet à deja ete traité, on ne le refait pas. Evite d'avoir des contraintes redondantes*/
					if (!contain(alreadytreated, s.getid())) {
						ArrayList<Integer> listeparcour = new ArrayList<>();
						
						/*Ajout du sommet à la liste du parcour en cours et à la liste des sommets deja traite*/
						listeparcour.add(s.getid());
						alreadytreated.add(s.getid());
						
						/*Recuperation de l'arc dont le sommet est le debut*/
						Arc ad = newsoluce.getArcbySommetD(s.getid());
						if (ad != null) {
							/*Recuperation du sommet d'arrivee de l'arc*/
							Sommet so = ad.getSomA();
							
							/*Ajout du sommet d'arrive à la liste du parcour en cours*/
							listeparcour.add(so.getid());
							alreadytreated.add(so.getid());
							
							/*Tant que l'on retombe pas sur le sommet d'origine du parcour en cours*/
							do {
								/*On cherche l'arc suivant dans le parcour*/
								ad = newsoluce.getArcbySommetD(so.getid());
								
								if (ad != null) {
									so = ad.getSomA();

									if (so.getid() != s.getid()) {
										listeparcour.add(so.getid());
										alreadytreated.add(so.getid());
									}
								}
							} while (so.getid() != s.getid() && ad != null);
							
							/*Si l'on a pas parcouru toutes les villes, on est en presence d'un sous tours*/
							if ((listeparcour.size()) != lsommet.size()) {
								nbst++;
								st = true;
								System.out.println(listeparcour);
								
								/*On déclare une nouvelle expression*/
								IloLinearNumExpr cons = model.linearNumExpr();
								
								/*Pour chaque trajet dans la liste du parcour en cours, on l'ajoute a l'expression*/
								for (int i = 0; i < listeparcour.size(); i++) {
									int d = listeparcour.get(i).intValue();
									int a;

									if (i + 1 == listeparcour.size()) {
										a = listeparcour.get(0).intValue();

									} else {
										a = listeparcour.get(i + 1).intValue();
									}
									
									cons.addTerm(1.0, var[d][a]);
								}
								
								/*On ajoute ensuite la contrainte au model*/
								model.addLe(cons, listeparcour.size() - 1);

								listeparcour.clear();
							}
						}
					}
					cplex.setModel(model);
				}
				Interface.majAffichage(nbst + " sous-tours détecté");

			}
		} catch (UnknownObjectException e) {
			e.printStackTrace();
		} catch (IloException e) {
			e.printStackTrace();
		}

		return st;
	}

	private boolean contain(ArrayList<Integer> lint, int id) {
		for (Integer i : lint) {
			if (id == i.intValue()) {
				return true;
			}
		}
		return false;
	}
}
