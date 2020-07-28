package interfaces;

import java.util.List;

import clases.Cliente;

public interface DAOCliente {

	public List<Cliente> getClientes();
	public Cliente getCliente(int id);
	public void modificar(Cliente c);
}
