package interfaces;

import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import clases.Cliente;
import clases.Contrato;
import clases.Locador;
import clases.Pagos;
import clases.Propiedad;
import utils.Fechas;
import utils.HibernateUtils;

@SuppressWarnings("deprecation")
public class DAOContratoImpl implements DAOContrato{

	private static Session session;
	
	@Override
	public List<Contrato> getContratos() {
		session = HibernateUtils.getSessionFactory().openSession();
		session.beginTransaction();
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery("SELECT c FROM Contrato c");
		@SuppressWarnings("unchecked")
		List<Contrato> contratos  = query.list();		
		session.getTransaction().commit();
		session.close();
		return contratos;
	}

	@Override
	public void agregar(Contrato c) {
		session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(c);
			tx.commit();
			JOptionPane.showMessageDialog(null,
				"Contrato generado correctamente",
				"Contrato agregado",
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
	public void modificar(Contrato c) {
		session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.merge(c);
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
	public void eliminar(Contrato c) {
		session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        tx.begin();
		try {
			DAOPagos ipagos = new DAOPagosImpl();
			List<Pagos> pagos = ipagos.getPagos(c.getId());
			for (Pagos p: pagos) {
				session.delete(p);
				session.flush();
				session.clear();
			}
		    session.delete(c);
		    JOptionPane.showMessageDialog(null,
				  "Contrato eliminado correctamente",
				  "Contrato eliminado",
				  JOptionPane.INFORMATION_MESSAGE);
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
	public Contrato getContrato(int id) {
		session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        try {
			 Contrato contrato = session.get(Contrato.class, id);
			 tx.commit();
			 return contrato;
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
	public List<Pagos> getPagos(int id) {
		session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        try {
			 @SuppressWarnings("rawtypes")
			Query query = session.createQuery("select p from Contrato as c join Pagos as p on (p.cid = c.id) where c.id = :id");
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
		}finally {
			session.close();
		}
		return null;
	}

	@Override
	public List<Contrato> getContratosVigentes(Date fecha) {
		session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        try {
			 @SuppressWarnings("rawtypes")
			Query query = session.createQuery("from Contrato as c where :fecha < c.fechaFinalizacion");
			 query.setParameter("fecha", fecha);
			 @SuppressWarnings("unchecked")
			List<Contrato> contratosVigentes = query.list();
			 tx.commit();
			 return contratosVigentes;
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
	public boolean contratoVigenteConCliente(int id,Date fecha) {
		session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        try {
			@SuppressWarnings("rawtypes")
			Query query = session.createQuery("select cl from Contrato as c join Cliente as cl on (cl.id = c.locatario) where cl.id = :id and :fecha < c.fechaFinalizacion");
			query.setParameter("id", id);
			query.setParameter("fecha", fecha);
			@SuppressWarnings("unchecked")
			List<Cliente> cl = query.list();
			if (cl.size() > 0) {
				return true;
			}else {
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

	@Override
	public boolean contratoVigenteConPropiedad(int id,Date fecha) {
		session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        try {
			@SuppressWarnings("rawtypes")
			Query query = session.createQuery("select p from Contrato as c join Propiedad as p on (c.locacion = p.id) where p.id = :id and :fecha < c.fechaFinalizacion");
			query.setParameter("id", id);
			query.setParameter("fecha", fecha);
			@SuppressWarnings("unchecked")
			List<Propiedad> p = query.list();
			if (p.size() > 0) {
				return true;
			}else {
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

	@Override
	public boolean contratoVigente(int id, Date fecha) {
		session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        try {
        	Contrato contrato = session.get(Contrato.class, id);
        	if (Fechas.compararFechas(contrato.getFechaFinalizacion()) >= 0) {
        		return true;
        	}else {
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

	@Override
	public boolean contratoVigenteConLocador(int id, Date fecha) {
		session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        try {
			@SuppressWarnings("rawtypes")
			Query query = session.createQuery("select l from Contrato as c join Locador as l on (l.id = c.locador) where l.id = :id and :fecha < c.fechaFinalizacion");
			query.setParameter("id", id);
			query.setParameter("fecha", fecha);
			@SuppressWarnings("unchecked")
			List<Locador> l = query.list();
			if (l.size() > 0) {
				return true;
			}else {
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
