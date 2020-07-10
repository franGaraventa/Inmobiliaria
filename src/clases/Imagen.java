package clases;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.sql.rowset.serial.SerialBlob;

@Entity
@Table(name="imagen")
public class Imagen implements java.io.Serializable {

	@Id
	@Column(name="id")
	private Integer id;
	
	@Column(name="pathImg")
	private String pathImg;
	
	@Column(name="imagen")
	private Blob imagen;
	
	@ManyToOne
	@JoinColumn(name="propiedadId")
	private Propiedad propiedad;

	public Imagen() {
	}

	public Imagen(int id,String pathImg,Blob imagen,Propiedad propiedad) {
		this.id = id;
		this.pathImg = pathImg;
		this.imagen = imagen;
		this.propiedad = propiedad;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPathImg() {
		return this.pathImg;
	}

	public void setPathImg(String pathImg) {
		this.pathImg = pathImg;
	}

	public Blob getImagen() {
		return this.imagen;
	}

	public void setImagen(Blob imagen) {
		this.imagen = imagen;
	}

	public Propiedad getPropiedadId() {
		return this.propiedad;
	}

	public void setPropiedadId(Propiedad propiedad) {
		this.propiedad = propiedad;
	}

}
