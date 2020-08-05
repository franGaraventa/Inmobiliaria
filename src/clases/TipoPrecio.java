package clases;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name="tipo_precio")
public abstract class TipoPrecio implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="precio_base")
	private double precio_base;
	
	@Column(name="expensas")
	private double expensas;
	
	public TipoPrecio() {
	}

	public TipoPrecio(int id,double precio_base, double expensas) {
		this.id = id;
		this.precio_base = precio_base;
		this.expensas = expensas;
	}

	
	/*GETTERS y SETTERS*/
	/*-----------------------------------------------------------------------*/
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getPrecioBase() {
		return this.precio_base;
	}

	public void setPrecioBase(double precio_base) {
		this.precio_base = precio_base;
	}

	public double getExpensas() {
		return this.expensas;
	}

	public void setExpensas(double expensas) {
		this.expensas = expensas;
	}

	/*-----------------------------------------------------------------------*/
	
	public abstract double getPrecio();
}
