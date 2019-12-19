package models.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import models.Bet;
import models.Category;
import models.DatabaseUtil;
import models.Game;

public class BetDao {

	private SessionFactory sessionFactory;
	public BetDao() {
		sessionFactory = DatabaseUtil.getSessionFactory();
		
	}
	
	public Bet add(Bet bet) {
		    Session session = sessionFactory.openSession();
		    int id = 0;
	 	    Transaction tx = null;
				try {
		         tx = session.beginTransaction();
		         id = (int) session.save(bet);
		         tx.commit();
		      } catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      } finally {
		         session.close(); 
		      }
		return bet;
	}
	
	public Bet get(int id) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Bet bet = null;
		try {
			tx = session.beginTransaction();
			bet = session.get(Bet.class, id);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return bet;
	}
	
	public void update(Bet bet) {
		Session session = sessionFactory.openSession();
	      Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
			 session.update(bet); 
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	}
	
	
}
