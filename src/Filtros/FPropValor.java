package Filtros;

import clases.Propiedad;

public class FPropValor implements FPropiedad{

	private double valor;
	private String comparador;
	
	public FPropValor(double valor,String comparador) {
		this.valor = valor;
		this.comparador = comparador;
	}
	
	@Override
	public boolean cumple(Propiedad p) {
		double valor = p.getValor();
		switch(comparador) {
			case ("="): return (valor == this.valor);
			case (">"): return (valor > this.valor);
			case ("<"): return (valor < this.valor);
			case (">="): return (valor >= this.valor);
			case ("<="): return (valor <= this.valor);
		}
		return false;
	}

}
