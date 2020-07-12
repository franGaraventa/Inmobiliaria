package clases;
import java.util.Date;

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
@Table(name="contrato")
public class Contrato implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="plazo")
	private int plazo;
	
	@Column(name="fechaFirma")
	private Date fechaFirma;
	
	@Column(name="fechaInicio")
	private Date fechaInicio;
	
	@Column(name="fechaFinalizacion")
	private Date fechaFinalizacion;
	
	@Column(name="locador")
	private String locador;
	
	@OneToOne(cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Persona locatario;
	
	@OneToOne(cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private TipoPrecio precio;
	
	@Column(name="fechaMaxPago")
	private int fechaMaxPago;
	
	@OneToOne(cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Propiedad locacion;
	
	@Column(name="garantia")
	private double garantia;
	
	@Column(name="gastosInmobiliaria")
	private double gastosInmobiliaria;

	public Contrato() {
	}

	public Contrato(int plazo, Date fechaFirma, Date fechaInicio, Date fechaFinalizacion, String locador, Persona locatario,
			TipoPrecio precio, Propiedad locacion, int garantia, int gastosInmobiliaria) {
		this.plazo = plazo;
		this.fechaFirma = fechaFirma;
		this.fechaInicio = fechaInicio;
		this.fechaFinalizacion = fechaFinalizacion;
		this.locador = locador;
		this.locatario = locatario;
		this.precio = precio;
		this.locacion = locacion;
		this.garantia = garantia;
		this.gastosInmobiliaria = gastosInmobiliaria;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getPlazo() {
		return this.plazo;
	}

	public void setPlazo(int plazo) {
		this.plazo = plazo;
	}

	public Date getFechaFirma() {
		return this.fechaFirma;
	}

	public void setFechaFirma(Date fechaFirma) {
		this.fechaFirma = fechaFirma;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFinalizacion() {
		return this.fechaFinalizacion;
	}

	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}

	public String getLocador() {
		return this.locador;
	}

	public void setLocador(String locador) {
		this.locador = locador;
	}

	public Persona getLocatario() {
		return this.locatario;
	}

	public void setLocatario(Persona locatario) {
		this.locatario = locatario;
	}

	public TipoPrecio getPrecio() {
		return this.precio;
	}

	public void setPrecio(TipoPrecio precio) {
		this.precio = precio;
	}

	public Integer getFechaMaxPago() {
		return this.fechaMaxPago;
	}

	public void setFechaMaxPago(Integer fechaMaxPago) {
		this.fechaMaxPago = fechaMaxPago;
	}

	public Propiedad getLocacion() {
		return this.locacion;
	}

	public void setLocacion(Propiedad locacion) {
		this.locacion = locacion;
	}

	public double getGarantia() {
		return this.garantia;
	}

	public void setGarantia(double garantia) {
		this.garantia = garantia;
	}

	public double getGastosInmobiliaria() {
		return this.gastosInmobiliaria;
	}

	public void setGastosInmobiliaria(double gastosInmobiliaria) {
		this.gastosInmobiliaria = gastosInmobiliaria;
	}

}
