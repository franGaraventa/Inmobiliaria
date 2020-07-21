package Filtros;

import clases.Contrato;

public class FContNOT implements FContrato{

	private FContrato filtro;
	
	public FContNOT(FContrato filtro) {
		this.filtro = filtro;
	}
	
	@Override
	public boolean cumple(Contrato c) {
		return(!this.filtro.cumple(c));
	}

}
