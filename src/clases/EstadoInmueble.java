package clases;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="estado_inmueble")
public class EstadoInmueble {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name="cid")
	private Contrato cid;

	@Column(name="clave")
	private String clave;
	
	@Column(name="dato")
	private String dato;
	
	/*CONSTRUCTORES*/
	/*-----------------------------------------------------------------------------------------------*/
	public EstadoInmueble() {
	}

	public EstadoInmueble(Contrato cid, String clave, String dato) {
		this.cid = cid;
		this.clave = clave;
		this.dato = dato;
	}
	/*-----------------------------------------------------------------------------------------------*/
	
	/*GETTERS Y SETTERS*/
	/*-----------------------------------------------------------------------------------------------*/
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Contrato getCid() {
		return cid;
	}

	public void setCid(Contrato cid) {
		this.cid = cid;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getDato() {
		return dato;
	}

	public void setDato(String dato) {
		this.dato = dato;
	}
	/*----------------------------------------------------------------------------------------------*/
	
}
