package Pruebas;

import clases.Propiedad;

public class FPropNOT implements FPropiedad{

	private FPropiedad filtro;
	
	public FPropNOT(FPropiedad filtro) {
		this.filtro = filtro;
	}
	
	@Override
	public boolean cumple(Propiedad p) {
		return (!filtro.cumple(p));
	}

}
