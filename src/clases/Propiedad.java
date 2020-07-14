package clases;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="propiedad")
public class Propiedad implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="valor")
	private double valor;
	
	@Column(name="supLote")
	private double supLote;
	
	@Column(name="supCubierta")
	private double supCubierta;
	
	@Column(name="informacion")
	private String informacion;
	
	@Column(name="amoblado")
	private boolean amoblado;
	
	@OneToOne(cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Ubicacion ubicacion;

	@OneToMany(cascade= CascadeType.ALL)
	@JoinColumn(name="propiedadId")
	private List<Imagen> imagenes;
	
	public Propiedad() {
	}

	public Propiedad(int id,double valor, double supLote, double supCubierta, String informacion, boolean amoblado) {
		this.id = id;
		this.valor = valor;
		this.supLote = supLote;
		this.supCubierta = supCubierta;
		this.informacion = informacion;
		this.amoblado = amoblado;
		this.imagenes = new ArrayList<Imagen>();
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getValor() {
		return this.valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getSupLote() {
		return this.supLote;
	}

	public void setSupLote(int supLote) {
		this.supLote = supLote;
	}

	public double getSupCubierta() {
		return this.supCubierta;
	}

	public void setSupCubierta(int supCubierta) {
		this.supCubierta = supCubierta;
	}

	public String getInformacion() {
		return this.informacion;
	}

	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}

	public boolean isAmoblado() {
		return this.amoblado;
	}

	public void setAmoblado(boolean amoblado) {
		this.amoblado = amoblado;
	}

	public void setUbicacion(Ubicacion u) {
		this.ubicacion = u;
	}
	
	public Ubicacion getUbicacion() {
		return this.ubicacion;
	}

	public List<Imagen> getImagenes(){
		return this.imagenes;
	}
	
	public void setImagenes(List<Imagen> imagenes) {
		this.imagenes = imagenes;
	}
}
