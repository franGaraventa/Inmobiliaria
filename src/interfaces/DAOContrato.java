package interfaces;

import java.util.List;
import clases.Contrato;
import clases.Pagos;
import clases.Persona;

public interface DAOContrato {

	public List<Contrato> getContratos();
	public void agregar(Contrato c);
	public void modificar(Contrato c);
	public void eliminar(Contrato c);
	public Contrato getContrato(int id);
	public List<Contrato> getContratos(String condicion);
	public List<Pagos> getPagos(int id);
	
}
