package clases;

import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import utils.HibernateUtils;

@SuppressWarnings("deprecation")
public class DAOPersonaImpl implements DAOPersona{

	private static Session session;
	
	@Override
	public List<Persona> getPersonas() {
		session = HibernateUtils.getSessionFactory().openSession();
		session.beginTransaction();
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery("SELECT p FROM Persona p");
		@SuppressWarnings("unchecked")
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
			Persona persona = session.get(Persona.class, p.getDni());
			if (persona != null) {
				if (persona.getDni().equals(p.getDni())) {
					session.save(p);
					tx.commit();
					JOptionPane.showMessageDialog(null,
							"Cliente agregado correctamente",
							"Cliente agregado",
							JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "El DNI ya fue ingresado","DNI Repetido",JOptionPane.WARNING_MESSAGE);
				}
			}else {
				session.save(p);
				tx.commit();
				JOptionPane.showMessageDialog(null,
						"Cliente agregado correctamente",
						"Cliente agregado",
						JOptionPane.INFORMATION_MESSAGE);
			}
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
			session.merge(p);
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
		        JOptionPane.showMessageDialog(null,
				        "Cliente eliminado correctamente",
				        "Cliente eliminado",
				        JOptionPane.INFORMATION_MESSAGE);
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
	public Persona obtenerPersona(String dni) {
		session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        try {
			 Persona persona = session.get(Persona.class, dni);
			 tx.commit();
			 return persona;
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
