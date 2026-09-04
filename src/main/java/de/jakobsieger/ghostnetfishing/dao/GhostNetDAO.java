package de.jakobsieger.ghostnetfishing.dao;

import java.util.List;

import de.jakobsieger.ghostnetfishing.model.GhostNet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class GhostNetDAO {
	
	private EntityManagerFactory factory;
	
	public GhostNetDAO() {
		factory = Persistence.createEntityManagerFactory("GhostNetFishingPersistenceUnit");
	}
	
	public List<GhostNet> findAll() {
		EntityManager manager = factory.createEntityManager();
		Query query = manager.createQuery("SELECT a FROM GhostNet a");
		List<GhostNet> allGhostNets = query.getResultList();
		manager.close();
		return allGhostNets;
	}
	
	public void save(GhostNet ghostNet) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		
		transaction.begin();
		manager.persist(ghostNet);
		transaction.commit();
		
		manager.close();
	}
	
	public void update(GhostNet ghostNet) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		
		transaction.begin();
		manager.merge(ghostNet);
		transaction.commit();
		
		manager.close();
	}
}