package Pruebas;

import clases.Propiedad;

public class FPropSupCubierta implements FPropiedad{

	private double supCubierta;
	private String comparador;
	
	public FPropSupCubierta(double supCubierta,String comparador) {
		this.supCubierta = supCubierta;
		this.comparador = comparador;
	}
	
	@Override
	public boolean cumple(Propiedad p) {
		double supCubierta = p.getSupCubierta();
		switch(comparador) {
			case ("="): return (supCubierta == this.supCubierta);
			case (">"): return (supCubierta > this.supCubierta);
			case ("<"): return (supCubierta < this.supCubierta);
			case (">="): return (supCubierta >= this.supCubierta);
			case ("<="): return (supCubierta <= this.supCubierta);
		}
		return false;
	}
	
}
