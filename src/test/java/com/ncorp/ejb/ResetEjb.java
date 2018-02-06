package com.ncorp.ejb;

import com.ncorp.homework.Category;
import com.ncorp.homework.Quiz;
import com.ncorp.homework.SubCategory;

import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ResetEjb {

	@PersistenceContext
	private EntityManager entityManager;

	public void resetDatabase(){
		deleteEntities(Category.class);
		deleteEntities(Quiz.class);
		deleteEntities(SubCategory.class);
	}

	private void deleteEntities(Class<?> entity){
		if (entity == null || entity.getAnnotation(Entity.class) == null){
			throw new IllegalArgumentException("Invalid non-entity class");
		}

		String name = entity.getSimpleName();

		Query query = entityManager.createQuery("delete from " + name);
		query.executeUpdate();
	}
}
