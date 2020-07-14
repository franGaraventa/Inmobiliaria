package interfaces;

import java.util.List;
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

	@Override
	public int getUltimoIndice() {
		session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        tx.begin();
		try {
			Object id = session.createQuery("SELECT MAX(id) FROM TipoPrecio").uniqueResult();
			tx.commit();
			if (id != null) {
				return (Integer) id;
			}
		}catch(Exception e) {
			if (tx != null) {
	            tx.rollback();
	         }
	         e.printStackTrace();
		}finally {
			session.close();
		}	
		return 0;
	}

}
