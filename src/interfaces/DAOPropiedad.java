package interfaces;

import java.util.List;

import clases.Propiedad;
import clases.Ubicacion;

public interface DAOPropiedad {

	public List<Propiedad> getPropiedades();
	public void agregar(Propiedad p);
	public void modificar(Propiedad p);
	public List<Propiedad> getPropiedad(Ubicacion u);
	public Propiedad obtenerPropiedad(int id); 
	public void eliminar(Propiedad p);
	public int getUltimoIndice();
	
}
