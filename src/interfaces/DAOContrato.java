package interfaces;

import java.util.Date;
import java.util.List;
import clases.Contrato;
import clases.Pagos;

public interface DAOContrato {

	public List<Contrato> getContratos();
	public void agregar(Contrato c);
	public void modificar(Contrato c);
	public void eliminar(Contrato c);
	public Contrato getContrato(int id);
	public List<Pagos> getPagos(int id);
	public List<Contrato> getContratosVigentes(Date fecha);
	public boolean contratoVigenteConCliente(int id,Date fecha);
	public boolean contratoVigenteConLocador(int id,Date fecha);
	public boolean contratoVigenteConPropiedad(int id,Date fecha);
	public boolean contratoVigente(int id,Date fecha);
}
