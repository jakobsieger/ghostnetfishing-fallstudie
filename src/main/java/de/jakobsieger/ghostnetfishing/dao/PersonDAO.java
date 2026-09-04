package de.jakobsieger.ghostnetfishing.dao;

import java.util.List;

import de.jakobsieger.ghostnetfishing.model.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class PersonDAO {

	private EntityManagerFactory factory;

	public PersonDAO() {
		factory = Persistence.createEntityManagerFactory("GhostNetFishingPersistenceUnit");
	}

	public Person findByNameAndPhone(String name, String phone) {
		EntityManager manager = factory.createEntityManager();
		Query query = manager
				.createQuery("SELECT p FROM Person p WHERE p.name = :name AND p.phone = :phone", Person.class)
				.setParameter("name", name).setParameter("phone", phone);
		List<Person> result = query.getResultList();
		manager.close();
		return result.isEmpty() ? null : result.get(0);
	}
}