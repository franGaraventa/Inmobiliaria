package interfaces;

import java.util.List;
import clases.Pagos;

public interface DAOPagos {

	public List<Pagos> getPagos();
	public boolean existePago(int id,int mes,int anio);
	public void agregar(Pagos p);
	public List<Pagos> getPagos(int id);	//Obtener los pagos de un contrato
}
