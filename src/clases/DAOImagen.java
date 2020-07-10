package clases;

import java.util.List;

public interface DAOImagen {

	public List<Imagen> getImagenes(Propiedad p);
	public void agregar(Imagen i);
	public void modificar(Imagen i);
	public boolean existeImagen(String path,Propiedad p);
	public void eliminarImagen(String path,Propiedad p);
	public int getUltimoIndice();
	public int idImagen(String path,Propiedad p);
}
