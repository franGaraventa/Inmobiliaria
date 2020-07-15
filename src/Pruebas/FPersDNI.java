package Pruebas;

import clases.Persona;

public class FPersDNI implements FPersona{

	private String dni;
	
	public FPersDNI(String dni) {
		this.dni = dni;
	}
	
	@Override
	public boolean cumple(Persona p) {
		return ((this.dni.equals(p.getDni()) || (p.getDni().contains(this.dni))));
	}

}
