package clases;

import java.util.List;

public interface DAOUbicacion {

	public List<Ubicacion> getUbicaciones();
	public void agregar(Ubicacion u);
	public void modificar(Ubicacion u);
	public void eliminar(Ubicacion u);
	
}
