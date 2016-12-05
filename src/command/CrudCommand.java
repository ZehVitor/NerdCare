package command;

import javax.persistence.EntityManager;

public interface CrudCommand {
	public void execute(EntityManager em, Object entidade);
	public void undo(EntityManager em);
}
