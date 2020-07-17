package clases;
// Generated 15-jul-2020 15:14:54 by Hibernate Tools 5.4.14.Final

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="pagos")
public class Pagos implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@ManyToOne
	@JoinColumn(name="cid")
	private Contrato cid;
	
	@Column(name="fecha")
	private Date fecha;

	@Column(name="monto")
	private double monto;
	
	@Column(name="recargo")
	private double recargo;
	
	public Pagos() {
	}

	public Pagos(Contrato cid, Date fecha,double monto,double recargo) {
		this.cid = cid;
		this.fecha = fecha;
		this.monto = monto;
		this.recargo = recargo;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Contrato getCid() {
		return this.cid;
	}

	public void setCid(Contrato cid) {
		this.cid = cid;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public double getRecargo() {
		return recargo;
	}

	public void setRecargo(double recargo) {
		this.recargo = recargo;
	}

	
	
}
