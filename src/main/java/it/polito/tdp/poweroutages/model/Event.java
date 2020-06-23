package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class Event implements Comparable<Event>{
	
	public enum eventType {
		inizio, fine, rimuovi;
	}

	private int i;
	private Nerc guasto;
	private Nerc donatore;
	private LocalDateTime tempo;
	private eventType tipo;
	
	public Event(int i, Nerc guasto, Nerc donatore, LocalDateTime tempo, eventType tipo) {
		super();
		this.i = i;
		this.guasto = guasto;
		this.donatore = donatore;
		this.tempo = tempo;
		this.tipo = tipo;
	}

	public Nerc getGuasto() {
		return guasto;
	}

	public Nerc getDonatore() {
		return donatore;
	}

	public LocalDateTime getTempo() {
		return tempo;
	}

	public eventType getTipo() {
		return tipo;
	}
	

	public int getI() {
		return i;
	}

	@Override
	public int compareTo(Event o) {
		return this.getTempo().compareTo(o.getTempo());
	}
	
	
	
	
	
	
}
