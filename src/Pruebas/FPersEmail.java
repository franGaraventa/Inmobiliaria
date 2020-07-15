package Pruebas;

import clases.Persona;

public class FPersEmail implements FPersona{

	private String email;
	
	public FPersEmail(String email) {
		this.email = email;
	}
	
	@Override
	public boolean cumple(Persona p) {
		return ((this.email.equals(p.getEmail())) || (p.getEmail().contains(this.email)));
	}

}
