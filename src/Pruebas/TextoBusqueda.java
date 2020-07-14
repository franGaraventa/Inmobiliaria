package Pruebas;

public class TextoBusqueda {

	private String texto;
	private boolean negado;
	
	public TextoBusqueda(String texto) {
		this.texto = texto;
		this.negado = false;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public boolean isNegado() {
		return negado;
	}

	public void setNegado(boolean negado) {
		this.negado = negado;
	}
	
	
	
}
