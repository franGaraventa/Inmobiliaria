package clases;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="ubicacion")
public class Ubicacion implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private Integer id;
	
	@Column(name="codPostal")
	private String codPostal;
	
	@Column(name="distrito")
	private String distrito;
	
	@Column(name="ciudad")
	private String ciudad;
	
	@Column(name="direccion")
	private String direccion;

	@OneToOne(cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Propiedad propiedad;
	
	/*CONSTRUCTORES*/
	/*-----------------------------------------------------------------------------------------------*/
	public Ubicacion() {
	}

	public Ubicacion(Integer id, String codPostal, String distrito, String ciudad, String direccion) {
		this.id = id;
		this.codPostal = codPostal;
		this.distrito = distrito;
		this.ciudad = ciudad;
		this.direccion = direccion;
	}
	/*-----------------------------------------------------------------------------------------------*/
	
	/*GETTERS Y SETTERS*/
	/*-----------------------------------------------------------------------------------------------*/
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodPostal() {
		return this.codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}

	public String getDistrito() {
		return this.distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	public String getCiudad() {
		return this.ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Propiedad getPropiedad() {
		return this.propiedad;
	}
	
	public void setPropiedad(Propiedad p) {
		this.propiedad = p;
	}
	/*-----------------------------------------------------------------------------------------------*/
}
