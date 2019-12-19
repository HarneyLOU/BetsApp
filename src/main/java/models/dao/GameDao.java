package models.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import models.Category;
import models.DatabaseUtil;
import models.Game;
import views.GameView;

public class GameDao {

	private SessionFactory sessionFactory;

	public GameDao() {
		sessionFactory = DatabaseUtil.getSessionFactory();
	}

	public List<Game> getAll() {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Game> games = null;
		try {
			tx = session.beginTransaction();
			games = session.createQuery("FROM Game").list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return games;
	}
	
	public Game get(int id) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Game game = null;
		try {
			tx = session.beginTransaction();
			game = session.get(Game.class, id);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return game;
	}

	public Game add(Game game) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(game);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return game;
	}
	
	public Game remove(int id) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Game game = null;
		try {
			tx = session.beginTransaction();
			System.out.print(id);
			game = session.get(Game.class, id);
			session.delete(game);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return game;
	}
	
	public void update(int id, int winner) {
		Session session = sessionFactory.openSession();
	      Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
	         Game game = (Game)session.get(Game.class, id); 
	         game.setWinner(winner);
			 session.update(game); 
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	}
}
