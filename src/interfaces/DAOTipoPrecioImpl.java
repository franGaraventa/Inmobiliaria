package interfaces;

import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Session;
import org.hibernate.Transaction;

import clases.TipoPrecio;
import utils.HibernateUtils;

public class DAOTipoPrecioImpl implements DAOTipoPrecio{

	private static Session session;
	
	@Override
	public void agregar(TipoPrecio tp) {
		session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(tp);
			tx.commit();
		}catch(Exception e) {
			if (tx != null) {
	            tx.rollback();
	         }
	         e.printStackTrace();
		}finally{
			session.close();
		}
	}

	@Override
	public void modificar(TipoPrecio tp) {
	}

	@Override
	public void eliminar(TipoPrecio tp) {
	}

	@Override
	public List<TipoPrecio> getTipoPrecios() {
		return null;
	}

}
