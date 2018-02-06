package com.ncorp.homework;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

public class CategoryEntityTest {

	private EntityManagerFactory factory;
	private EntityManager entityManager;
	private Util util;

	@Before
	public void init(){
		util = new Util();
		util.setFactory(factory);
		util.setEntityManager(entityManager);
		util.init();
	}

	@After
	public void tearDown(){
		util.tearDown();
	}

	@Test
	public void testTooLongName(){
		Category category = new Category();
		category.setName(new String(new char[1_000]));

		assertFalse(util.persistInATransaction(category)); // Should fail because name is to long

		util.getEntityManager().clear();

		category.setId(null);
		category.setName("short name");

		assertTrue(util.persistInATransaction(category)); // Named changed to shorter version, now it should pass

	}

	@Test
	public void testUniqueName(){
		Category category1 = new Category(), category2 = new Category();
		category1.setName("name");
		category2.setName("name");

		assertTrue(util.persistInATransaction(category1));
		assertFalse(util.persistInATransaction(category2)); // Should fail because name should be unique


	}
}
