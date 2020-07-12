package interfaces;

import java.util.List;

import clases.TipoPrecio;

public interface DAOTipoPrecio {
	
	public void agregar(TipoPrecio tp);
	public void modificar(TipoPrecio tp);
	public void eliminar(TipoPrecio tp);
	public List<TipoPrecio> getTipoPrecios();
	
}
