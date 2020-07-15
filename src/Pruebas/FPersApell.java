package Pruebas;

import clases.Persona;

public class FPersApell implements FPersona{

	private String apellido;
	
	public FPersApell(String apellido) {
		this.apellido = apellido;
	}
	
	@Override
	public boolean cumple(Persona p) {
		return ((this.apellido.equals(p.getApellido())) || (p.getApellido().contains(this.apellido)));
	}

}
