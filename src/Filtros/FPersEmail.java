package Filtros;

import clases.Persona;

public class FPersEmail implements FPersona{

	private String email;
	
	public FPersEmail(String email) {
		this.email = email;
	}
	
	@Override
	public boolean cumple(Persona p) {
		return ((this.email.toLowerCase().equals(p.getEmail().toLowerCase())) || (p.getEmail().toLowerCase().contains(this.email.toLowerCase())));
	}

}
