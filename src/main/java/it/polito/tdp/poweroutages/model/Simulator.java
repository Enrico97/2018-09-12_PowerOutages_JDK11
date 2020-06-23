package it.polito.tdp.poweroutages.model;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graphs;

import it.polito.tdp.poweroutages.model.Event.eventType;

public class Simulator {
	
	private PriorityQueue<Event> queue;
	private Map<Nerc, Integer> bonus;
	private Map<Nerc, Nerc> donatori;
	private int catastrofi;
	private Model model;
	private int k;
	
	public void simula(Model model, int k) {
		queue=new PriorityQueue<>();
		bonus=new HashMap<>();
		donatori=new HashMap<>();
		catastrofi=0;
		this.model=model;
		this.k=k;
		
		for(Interruzioni i : model.interruzioni()) {
			Event e = new Event (i.getId(), i.getNerc(), null, i.getInizio(), eventType.inizio);
			queue.add(e);
		}
		
		for(Nerc n : model.creaGrafo().vertexSet())
			donatori.put(n, null);
		
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
	}
	}

	private void processEvent(Event e) {
		boolean trovato = false;
		double min = 1000000;
		Nerc best = null;
		
		switch (e.getTipo()) {
			
			case inizio:
				for(Nerc n : Graphs.neighborListOf(model.creaGrafo(), e.getGuasto())) {
					if(e.getGuasto().getAiutato().contains(n) && !donatori.values().contains(n)) {
						if(model.creaGrafo().getEdgeWeight(model.creaGrafo().getEdge(e.getGuasto(), n))<min) {
							min = model.creaGrafo().getEdgeWeight(model.creaGrafo().getEdge(e.getGuasto(), n));
							best = n;
							trovato = true;
						}}}
				if(trovato == true) {
					Event ev = new Event(e.getI(), e.getGuasto(), best, model.mappaInterruzioni().get(e.getI()).getFine() , eventType.fine);
					queue.add(ev);
					donatori.put(e.getGuasto(), best);
				}
					
				if(trovato == false) {
					for(Nerc n : Graphs.neighborListOf(model.creaGrafo(), e.getGuasto())) {
						if(!donatori.values().contains(n) && model.creaGrafo().getEdgeWeight(model.creaGrafo().getEdge(e.getGuasto(), n))<min) {
							min = model.creaGrafo().getEdgeWeight(model.creaGrafo().getEdge(e.getGuasto(), n));
							best = n;
							trovato = true;
						}}
					if(trovato == true) {
						Event ev = new Event(e.getI(), e.getGuasto(), best, model.mappaInterruzioni().get(e.getI()).getFine() , eventType.fine);
						queue.add(ev);
						best.getAiutato().add(e.getGuasto());
						queue.add(new Event(-1, e.getGuasto(), best, e.getTempo().plusMonths(k), eventType.rimuovi));
						donatori.put(e.getGuasto(), best);
					}
					}
				if(trovato == false) {
					catastrofi++;
				}
				
				break;
				
			case fine:
				donatori.put(e.getGuasto(), null);
				int giorni=(int) model.mappaInterruzioni().get(e.getI()).getInizio().until(model.mappaInterruzioni().get(e.getI()).getFine(), ChronoUnit.DAYS);
				bonus.put(e.getDonatore(), giorni);
				break;
				
			case rimuovi:
				e.getDonatore().getAiutato().remove(e.getGuasto());
				break;
				
		}
		
	}

	public Map<Nerc, Integer> getBonus() {
		return bonus;
	}

	public int getCatastrofi() {
		return catastrofi;
	}
	
	

}
