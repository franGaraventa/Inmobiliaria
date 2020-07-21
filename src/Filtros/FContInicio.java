package Filtros;

import java.util.Date;

import clases.Contrato;
import utils.Fechas;

public class FContInicio implements FContrato{

	private Date finicio;
	private Date ffin;
	private String comparador;
	
	public FContInicio(Date finicio,String comparador) {
		this.finicio = Fechas.modificarFecha(finicio);
		this.comparador = comparador;
	}
	
	public FContInicio(Date finicio,Date ffin,String comparador) {
		this.finicio = Fechas.modificarFecha(finicio);
		this.ffin = Fechas.modificarFecha(ffin);
		this.comparador = comparador;
	}
	
	@Override
	public boolean cumple(Contrato c) {
		Date cFecha = Fechas.modificarFecha(c.getFechaInicio());
		switch(comparador) {
			case ("="): return (cFecha.equals(finicio));
			case (">"): return (cFecha.after(finicio));
			case ("<"): return (cFecha.before(finicio));
			case (">="): return (cFecha.after(finicio) || cFecha.equals(finicio));
			case ("<="): return (cFecha.before(finicio) || cFecha.equals(finicio));
			case ("entre"): return (cFecha.after(finicio) && cFecha.before(ffin));
		}
		return false;
	}

}
