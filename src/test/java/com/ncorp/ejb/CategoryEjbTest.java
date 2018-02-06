package com.ncorp.ejb;

import com.ncorp.beans.CategoryEjb;
import com.ncorp.homework.Category;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
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
}
