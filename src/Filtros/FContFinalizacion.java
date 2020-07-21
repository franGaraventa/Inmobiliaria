package Filtros;

import java.util.Date;

import clases.Contrato;
import utils.Fechas;

public class FContFinalizacion implements FContrato{

	private Date ffinalizacion;
	private Date ffin;
	private String comparador;
	
	public FContFinalizacion(Date ffinalizacion,String comparador) {
		this.ffinalizacion = Fechas.modificarFecha(ffinalizacion);
		this.comparador = comparador;
	}
	
	public FContFinalizacion(Date ffinalizacion,Date ffin,String comparador) {
		this.ffinalizacion = Fechas.modificarFecha(ffinalizacion);
		this.ffin = Fechas.modificarFecha(ffin);
		this.comparador = comparador;
	}
	
	@Override
	public boolean cumple(Contrato c) {
		Date cFecha = Fechas.modificarFecha(c.getFechaFinalizacion());
		switch(comparador) {
			case ("="): return (cFecha.equals(ffinalizacion));
			case (">"): return (cFecha.after(ffinalizacion));
			case ("<"): return (cFecha.before(ffinalizacion));
			case (">="): return (cFecha.after(ffinalizacion) || cFecha.equals(ffinalizacion));
			case ("<="): return (cFecha.before(ffinalizacion) || cFecha.equals(ffinalizacion));
			case ("entre"): return (cFecha.after(ffinalizacion) && cFecha.before(ffin));
		}
		return false;
	}
	
}
