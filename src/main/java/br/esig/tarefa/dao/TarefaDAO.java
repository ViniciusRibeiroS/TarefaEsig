package br.esig.tarefa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.esig.tarefa.model.Responsavel;
import br.esig.tarefa.model.Tarefa;

public class TarefaDAO {

	public Tarefa salvar(Tarefa t) throws Exception {
		EntityManager entityManager = Persistence.createEntityManagerFactory("entityManager").createEntityManager();
		
		try {
			entityManager.getTransaction().begin();
			if (t.getId() == null) {
				entityManager.persist(t);
				entityManager.getTransaction().commit();
			} else {
				t = entityManager.merge(t);
				entityManager.getTransaction().commit();
			}
			entityManager.getTransaction().commit();
		} finally {
			entityManager.close();
		}
		return t;
	}

	public void excluir(Long id) {
		EntityManager entityManager = Persistence.createEntityManagerFactory("entityManager").createEntityManager();
		try {
			entityManager.getTransaction().begin();
			Tarefa t = entityManager.find(Tarefa.class, id);
			System.out.println("Excluindo os dados de: " + t.getTitulo());
			entityManager.remove(t);
			entityManager.getTransaction().commit();
		} finally {
			entityManager.close();
		}
	}

	public Tarefa consultarPorId(Long id) {
		EntityManager entityManager = Persistence.createEntityManagerFactory("entityManager").createEntityManager();
		Tarefa t = null;
		try {
			t = entityManager.find(Tarefa.class, id);
		} finally {
			entityManager.close();
		}
		return t;
	}
	
	public List<Tarefa> listarTodas() {
		EntityManager entityManager = Persistence.createEntityManagerFactory("entityManager").createEntityManager();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tarefa> criteriaQuery = builder.createQuery(Tarefa.class);
		Root<Tarefa> entidade = criteriaQuery.from(Tarefa.class);
		criteriaQuery.where(builder.equal(entidade.get("concluida"), false));
		criteriaQuery.orderBy(builder.asc(entidade.get("id")));
		TypedQuery<Tarefa> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	public List<Tarefa> buscar(String tituloDescricao, Boolean concluido, Responsavel responsavel) {				
		EntityManager entityManager = Persistence.createEntityManagerFactory("entityManager").createEntityManager();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tarefa> criteriaQuery = builder.createQuery(Tarefa.class);
		Root<Tarefa> entidade = criteriaQuery.from(Tarefa.class);	
		criteriaQuery.where(builder.or(builder.equal(entidade.get("titulo"), tituloDescricao),
		builder.equal(entidade.get("descricao"), tituloDescricao), builder.equal(entidade.get("concluida"), concluido), 
		builder.equal(entidade.get("responsavel"), responsavel)));
		criteriaQuery.orderBy(builder.asc(entidade.get("id")));
		TypedQuery<Tarefa> query = entityManager.createQuery(criteriaQuery);
		for (Tarefa tarefa : query.getResultList()) {
			System.out.print(tarefa.getTitulo());
		}
		return query.getResultList();
	}


}
