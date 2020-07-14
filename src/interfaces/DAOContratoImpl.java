package interfaces;

import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import clases.Contrato;
import clases.Persona;
import clases.Propiedad;
import utils.HibernateUtils;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminar(Contrato c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Contrato> getContratos(Persona p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contrato> getContratos(String condicion) {
		session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery(condicion); 
		@SuppressWarnings("unchecked")
		List<Contrato> contratos = query.list();
		tx.commit();
		session.close();
		return contratos;
	}

}
