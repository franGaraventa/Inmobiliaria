package Filtros;

import clases.Persona;

public class FPersOR implements FPersona{

	private FPersona filtro1;
	private FPersona filtro2;
	
	public FPersOR(FPersona filtro1,FPersona filtro2) {
		this.filtro1 = filtro1;
		this.filtro2 = filtro2;
	}
	
	@Override
	public boolean cumple(Persona p) {
		return ((this.filtro1.cumple(p)) || (this.filtro2.cumple(p)));
	}

}
