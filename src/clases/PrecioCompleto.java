package clases;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="precio_completo")
public class PrecioCompleto extends TipoPrecio implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name="cantMesesContrato")
	private Integer cantMesesContrato;
	
	/*CONSTRUCTORES*/
	/*-----------------------------------------------------------------------------------------------*/
	public PrecioCompleto() {
	}

	public PrecioCompleto(Integer id,double precio_base,double expensas,int cantMesesContrato) {
		super(id,precio_base,expensas);
		this.cantMesesContrato = cantMesesContrato;
	}

	/*GETTERS Y SETTERS*/
	/*-----------------------------------------------------------------------------------------------*/
	@Override
	public double getPrecio() {
		return (this.getPrecioBase()/this.cantMesesContrato)+this.getExpensas();
	}

	public Integer getCantMesesContrato() {
		return cantMesesContrato;
	}

	public void setCantMesesContrato(Integer cantMesesContrato) {
		this.cantMesesContrato = cantMesesContrato;
	}
	/*-----------------------------------------------------------------------------------------------*/
}
