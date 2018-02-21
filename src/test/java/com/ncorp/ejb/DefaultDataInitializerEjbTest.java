package com.ncorp.ejb;

import com.ncorp.entity.Category;
import com.ncorp.entity.Quiz;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import javax.ejb.EJB;
import java.util.List;

@RunWith(Arquillian.class)
public class DefaultDataInitializerEjbTest {

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addPackages(true, "com.ncorp")
				.addAsResource("META-INF/persistence.xml");
	}

	@EJB
	private CategoryEjb categoryEjb;

	@EJB
	private QuizEjb quizEjb;

	@Test
	public void verifyDataInDatabase(){
		List<Category> categoryList = categoryEjb.getAllCategories(false);
		List<Category> categoryListWithSub = categoryEjb.getAllCategories(true);
		List<Quiz> quizList = quizEjb.getQuizzes();

		assertTrue(categoryList.size() > 0);

		assertTrue(categoryListWithSub.stream().mapToLong(c -> c.getSubCategories().size()).sum() > 0);

		assertTrue(quizList.size() > 0);
	}
}
