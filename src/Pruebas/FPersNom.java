package Pruebas;

import clases.Persona;

public class FPersNom implements FPersona{

	private String nombre;
	
	public FPersNom(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public boolean cumple(Persona p) {
		return ((this.nombre.equals(p.getNombre())) || (p.getNombre().contains(this.nombre)));
	}

}
