package models.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import models.Category;
import models.DatabaseUtil;
import models.Game;

public class CategoryDao {

	private SessionFactory sessionFactory;
	public CategoryDao() {
		sessionFactory = DatabaseUtil.getSessionFactory();
		
	}
	
	public List<Category> getAll() {
		    Session session = sessionFactory.openSession();
	 	   Transaction tx = null;
				List<Category> categories = null;
				try {
		         tx = session.beginTransaction();
		         categories = session.createQuery("FROM Category").list();
		         tx.commit();
		      } catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      } finally {
		         session.close(); 
		      }
		return categories;
	}
	
	public Category add(Category category) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(category);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return category;
	}
	
	public Category remove(int id) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Category category = null;
		try {
			tx = session.beginTransaction();
			System.out.print(id);
			category = session.get(Category.class, id);
			session.delete(category);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return category;
	}
	
	public void update(Category category) {
		Session session = sessionFactory.openSession();
	      Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
			 session.update(category); 
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	}
}
