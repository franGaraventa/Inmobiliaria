package Pruebas;

import clases.Persona;

public class FPersNom implements FPersona{

	private String nombre;
	
	public FPersNom(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public boolean cumple(Persona p) {
		return ((this.nombre.toLowerCase().equals(p.getNombre().toLowerCase())) || (p.getNombre().toLowerCase().contains(this.nombre.toLowerCase())));
	}

}
