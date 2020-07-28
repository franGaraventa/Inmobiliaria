package interfaces;

import java.util.List;

import clases.Locador;

public interface DAOLocador {

	public List<Locador> getLocadores();
	public Locador getLocador(int id);
	
}
