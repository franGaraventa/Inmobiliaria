package interfaces;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import clases.Contrato;
import clases.Propiedad;
import clases.Ubicacion;
import utils.HibernateUtils;

@SuppressWarnings("deprecation")
public class DAOPropiedadImpl implements DAOPropiedad{

	private static Session session;
	
	@Override
	public List<Propiedad> getPropiedades() {
		session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery("SELECT p FROM Propiedad p"); 
		@SuppressWarnings("unchecked")
		List<Propiedad> propiedades = query.list();
		tx.commit();
		session.close();
		return propiedades;
	}

	@Override
	public void agregar(Propiedad p) {
		session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
	    try {
			session.save(p);
			tx.commit();
			JOptionPane.showMessageDialog(null,
			        "Propiedad agregada correctamente",
			        "Propiedad agregada",
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
	public void modificar(Propiedad p) {
		session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.merge(p);
			tx.commit();
			JOptionPane.showMessageDialog(null,
			        "Propiedad modificada correctamente",
			        "Modificar propiedad",
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

	@Override
	public List<Propiedad> getPropiedad(Ubicacion u) {
		session = HibernateUtils.getSessionFactory().openSession();
		@SuppressWarnings("unused")
		Transaction tx = session.beginTransaction();
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery("select propiedad from Propiedad as propiedad inner join Ubicacion as ubicacion where ubicacion.id = :id");
		query.setParameter("id", u.getId());
		@SuppressWarnings("unchecked")
		List<Propiedad> propiedades = query.list();
		return propiedades;
	}

	@Override
	public void eliminar(Propiedad p) {
		session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        tx.begin();
		try {
			 Propiedad propiedad = session.get(Propiedad.class, p.getId());
			 if(propiedad != null){
		        session.delete(propiedad);
		     }
			 tx.commit();
		}catch(Exception e) {
			if (tx != null) {
	            tx.rollback();
	         }
	         e.printStackTrace();
		}finally {
			session.close();
		}
	}

	@Override
	public Propiedad obtenerPropiedad(int id) {
		session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        try {
			 Propiedad propiedad = session.get(Propiedad.class, id);
			 tx.commit();
		     return propiedad;	 
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

	@Override
	public int getUltimoIndice() {
		session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        tx.begin();
		try {
			Object id = session.createQuery("SELECT MAX(id) FROM Propiedad").uniqueResult();
			tx.commit();
			if (id != null) {
				return (Integer)id;
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
