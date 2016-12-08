package persistence.dominio;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Banco {
	private static EntityManager em;
	private static Usuario currentUser;

	public static EntityManager getInstance(){
		if (em == null) {
			EntityManagerFactory emf =
					Persistence.createEntityManagerFactory("NerdCare");
			em = emf.createEntityManager();
		}
		
		return em;
	}
	
	public static void closeInstance(){
		if (em != null && em.isOpen()) {
			em.close();	
		}
	}
	
	public static Usuario getCurrentUser(){
		return currentUser;
	}
	
	public static void setCurrentUser(Usuario cUser) {
		currentUser = cUser;
	}
}
