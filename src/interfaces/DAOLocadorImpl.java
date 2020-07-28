package interfaces;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import clases.Locador;
import utils.HibernateUtils;

@SuppressWarnings("deprecation")
public class DAOLocadorImpl implements DAOLocador{

	private static Session session;
	
	@Override
	public List<Locador> getLocadores() {
		session = HibernateUtils.getSessionFactory().openSession();
		session.beginTransaction();
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery("SELECT l FROM Locador l");
		@SuppressWarnings("unchecked")
		List<Locador> listlocadores  = query.list();		
		session.getTransaction().commit();
		session.close();
		return listlocadores;
	}

	@Override
	public Locador getLocador(int id) {
		session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        try {
			 Locador locador = session.get(Locador.class, id);
			 tx.commit();
			 return locador;
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

}
