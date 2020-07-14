package Pruebas;

import clases.Propiedad;

public class FPropAmoblado implements FPropiedad{

	private boolean amoblado;
	
	public FPropAmoblado(boolean amoblado) {
		this.amoblado = amoblado;
	}
	
	@Override
	public boolean cumple(Propiedad p) {
		return (p.isAmoblado() == amoblado);
	}

}
