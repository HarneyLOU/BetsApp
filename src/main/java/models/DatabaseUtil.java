package models;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DatabaseUtil {

	public static SessionFactory getSessionFactory() {
		SessionFactory factory = null;
		 try {
			 factory = new Configuration().configure().buildSessionFactory();
	      } catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex);
	      }
		 return factory;
	}
}
