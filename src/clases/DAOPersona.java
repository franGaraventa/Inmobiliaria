package clases;

import java.util.List;

public interface DAOPersona {
	public List<Persona> getPersonas();
	public void guardar(Persona p);
	public void modificar(Persona p);
	public void eliminar(Persona p);
}