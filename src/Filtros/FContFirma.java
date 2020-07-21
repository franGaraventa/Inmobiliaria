package Filtros;
import java.util.Date;

import clases.Contrato;
import utils.Fechas;

public class FContFirma implements FContrato{

	private Date ffirma;
	private Date ffin;
	private String comparador;
	
	public FContFirma(Date ffirma,String comparador) {
		this.ffirma = Fechas.modificarFecha(ffirma);
		this.comparador = comparador;
	}
	
	public FContFirma(Date ffirma,Date ffin,String comparador) {
		this.ffirma = Fechas.modificarFecha(ffirma);
		this.ffin = Fechas.modificarFecha(ffin);
		this.comparador = comparador;
	}
	
	@Override
	public boolean cumple(Contrato c) {
		Date cFecha = Fechas.modificarFecha(c.getFechaFirma());
		switch(comparador) {
			case ("="): return (cFecha.equals(ffirma));
			case (">"): return (cFecha.after(ffirma));
			case ("<"): return (cFecha.before(ffirma));
			case (">="): return (cFecha.after(ffirma) || cFecha.equals(ffirma));
			case ("<="): return (cFecha.before(ffirma) || cFecha.equals(ffirma));
			case ("entre"): return (cFecha.after(ffirma) && cFecha.before(ffin));
		}
		return false;
	}

}
