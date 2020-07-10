package clases;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import utils.HibernateUtils;

@SuppressWarnings("deprecation")
public class DAOImagenImpl implements DAOImagen{

	private static Session session;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Imagen> getImagenes(Propiedad p) {
		session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		List<Imagen> imagenes = new ArrayList<Imagen>();
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery("from Imagen where propiedadId = :id");
		query.setParameter("id", p.getId());
		imagenes = query.list();
		tx.commit();
		return imagenes;
	}

	@Override
	public void agregar(Imagen i) {
		session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
	    try {
	    	session.save(i);
			tx.commit();
			JOptionPane.showMessageDialog(null,
			        "Imagen agregada correctamente",
			        "Imagen agregada",
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
	public void modificar(Imagen i) {
	}

	@Override
	public boolean existeImagen(String path, Propiedad p) {
		session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        try {
        	@SuppressWarnings("rawtypes")
			Query query = session.createQuery("from Imagen where propiedadId = :id AND pathImg = :path");
    		query.setParameter("id", p.getId());
    		query.setParameter("path", path);
			@SuppressWarnings("unchecked")
			List<Imagen> img = query.list(); 
    		if(img.size() != 0){
		        return true;
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
        return false;
	}

	@Override
	public void eliminarImagen(String path, Propiedad p) {
		session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        try {
        	@SuppressWarnings("rawtypes")
			Query query = session.createQuery("delete from Imagen where propiedadId = :id AND pathImg = :path");
    		query.setParameter("id", p.getId());
    		query.setParameter("path", path);
    		query.executeUpdate();
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
	public int getUltimoIndice() {
		session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        tx.begin();
		try {
			Object id = session.createQuery("SELECT MAX(id) FROM Imagen").uniqueResult();
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
	public int idImagen(String path, Propiedad p) {
		session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        try {
        	@SuppressWarnings("rawtypes")
			Query query = session.createQuery("from Imagen where propiedadId = :id AND pathImg = :path");
    		query.setParameter("id", p.getId());
    		query.setParameter("path", path);
			@SuppressWarnings("unchecked")
			List<Imagen> img = query.list(); 
    		if(img.size() != 0){
		        return img.get(0).getId();
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
        return -1;
	}



}
