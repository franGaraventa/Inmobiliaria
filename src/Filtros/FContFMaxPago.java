package Filtros;

import clases.Contrato;

public class FContFMaxPago implements FContrato{

	private int pago;
	private String comparador;
	
	public FContFMaxPago(int pago,String comparador) {
		this.comparador = comparador;
		this.pago = pago;
	}
	
	@Override
	public boolean cumple(Contrato c) {
		switch(comparador) {
			case ("="): return (c.getFechaMaxPago() == this.pago);
			case (">"): return (c.getFechaMaxPago() > this.pago);
			case ("<"): return (c.getFechaMaxPago() < this.pago);
			case (">="): return (c.getFechaMaxPago() >= this.pago);
			case ("<="): return (c.getFechaMaxPago() <= this.pago);
		}
		return false;
	}

}
