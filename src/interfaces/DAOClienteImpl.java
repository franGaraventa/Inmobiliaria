package interfaces;

import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import clases.Cliente;
import utils.HibernateUtils;

@SuppressWarnings("deprecation")
public class DAOClienteImpl implements DAOCliente{

	private static Session session;
	
	@Override
	public List<Cliente> getClientes() {
		session = HibernateUtils.getSessionFactory().openSession();
		session.beginTransaction();
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery("SELECT c FROM Cliente c");
		@SuppressWarnings("unchecked")
		List<Cliente> listclientes  = query.list();		
		session.getTransaction().commit();
		session.close();
		return listclientes;
	}

	@Override
	public Cliente getCliente(int id) {
		session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        try {
			 Cliente cliente = session.get(Cliente.class, id);
			 tx.commit();
			 return cliente;
        }catch(Exception e) {
			if (tx != null) {
	            tx.rollback();
	         }
	         e.printStackTrace();
		}finally {
			session.close();
		}
		return null;
	}

	public void modificar(Cliente c) {
		session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.merge(c);
			tx.commit();
			JOptionPane.showMessageDialog(null,
			        "Cliente modificado correctamente",
			        "Modificar cliente",
			        JOptionPane.INFORMATION_MESSAGE);
		}catch(Exception e) {
			if (tx != null) {
	            tx.rollback();
	         }
	         e.printStackTrace();
		}finally {
			session.close();
		}
	}
	
}
