package Filtros;

import clases.Persona;

public class FPersNOT implements FPersona{

	private FPersona filtro;
	
	public FPersNOT(FPersona filtro) {
		this.filtro = filtro;
	}
	
	@Override
	public boolean cumple(Persona p) {
		return(!this.filtro.cumple(p));
	}

}
