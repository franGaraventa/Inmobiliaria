package clases;

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
@Table(name="fecha_pautada")
public class FechaPautada implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="fecha")
	private Date fecha;
	
	@Column(name = "porcentaje")
	private double porcentaje;
	
	@ManyToOne
	@JoinColumn(name="ppid")
	private PrecioPorcentual ppid;

	public FechaPautada() {
	}

	public FechaPautada(Date fecha, double porcentaje,PrecioPorcentual ppid) {
		this.fecha = fecha;
		this.porcentaje = porcentaje;
		this.ppid = ppid;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getPorcentaje() {
		return this.porcentaje;
	}

	public void setPorcentaje(double porcentaje) {
		this.porcentaje = porcentaje;
	}

	public PrecioPorcentual getPpid() {
		return this.ppid;
	}

	public void setPpid(PrecioPorcentual ppid) {
		this.ppid = ppid;
	}

}
