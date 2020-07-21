package interfaces;

import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import clases.Pagos;
import utils.HibernateUtils;

@SuppressWarnings("deprecation")
public class DAOPagosImpl implements DAOPagos{

	private static Session session;
	
	@Override
	public List<Pagos> getPagos() {
		session = HibernateUtils.getSessionFactory().openSession();
		session.beginTransaction();
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery("SELECT p FROM Pagos p");
		@SuppressWarnings("unchecked")
		List<Pagos> pagos  = query.list();		
		session.getTransaction().commit();
		session.close();
		return pagos;
	}

	@Override
	public void agregar(Pagos p) {
		session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
	    try {
	    	session.save(p);
			tx.commit();
			JOptionPane.showMessageDialog(null,
			        "Pago agregado correctamente",
			        "Pago agregado",
			        JOptionPane.INFORMATION_MESSAGE);
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
	public boolean existePago(int id, int mes,int anio) {
		session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
	    try {
	    	@SuppressWarnings("rawtypes")
			Query query = session.createQuery("SELECT p.fecha FROM Pagos as p join Contrato as c on (c.id = p.cid) where c.id = :id and MONTH(p.fecha) = :mes and YEAR(p.fecha) = :anio");
	    	query.setParameter("id", id);
	    	query.setParameter("mes",mes);
	    	query.setParameter("anio", anio);
	    	Date fecha = (Date) query.uniqueResult();
	    	tx.commit();
	    	if (fecha != null) {
	    		return true;
	    	}else {
	    		return false;
	    	}
		}catch(Exception e) {
			if (tx != null) {
	            tx.rollback();
	         }
	         e.printStackTrace();
		}finally{
			session.close();
		}
		return false;
	}

	@Override
	public List<Pagos> getPagos(int id) {
		session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
	    try {
	    	@SuppressWarnings("rawtypes")
			Query query = session.createQuery("SELECT p FROM Pagos as p join Contrato as c on (c.id = p.cid) where c.id = :id");
	    	query.setParameter("id", id);
	    	@SuppressWarnings("unchecked")
			List<Pagos> pagos = query.list();
	    	tx.commit();
	    	return pagos;
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
