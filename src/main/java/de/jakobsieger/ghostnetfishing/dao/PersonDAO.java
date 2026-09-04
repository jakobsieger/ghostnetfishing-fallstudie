package de.jakobsieger.ghostnetfishing.dao;

import java.util.List;

import de.jakobsieger.ghostnetfishing.model.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

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

	public List<Person> findAll() {
		EntityManager manager = factory.createEntityManager();
		Query query = manager.createQuery("SELECT a FROM Person a");
		List<Person> allPersons = query.getResultList();
		manager.close();
		return allPersons;
	}
}