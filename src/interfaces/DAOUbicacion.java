package interfaces;

import java.util.List;

import clases.Ubicacion;

public interface DAOUbicacion {

	public List<Ubicacion> getUbicaciones();
	public void agregar(Ubicacion u);
	public void modificar(Ubicacion u);
	public void eliminar(Ubicacion u);
	public int cantidadFilas();
}
