package clases;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="precio_porcentual")
public class PrecioPorcentual extends TipoPrecio implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy="ppid",cascade= CascadeType.ALL)
	private List<FechaPautada> fechas;

	public PrecioPorcentual() {
	}

	public PrecioPorcentual(int id,double precio_base,double expensas) {
		super(id,precio_base,expensas);
		this.fechas = new ArrayList<FechaPautada>();
	}

	@Override
	public double getPrecio() {
		double porcentaje = getPorcentaje();
		return (this.getPrecioBase()+this.getExpensas()+(this.getPrecioBase()*porcentaje));
	}

	private boolean existeFecha(FechaPautada fp) {
		if (fechas.size() == 0) {
			return false;
		}else {
			for (FechaPautada fp2: fechas) {
				if (fp2.getFecha().equals(fp.getFecha()))
					return true;
			}
			return false;
		}
	}
	
	public void addFecha(FechaPautada fp) {
		if (!existeFecha(fp)) {
			fechas.add(fp);
		}
	}
	
	private double getPorcentaje() {
		Date fecha = new Date();
		if (fechas.size() == 1) {
			if (fecha.before(fechas.get(0).getFecha())) {
				return 0;
			}else {
				return (fechas.get(0).getPorcentaje());
			}
		}else {
			for (int i = 0; i < fechas.size();i++) {
				if ((i+1 < fechas.size())) {
					if ((fecha.after(fechas.get(i).getFecha())) && (fecha.before(fechas.get(i+1).getFecha())))
						return (fechas.get(i).getPorcentaje());
				}else {
					if (fecha.after(fechas.get(i).getFecha())) {
						return (fechas.get(i).getPorcentaje());
					}
				}
			}
		}
		return 0;
	}
	
	public List<FechaPautada> getFechaspautadas() {
		return fechas;
	}

	public void setFechaspautadas(List<FechaPautada> fechas) {
		this.fechas = fechas;
	}
	
}
