package Filtros;

import clases.Propiedad;

public class FPropOR implements FPropiedad{

	private FPropiedad filtro1;
	private FPropiedad filtro2;
	
	public FPropOR(FPropiedad filtro1,FPropiedad filtro2) {
		this.filtro1 = filtro1;
		this.filtro2 = filtro2;
	}
	
	@Override
	public boolean cumple(Propiedad p) {
		return((filtro1.cumple(p)) || (filtro2.cumple(p)));
	}

}
