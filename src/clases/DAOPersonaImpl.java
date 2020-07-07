package clases;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import utils.HibernateUtils;

public class DAOPersonaImpl implements DAOPersona{

	private static Session session;
	
	@Override
	public List<Persona> getPersonas() {
		session = HibernateUtils.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("SELECT p FROM Persona p");
		List<Persona> list_persona  = query.list();
		session.getTransaction().commit();
		session.close();
		return list_persona;
	}

	@Override
	public void guardar(Persona p) {
		session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(p);
			tx.commit();
			JOptionPane.showMessageDialog(null,
			        "Cliente agregado correctamente",
			        "Cliente agregado",
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
	public void modificar(Persona p) {
		session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.update(p);
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

	@Override
	public void eliminar(Persona p) {
		session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        tx.begin();
		try {
			 Persona persona = session.get(Persona.class, p.getDni());
			 if(persona != null){
		        session.delete(persona);
		        System.out.println("Borrado");
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

}
