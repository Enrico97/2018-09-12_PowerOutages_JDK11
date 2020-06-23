package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class Interruzioni {
	
	private int id;
	private Nerc nerc;
	private LocalDateTime inizio;
	private LocalDateTime fine;
	
	
	public Interruzioni(int id, Nerc nerc, LocalDateTime inizio, LocalDateTime fine) {
		super();
		this.id = id;
		this.nerc = nerc;
		this.inizio = inizio;
		this.fine = fine;
	}


	public int getId() {
		return id;
	}


	public Nerc getNerc() {
		return nerc;
	}


	public LocalDateTime getInizio() {
		return inizio;
	}


	public LocalDateTime getFine() {
		return fine;
	}
	
	

}
