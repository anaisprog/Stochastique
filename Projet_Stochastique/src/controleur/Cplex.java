package controleur;

import java.util.ArrayList;

import ilog.concert.*;
import ilog.cplex.*;
import modele.Arc;
import modele.ProgrammeLineaire;

public class Cplex {
	private IloCplex model;
	private IloLinearNumExpr fct;
	private IloNumVar[][] var;
	private IloRange[][] rng;
	
	
	public Cplex() {
		try {
			this.model = new IloCplex();
		} catch (IloException e) {
			e.printStackTrace();
		}
	}


	private void run(ProgrammeLineaire prog){
		try {
			
			ArrayList<Arc> larcs = prog.getGraph().getArcs();
			this.var = new IloNumVar[larcs.size()+1][larcs.size()+1];
			
			
			for(Arc a : larcs){
				this.var[a.getSomD().getid()][a.getSomA().getid()] = model.boolVar("x." + a.getSomD().getid() + "." + a.getSomA().getid());
			}
			
			this.fct = model.linearNumExpr();
			for(Arc a : larcs){
				fct.addTerm(a.getMoyenne(),this.var[a.getSomD().getid()][a.getSomA().getid()]);
			}
			model.addMinimize(fct);
			
			for(Arc a : larcs){
				IloLinearNumExpr expr = model.linearNumExpr();
				
				expr.addTerm(1, this.var[a.getSomD().getid()][a.getSomA().getid()]);
				
				model.addEq(expr, 1);
			}
			
			System.out.println(model.getModel());
			model.solve();
			
		} catch (IloException e) {
			e.printStackTrace();
		}
	}
	
}
