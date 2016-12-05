package command;

import javax.persistence.EntityManager;

public class InsertCommand implements CrudCommand {

	@Override
	public void execute(EntityManager em, Object entidade) {
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();	
		}
		
		em.persist(entidade);
		em.getTransaction().commit();
	}

	@Override
	public void undo(EntityManager em) {
		em.getTransaction().rollback();
	}

}
