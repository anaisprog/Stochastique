package modele;
import java.util.*;

public class ProgrammeLineaire {
	private Matrice matriceContraintes; 
	private ArrayList<Matrice> iterations; 
	
	private int nbIterations; 
	private double meilleureSolution; 
	
	private boolean maximisation; 
	private Graph graph;
	
	
	
	public ProgrammeLineaire() {
		
	}

	public ProgrammeLineaire(Matrice matriceContraintes, ArrayList<Matrice> iterations, int nbIterations,
			double meilleureSolution, boolean maximisation, Graph graph) {
		this.matriceContraintes = matriceContraintes;
		this.iterations = iterations;
		this.nbIterations = nbIterations;
		this.meilleureSolution = meilleureSolution;
		this.maximisation = maximisation;
		this.graph = graph;
	}
	
	public int cout(){
		int cout = 0;
				
		for(Arc a : this.graph.getArcs()){
			cout += a.getMoyenne();
		}
		
		return cout;
	}
	
	public Graph getGraph() {
		return graph;
	}
	
	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	
	/*Permet d'ajouter une contrainte au problème et donc une ligne de plus dans la matrice.*/
	public void addContraintes(ArrayList<Double> ligne) {
		
	}
	/*Permet d'ajouter la fonction objectif en prenant les contraintes*/
	public void addFctObj(ArrayList<Double> ligne) {
		
	}
	
	/* Retourne la solution du systÃ¨me dans une ArrayList.*/
	public ArrayList<Double> getSolution() {
		return null;
		
	}
		
	public void addIteration() {
	}
	

	public int getColonnes() {
		return 2;
	}
	
	public double getMeilleureSolution() {
		return meilleureSolution;
	}
	
	public void setMeilleureSolution(double meilleureSolution) {
		this.meilleureSolution = meilleureSolution;
	}
	
	
	
	
	
}
