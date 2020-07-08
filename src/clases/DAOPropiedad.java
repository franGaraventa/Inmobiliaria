package clases;

import java.util.List;

public interface DAOPropiedad {

	public List<Propiedad> getPropiedades();
	public void agregar(Propiedad p);
	public void modificar(Propiedad p);
	public List<Propiedad> getPropiedad(Ubicacion u);
	public void eliminar(Propiedad p);
	
}
