package modele;

import java.util.ArrayList;

public class Graph {
	private ArrayList<Arc> arcs;
	private ArrayList<Sommet> sommets;
	
	public Graph() {
		this.arcs = new ArrayList<>();
		this.sommets = new ArrayList<>();
	}
	
	public Graph(ArrayList<Arc> arcs, ArrayList<Sommet> sommets) {
		super();
		this.arcs = arcs;
		this.sommets = sommets;
	}

	public ArrayList<Arc> getArcs() {
		return arcs;
	}

	public void setArcs(ArrayList<Arc> arcs) {
		this.arcs = arcs;
	}
	
	public ArrayList<Sommet> getSommets() {
		return sommets;
	}

	public void setSommets(ArrayList<Sommet> sommets) {
		this.sommets = sommets;
	}

	public void addArc(Arc a){
		this.arcs.add(a);
	}
}
