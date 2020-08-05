package interfaces;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import clases.EstadoInmueble;
import utils.HibernateUtils;

public class DAOEstadoInmuebleImpl implements DAOEstadoInmueble{

	private static Session session;
	
	@Override
	public List<EstadoInmueble> getEstadoInmueble(int id) {
		session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
	    try {
	    	@SuppressWarnings("rawtypes")
			Query query = session.createQuery("SELECT ei FROM EstadoInmueble as ei join Contrato as c on (c.id = ei.cid) where c.id = :id");
	    	query.setParameter("id", id);
	    	@SuppressWarnings("unchecked")
			List<EstadoInmueble> estado_inmueble = query.list();
	    	tx.commit();
	    	return estado_inmueble;
		}catch(Exception e) {
			if (tx != null) {
	            tx.rollback();
	         }
	         e.printStackTrace();
		}finally{
			session.close();
		}
		return null;
	}

}
