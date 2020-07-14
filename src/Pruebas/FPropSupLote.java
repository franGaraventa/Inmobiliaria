package Pruebas;

import clases.Propiedad;

public class FPropSupLote implements FPropiedad{
	
	private double supLote;
	private String comparador;
	
	public FPropSupLote(double supLote,String comparador) {
		this.supLote = supLote;
		this.comparador = comparador;
	}
	
	@Override
	public boolean cumple(Propiedad p) {
		double supLote = p.getSupLote();
		switch(comparador) {
			case ("="): return (supLote == this.supLote);
			case (">"): return (supLote > this.supLote);
			case ("<"): return (supLote < this.supLote);
			case (">="): return (supLote >= this.supLote);
			case ("<="): return (supLote <= this.supLote);
		}
		return false;
	}

}
