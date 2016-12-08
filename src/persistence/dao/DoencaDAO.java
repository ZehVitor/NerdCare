package persistence.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import persistence.dominio.Doenca;

public class DoencaDAO extends GenericDAO {

	public void InserUpdateDoencaByName(Doenca disease){
		EntityManager em = getEntityManager();
		try {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();	
			}
			
			Query q = em.createQuery("Select o from Doenca as o where o.nome = :nome");
			q.setParameter("nome", disease.getNome());
			
			try {
				disease = (Doenca) q.getSingleResult();
				em.merge(disease);
			} catch (Exception e) {
				em.persist(disease);
			}
			
			em.getTransaction().commit();
		}catch (Exception e) {
			System.out.println(e.getMessage());
			em.getTransaction().rollback();
		}
		finally {
		}
	}
}
