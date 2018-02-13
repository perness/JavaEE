package com.ncorp.entity;

import org.junit.Ignore;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@Ignore
public class Util {

	public Util() {
	}

	private EntityManagerFactory factory;
	private EntityManager entityManager;

	public void init(){
		factory = Persistence.createEntityManagerFactory("DB");
		entityManager = factory.createEntityManager();
	}

	public void tearDown(){
		entityManager.close();
		factory.close();
	}

	public boolean persistInATransaction(Object... obj) {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		try {
			for(Object o : obj) {
				entityManager.persist(o);
			}
			entityTransaction.commit();
		} catch (Exception e) {
			System.out.println("FAILED TRANSACTION: " + e.toString());
			entityTransaction.rollback();
			return false;
		}

		return true;
	}

	public EntityManagerFactory getFactory() {
		return factory;
	}

	public void setFactory(EntityManagerFactory factory) {
		this.factory = factory;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
