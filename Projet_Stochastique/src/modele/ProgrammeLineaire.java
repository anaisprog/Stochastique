package modele;
import java.util.*;

public class ProgrammeLineaire {

	private int nbIterations; 
	
	private boolean maximisation; 
	private Graph graph;
	
	
	public ProgrammeLineaire() {
		
	}

	public ProgrammeLineaire(int nbIterations,
		 boolean maximisation, Graph graph) {
	
		this.nbIterations = nbIterations;
		
		this.maximisation = maximisation;
		this.graph = graph;
	}
	
	
	
	public Graph getGraph() {
		return graph;
	}
	
	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public int getNbIterations() {
		return nbIterations;
	}

	public void setNbIterations(int nbIterations) {
		this.nbIterations = nbIterations;
	}

	public boolean isMaximisation() {
		return maximisation;
	}

	public void setMaximisation(boolean maximisation) {
		this.maximisation = maximisation;
	}

	
	
}
