package interfaces;

import java.util.List;

import clases.Persona;

public interface DAOPersona {
	public List<Persona> getPersonas();
	public Persona obtenerPersona(String dni);
	public void guardar(Persona p);
	public void modificar(Persona p);
	public void eliminar(Persona p);
}
