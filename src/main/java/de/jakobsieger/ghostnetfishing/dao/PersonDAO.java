package de.jakobsieger.ghostnetfishing.dao;

import de.jakobsieger.ghostnetfishing.model.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class PersonDAO {

	private EntityManagerFactory factory;

	public PersonDAO() {
		factory = Persistence.createEntityManagerFactory("GhostNetFishingPersistenceUnit");
	}

	public void save(Person person) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();

		transaction.begin();
		manager.persist(person);
		transaction.commit();

		manager.close();
	}
}