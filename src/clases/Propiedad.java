package clases;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="propiedad")
public class Propiedad implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="valor")
	private int valor;
	
	@Column(name="supLote")
	private int supLote;
	
	@Column(name="supCubierta")
	private int supCubierta;
	
	@Column(name="informacion")
	private String informacion;
	
	@Column(name="amoblado")
	private boolean amoblado;
	
	@Column(name="alquilado")
	private boolean alquilado;
	
	@OneToOne(cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Ubicacion ubicacion;

	public Propiedad() {
	}

	public Propiedad(int id, int valor, int supLote, int supCubierta, String informacion, boolean amoblado,
			boolean alquilado) {
		this.id = id;
		this.valor = valor;
		this.supLote = supLote;
		this.supCubierta = supCubierta;
		this.informacion = informacion;
		this.amoblado = amoblado;
		this.alquilado = alquilado;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getValor() {
		return this.valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public int getSupLote() {
		return this.supLote;
	}

	public void setSupLote(int supLote) {
		this.supLote = supLote;
	}

	public int getSupCubierta() {
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

	public boolean isAlquilado() {
		return this.alquilado;
	}

	public void setAlquilado(boolean alquilado) {
		this.alquilado = alquilado;
	}

	public void setUbicacion(Ubicacion u) {
		this.ubicacion = u;
	}
	
	public Ubicacion getUbicacion() {
		return this.ubicacion;
	}

}
