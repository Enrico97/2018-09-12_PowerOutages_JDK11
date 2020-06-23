package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.poweroutages.db.PowerOutagesDAO;

public class Model {

	PowerOutagesDAO dao = new PowerOutagesDAO();
	Graph<Nerc, DefaultWeightedEdge> grafo;
	Simulator s = new Simulator();
	
	public Graph<Nerc, DefaultWeightedEdge> creaGrafo() {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, dao.loadAllNercs());
		for(Adiacenza a : dao.archi())
			Graphs.addEdge(grafo, a.getN1(), a.getN2(), a.getPeso());
		return grafo;
	}
	
	public List<Nerc> loadAllNercs() {
		return dao.loadAllNercs();
	}
	
	public List<Vicini> vicini(Nerc n) {
		List<Vicini> listaVicini = new ArrayList<>();
		for(Nerc nerc : Graphs.neighborListOf(grafo, n)) {
			Vicini v = new Vicini(nerc, grafo.getEdgeWeight(grafo.getEdge(n, nerc)));
			listaVicini.add(v);
	}
		Collections.sort(listaVicini);
		return listaVicini;
	}
	
	public List<Interruzioni> interruzioni() {
		return dao.interruzioni();
	}

	public Map<Integer, Interruzioni> mappaInterruzioni() {
		return dao.mappaInterruzioni();
	}
	
	public void simula(int k) {
		s.simula(this, k);
	}
	
	public Map<Nerc, Integer> getBonus() {
		return s.getBonus();
	}
	
	public int getCatastrofi() {
		return s.getCatastrofi();
	}

}
