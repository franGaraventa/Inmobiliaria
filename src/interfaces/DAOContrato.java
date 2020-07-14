package interfaces;

import java.util.List;
import clases.Contrato;
import clases.Persona;

public interface DAOContrato {

	public List<Contrato> getContratos();
	public void agregar(Contrato c);
	public void modificar(Contrato c);
	public void eliminar(Contrato c);
	public List<Contrato> getContratos(Persona p);
	public List<Contrato> getContratos(String condicion);
	
}
