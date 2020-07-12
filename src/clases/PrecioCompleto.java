package clases;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="precio_completo")
public class PrecioCompleto extends TipoPrecio implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name="cantMesesContrato")
	private int cantMesesContrato;
	
	public PrecioCompleto() {
	}

	public PrecioCompleto(double precio_base,double expensas,int cantMesesContrato) {
		super(precio_base,expensas);
		this.cantMesesContrato = cantMesesContrato;
	}

	@Override
	public double getPrecio() {
		return (this.getPrecioBase()/this.cantMesesContrato)+this.getExpensas();
	}

	public int getCantMesesContrato() {
		return cantMesesContrato;
	}

	public void setCantMesesContrato(int cantMesesContrato) {
		this.cantMesesContrato = cantMesesContrato;
	}
	
}
