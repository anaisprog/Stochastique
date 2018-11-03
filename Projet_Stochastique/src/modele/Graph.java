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
	
	public Sommet getSommetById(int id){
		for(Sommet s : sommets){
			if(s.getid() == id){
				return s;
			}
		}
		
		return null;
	}
	
	public Arc getArcbySommetD(int idD){
		for(Arc a : arcs){
			if(a.getSomD().getid() == idD){
				return a;
			}
		}
		
		return null;
	}
	
	public Arc getArcbySommetA(int idA){
		for(Arc a : arcs){
			if(a.getSomA().getid() == idA){
				return a;
			}
		}
		
		return null;
	}
	
	public Arc getArcbyDA(int idD, int idA){
		for(Arc a : arcs){
			if((a.getSomD().getid() == idD) && (a.getSomA().getid() == idA)){
				return a;
			}
		}
		
		return null;
	}
	
	public int cout(){
		int cout = 0;
				
		for(Arc a : arcs){
			cout += a.getCout();
		}
		
		return cout;
	}
}