package Filtros;

import clases.Contrato;

public class FContPlazo implements FContrato{

	private int plazo;
	private String comparador;
	
	public FContPlazo(int plazo,String comparador) {
		this.plazo = plazo;
		this.comparador = comparador;
	}
	
	@Override
	public boolean cumple(Contrato c) {
		switch(comparador) {
			case ("="): return (c.getPlazo() == this.plazo);
			case (">"): return (c.getPlazo() > this.plazo);
			case ("<"): return (c.getPlazo() < this.plazo);
			case (">="): return (c.getPlazo() >= this.plazo);
			case ("<="): return (c.getPlazo() <= this.plazo);
		}
		return false;
	}

	
}
