package clases;

import javax.swing.ImageIcon;

public class ImagenPropiedad {

	private final static int IDDEFAULT = -1;
	private int id;
	private String path;
	private ImageIcon imagen;
	
	/*CONSTRUCTORES*/
	/*-----------------------------------------------------------------------------------------------*/
	public ImagenPropiedad(int id,String path,ImageIcon imagen) {
		this.id = id;
		this.imagen = imagen;
		this.path = path;
	}
	
	public ImagenPropiedad(String path,ImageIcon imagen) {
		this.id = IDDEFAULT;
		this.imagen = imagen;
		this.path = path;
	}
	/*-----------------------------------------------------------------------------------------------*/
	
	/*GETTERS Y SETTERS*/
	/*-----------------------------------------------------------------------------------------------*/
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ImageIcon getImagen() {
		return imagen;
	}

	public void setImagen(ImageIcon imagen) {
		this.imagen = imagen;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isDefault() {
		return (this.id == IDDEFAULT);
	}
	/*-----------------------------------------------------------------------------------------------*/
}
