package de.jakobsieger.ghostnetfishing.dao;

import java.util.List;

import de.jakobsieger.ghostnetfishing.model.GhostNet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class GhostNetDAO {
	
	private EntityManagerFactory entityManagerFactory;
	
	public GhostNetDAO() {
		entityManagerFactory = Persistence.createEntityManagerFactory("GhostNetFishingPersistenceUnit");
	}
	
	public List<GhostNet> findAll() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createQuery("SELECT a FROM GhostNet a");
		List<GhostNet> allGhostNets = query.getResultList();
		entityManager.close();
		return allGhostNets;
	}
}