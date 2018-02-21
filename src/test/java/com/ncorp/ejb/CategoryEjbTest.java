package com.ncorp.ejb;

import com.ncorp.entity.Category;
import com.ncorp.entity.SubCategory;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class CategoryEjbTest {

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addPackages(true, "com.ncorp")
				.addAsResource("META-INF/persistence.xml");
	}

	@EJB
	private CategoryEjb categoryEjb;

	@EJB
	private ResetEjb resetEjb;

	@Before
	public void init(){
		resetEjb.resetDatabase();
	}

	@Test
	public void testNoCategory(){
		List<Category> categories = categoryEjb.getAllCategories(false);

		assertEquals(0, categories.size());
	}

	@Test
	public void testCreateCategory(){
		Long id = categoryEjb.createCategory("firstCategory");

		List<Category> categories = categoryEjb.getAllCategories(false);

		assertEquals(1, categories.size());
		assertNotNull(id);
	}

	@Test
	public void testGetCategory(){
		String categoryName = "name";

		Long id = categoryEjb.createCategory(categoryName);

		Category category = categoryEjb.getCategory(id, false);

		assertEquals(category.getName(), categoryName);
	}

	@Test
	public void testCreateSubCategory(){
		String categoryName = "CategoryName";
		String subCategoryName = "SubCategoryName";

		Long categoryId = categoryEjb.createCategory(categoryName);
		Long subCategoryId = categoryEjb.createSubCategory(categoryId, subCategoryName);

		SubCategory subCategory = categoryEjb.getSubCategory(subCategoryId);

		System.out.println("Category name: " + subCategory.getCategory().getName());

		assertEquals(subCategory.getName(), subCategoryName);
		assertEquals(subCategory.getCategory().getName(), categoryName);

	}

	@Test
	public void testGetAllCategories(){
		Long categoryId1, categoryId2, categoryId3, subCategory1, subCategory2, subCategory3;
		String firstCategoryName = "firstCategoryName",
				secondCategoryName = "secondCategoryName",
				thirdCategoryName = "thirdCategoryName",
				subCategoryName = "subCategoryName";

		categoryId1 = categoryEjb.createCategory(firstCategoryName);
		categoryId2 = categoryEjb.createCategory(secondCategoryName);
		categoryId3 = categoryEjb.createCategory(thirdCategoryName);

		subCategory1 = categoryEjb.createSubCategory(categoryId1, subCategoryName);
		subCategory2 = categoryEjb.createSubCategory(categoryId2, subCategoryName);
		subCategory3 = categoryEjb.createSubCategory(categoryId3, subCategoryName);

		List<Category> categoriesWithoutSub = categoryEjb.getAllCategories(false);

		assertEquals(3, categoriesWithoutSub.size());

		try {
			categoriesWithoutSub.get(0).getSubCategories().size();
			fail();
		}
		catch (Exception e){
		}

		List<Category> categoriesWithSub = categoryEjb.getAllCategories(true);

		assertEquals(1, categoriesWithSub.get(0).getSubCategories().size());
	}

	@Test
	public void testCreateTwice(){
		String name = "name";
		Long categoryId1 = categoryEjb.createCategory(name);

		assertNotNull(categoryId1);

		try {
			categoryEjb.createCategory(name);
			fail();
		}
		catch (Exception e){
		}
	}
}