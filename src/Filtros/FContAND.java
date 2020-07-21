package Filtros;

import clases.Contrato;

public class FContAND implements FContrato{

	private FContrato filtro1;
	private FContrato filtro2;
	
	public FContAND(FContrato filtro1,FContrato filtro2) {
		this.filtro1 = filtro1;
		this.filtro2 = filtro2;
	}
	
	@Override
	public boolean cumple(Contrato c) {
		return ((this.filtro1.cumple(c)) && (this.filtro2.cumple(c)));
	}

}
