package Filtros;

import clases.Persona;

public class FPersDirec implements FPersona{

	private String direccion;
	
	public FPersDirec(String direccion) {
		this.direccion = direccion;
	}
	
	@Override
	public boolean cumple(Persona p) {
		return ((this.direccion.toLowerCase().equals(p.getDireccion().toLowerCase())) || (p.getDireccion().toLowerCase().contains(this.direccion.toLowerCase())));
	}
	
}
