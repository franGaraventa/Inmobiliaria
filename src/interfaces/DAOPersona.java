package interfaces;

import java.util.List;

import clases.Persona;

public interface DAOPersona {
	public List<Persona> getPersonas();
	public Persona obtenerPersona(int id);
	public void guardar(Persona p);
	public void modificar(Persona p);
	public void eliminar(Persona p);
	public List<Persona> gerPersonas(String condicion);
	public List<Persona> getPersonas(char tipo);
	public int getUltimoIndice();
	public boolean existe(String dni);
}
