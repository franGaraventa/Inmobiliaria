package clases;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="cliente")
public class Cliente extends Persona implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public Cliente() {
	}

	public Cliente(int id,String dni, String nombre, String apellido, String email, String codArea, String telefono,
			String direccion) {
		super(id,dni,nombre,apellido,email,codArea,telefono,direccion);
	}

	@Override
	public CuentaBancaria getCuentaBancaria() {
		return null;
	}

}
