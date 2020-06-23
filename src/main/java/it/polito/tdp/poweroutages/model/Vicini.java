package it.polito.tdp.poweroutages.model;

public class Vicini implements Comparable <Vicini>{
	
	private Nerc n;
	private double peso;
	
	public Vicini(Nerc n, double peso) {
		super();
		this.n = n;
		this.peso = peso;
	}

	public Nerc getN() {
		return n;
	}

	public double getPeso() {
		return peso;
	}

	@Override
	public int compareTo(Vicini o) {
		int x = 0;
		if(this.peso>o.peso)
			x=-1;
		if(this.peso<o.peso)
			x=1;
		return x;
	}

	@Override
	public String toString() {
		return n + " --> " + peso;
	}
	
	

}
