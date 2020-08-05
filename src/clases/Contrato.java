package clases;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import javax.persistence.Table;
import Filtros.FContrato;
import interfaces.DAOContrato;
import interfaces.DAOContratoImpl;
import interfaces.DAOPagos;
import interfaces.DAOPagosImpl;

@Entity
@Table(name="contrato")
public class Contrato implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="plazo")
	private Integer plazo;
	
	@Column(name="fechaFirma")
	private Date fechaFirma;
	
	@Column(name="fechaInicio")
	private Date fechaInicio;
	
	@Column(name="fechaFinalizacion")
	private Date fechaFinalizacion;
	
	@OneToOne(orphanRemoval = false)
	@JoinColumn(name = "locador", referencedColumnName = "id")
	private Persona locador;
	
	@OneToOne(orphanRemoval = false)
	@JoinColumn(name = "locatario", referencedColumnName = "id")
	private Persona locatario;
	
	@OneToOne(orphanRemoval = false)
	@JoinColumn(name = "precio", referencedColumnName = "id")
	private TipoPrecio precio;
	
	@Column(name="fechaMaxPago")
	private Integer fechaMaxPago;
	
	@OneToOne(orphanRemoval = false)
	@JoinColumn(name = "locacion", referencedColumnName = "id")
	private Propiedad locacion;
	
	@Column(name="garantia")
	private double garantia;
	
	@Column(name="gastosInmobiliaria")
	private double gastosInmobiliaria;

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="cid")
	private List<Pagos> pagos;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="cid")
	private List<EstadoInmueble> estado_inmueble;
	
	public Contrato() {
	}

	public Contrato(Integer plazo, Date fechaFirma, Date fechaInicio, Date fechaFinalizacion, Persona locatario,
			TipoPrecio precio, Propiedad locacion, double garantia, double gastosInmobiliaria) {
		this.plazo = plazo;
		this.fechaFirma = fechaFirma;
		this.fechaInicio = fechaInicio;
		this.fechaFinalizacion = fechaFinalizacion;
		this.locatario = locatario;
		this.precio = precio;
		this.locacion = locacion;
		this.garantia = garantia;
		this.gastosInmobiliaria = gastosInmobiliaria;
		this.pagos = new ArrayList<Pagos>();
		this.estado_inmueble = new ArrayList<EstadoInmueble>();
	}
	
	public Contrato(Integer plazo,Integer fechaMaxPago, Date fechaFirma, Date fechaInicio, Date fechaFinalizacion, double garantia, double gastosInmobiliaria) {
		this.plazo = plazo;
		this.fechaFirma = fechaFirma;
		this.fechaInicio = fechaInicio;
		this.fechaFinalizacion = fechaFinalizacion;
		this.garantia = garantia;
		this.gastosInmobiliaria = gastosInmobiliaria;
		this.fechaMaxPago = fechaMaxPago;
		this.pagos = new ArrayList<Pagos>();
		this.estado_inmueble = new ArrayList<EstadoInmueble>();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPlazo() {
		return this.plazo;
	}

	public void setPlazo(Integer plazo) {
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

	public Persona getLocador() {
		return this.locador;
	}

	public void setLocador(Persona locador) {
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

	public List<Pagos> getPagos() {
		return pagos;
	}

	public void setPagos(List<Pagos> pagos) {
		this.pagos = pagos;
	}
	
	public boolean cumple(FContrato filtro) {
		return (filtro.cumple(this));
	}

	public boolean cumpleFaltaDePago() {
		DAOContrato icontrato = new DAOContratoImpl();
		List<Pagos> list_pagos = icontrato.getPagos(this.getId());
		if (list_pagos.size() == 0) {
			return true;
		}else {
			Calendar fecha_actual = Calendar.getInstance();
			int mes = fecha_actual.get(Calendar.MONTH);
			int anio = fecha_actual.get(Calendar.YEAR);
			DAOPagos ipagos = new DAOPagosImpl();
			if (!ipagos.existePago(anio, mes, this.getId())) {
				/*CONTROLO QUE EL MES ANTERIOR AL MES ACTUAL TAMPOCO ESTE PAGO*/
				if ((mes-1) < 0) {
					mes = 12;
					anio-=1;
				}
				if (!ipagos.existePago(anio, mes, this.getId())) {
					return true;
				}
			}else {
				return false;
			}
		}
		return false;
	}

	
	public List<EstadoInmueble> getEstado_inmueble() {
		return estado_inmueble;
	}

	
	public void setEstado_inmueble(List<EstadoInmueble> estado_inmueble) {
		this.estado_inmueble = estado_inmueble;
	}


}
