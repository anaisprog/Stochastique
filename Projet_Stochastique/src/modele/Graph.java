package modele;

import java.util.ArrayList;

public class Graph {
	private ArrayList<Arc> arcs;
	
	public Graph() {
		this.arcs = new ArrayList<>();
	}
	
	public Graph(ArrayList<Arc> arcs) {
		this.arcs = arcs;
	}
	
	public ArrayList<Arc> getArcs() {
		return arcs;
	}

	public void setArcs(ArrayList<Arc> arcs) {
		this.arcs = arcs;
	}
	
	public void addArc(Arc a){
		this.arcs.add(a);
	}
}
