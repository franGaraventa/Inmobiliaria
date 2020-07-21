package Filtros;

import clases.Contrato;

public class FContLocador implements FContrato{

	private String locador;
	
	public FContLocador(String locador) {
		this.locador = locador;
	}
	
	@Override
	public boolean cumple(Contrato c) {
		return (locador.toLowerCase().equals(c.getLocador().toLowerCase())) || (c.getLocador().toLowerCase().contains(locador.toLowerCase()));
	}

}
