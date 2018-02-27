package com.ncorp.ejb;

import com.ncorp.entity.Category;
import com.ncorp.entity.SubCategory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.List;

@Stateless
public class CategoryEjb {

	@PersistenceContext
	private EntityManager entityManager;

	public Long createCategory(String name){
		Category category = new Category();
		category.setName(name);

		entityManager.persist(category);

		return category.getId();
	}

	public Long createSubCategory(long parentId, String name){
		Category category = entityManager.find(Category.class, parentId);
		if(category == null) throw new IllegalArgumentException("Category with id " +parentId+ " does not exist");

		SubCategory subCategory = new SubCategory();

		subCategory.setName(name);
		subCategory.setCategory(category);

		category.getSubCategories().add(subCategory);

		entityManager.persist(subCategory);

		return subCategory.getId();
	}

	public List<Category> getAllCategories(boolean withSub){
		TypedQuery query = entityManager.createQuery("select c from Category c", Category.class);

		List<Category> categories = query.getResultList();

		if (withSub) categories.forEach(category -> category.getSubCategories().size());

		return categories;
	}

	public Category getCategory(long id, boolean withSub){
		Category category = entityManager.find(Category.class, id);

		if (withSub && category != null) category.getSubCategories().size();

		return category;
	}

	public SubCategory getSubCategory(long id){
		return entityManager.find(SubCategory.class, id);
	}
}





















