package clases;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import utils.HibernateUtils;

@SuppressWarnings("deprecation")
public class DAOUbicacionImpl implements DAOUbicacion{

	private static Session session;
	
	@Override
	public List<Ubicacion> getUbicaciones() {
		return null;
	}

	@Override
	public void agregar(Ubicacion u) {
	}

	@Override
	public void modificar(Ubicacion u) {
	}

	@Override
	public void eliminar(Ubicacion u) {
	}

	@Override
	public int cantidadFilas() {
		session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery("select count(*) from Ubicacion"); 
		@SuppressWarnings("rawtypes")
		List l = query.list();
		long cantidadFilas = (Long)l.get(0);
		tx.commit();
		session.close();
		return Math.toIntExact(cantidadFilas);
	}

}
