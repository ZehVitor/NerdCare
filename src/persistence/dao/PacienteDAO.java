package persistence.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import persistence.dominio.Banco;
import persistence.dominio.Doenca;

public class PacienteDAO extends GenericDAO {

	public List<Doenca> findDoencasByUserId (int id){
		EntityManager em = getEntityManager();
		ArrayList<Doenca> doencas = null;
		StringBuilder sbQuery = new StringBuilder();
		try {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();	
			}
			
			sbQuery.append("Select d from paciente_has_doencas as pd ");
			sbQuery.append("inner join paciente p on pd.id_paciente = p.id_paciente ");
			sbQuery.append("inner join doenca d on pd.id_doenca = d.id_doenca ");
			sbQuery.append("where p.id_paciente = :id");
			
			Query q = em.createQuery(sbQuery.toString());
			
			q.setParameter("id", id);
			doencas = (ArrayList<Doenca>) q.getResultList();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
		finally {
		}
		
		return doencas;
	}
}
