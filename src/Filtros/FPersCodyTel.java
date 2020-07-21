package Filtros;

import clases.Persona;

public class FPersCodyTel implements FPersona{

	private String codArea;
	private String telefono;
	
	public FPersCodyTel(String codArea,String telefono) {
		this.codArea = codArea;
		this.telefono = telefono;
	}
	
	@Override
	public boolean cumple(Persona p) {
		return (((this.codArea.equals(p.getCodArea())) || (p.getCodArea().contains(this.codArea))) || ((this.telefono.equals(p.getTelefono()))|| (p.getTelefono().contains(this.telefono))));
	}

}
