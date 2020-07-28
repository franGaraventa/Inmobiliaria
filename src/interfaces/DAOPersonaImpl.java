package interfaces;

import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import clases.Persona;
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
			session.save(p);
			tx.commit();
			JOptionPane.showMessageDialog(null,
					"Persona agregada correctamente",
					"Persona agregada",
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
			 Persona persona = session.get(Persona.class, p.getId());
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
	public Persona obtenerPersona(int id) {
		session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        try {
			 Persona persona = session.get(Persona.class, id);
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

	@Override
	public List<Persona> gerPersonas(String condicion) {
		session = HibernateUtils.getSessionFactory().openSession();
		session.beginTransaction();
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery(condicion);
		@SuppressWarnings("unchecked")
		List<Persona> list_persona  = query.list();
		session.getTransaction().commit();
		session.close();
		return list_persona;
	}

	@Override
	public List<Persona> getPersonas(char tipo) {
		session = HibernateUtils.getSessionFactory().openSession();
		session.beginTransaction();
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery("SELECT p FROM Persona p where p.tipo = :tipo");
		query.setParameter("tipo", tipo);
		@SuppressWarnings("unchecked")
		List<Persona> list_persona  = query.list();		
		session.getTransaction().commit();
		session.close();
		return list_persona;
	}

	@Override
	public int getUltimoIndice() {
		session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        tx.begin();
		try {
			Object id = session.createQuery("SELECT MAX(id) FROM Persona").uniqueResult();
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

	@Override
	public boolean existe(String dni) {
		session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        tx.begin();
		try {
			Query query = session.createQuery("SELECT p FROM Persona as p where p.dni = :dni");
			query.setParameter("dni", dni);
			List<Persona> p = query.list();
			tx.commit();
			if (p.size() > 0) {
				return true;
			}else {
				System.out.println("TIENE QUE ENTRAR ACA");
				return false;
			}
		}catch(Exception e) {
			if (tx != null) {
	            tx.rollback();
	         }
	         e.printStackTrace();
		}finally {
			session.close();
		}	
		return false;
	}

}
