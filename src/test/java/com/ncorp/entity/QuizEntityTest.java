package com.ncorp.entity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class QuizEntityTest {

//	private EntityManagerFactory factory;
//	private EntityManager entityManager;
	private Util util;

	@Before
	public void init(){
		util = new Util();
//		util.setFactory(factory);
//		util.setEntityManager(entityManager);
		util.init();
	}

	@After
	public void tearDown(){
		util.tearDown();
	}

    @Test
    public void quizTest(){
        Quiz quiz = new Quiz();
        quiz.setQuestion("Is this going to work?");
        quiz.setAnswerOne("No");
        quiz.setAnswerTwo("Yes");
        quiz.setAnswerThree("Maybe");
        quiz.setAnswerFour("I do not know");
        quiz.setCorrectAnswer(1);

        assertFalse(util.persistInATransaction(quiz)); // Will fail because does not add subcategory

		util.getEntityManager().clear();

        assertNotNull(quiz.getId());
        assertEquals("No", quiz.getAnswerOne());
        assertEquals("Yes", quiz.getAnswerTwo());
        assertEquals("Maybe", quiz.getAnswerThree());
        assertEquals("I do not know", quiz.getAnswerFour());

        System.out.println("Generated ID for quiz " + quiz.getId());

    }

    @Test
	public void testQuizWithSubCategory(){
    	Category category = new Category();
    	SubCategory subCategory = new SubCategory();
    	Quiz quiz = new Quiz();
		List<SubCategory> subCategoryList = new ArrayList<SubCategory>();

		category.setName("category name");
		category.setSubCategories(subCategoryList);

		subCategory.setName("first sub category");
		subCategory.setCategory(category);

		subCategoryList.add(subCategory);

    	quiz.setSubCategory(subCategory);
		quiz.setQuestion("Is this going to work?");
		quiz.setAnswerOne("No");
		quiz.setAnswerTwo("Yes");
		quiz.setAnswerThree("Maybe");
		quiz.setAnswerFour("I do not know");
		quiz.setCorrectAnswer(1);

		assertTrue(util.persistInATransaction(category,subCategory,quiz));

		util.getEntityManager().clear();

		assertNotNull(quiz.getId());
    	assertNotNull(category.getId());
    	assertNotNull(subCategory.getId());

    	assertEquals("category name", category.getName());
    	assertEquals("first sub category", category.getSubCategories().get(0).getName());
	}

	@Test
	public void testQueries(){
		Category category = new Category();
		Quiz quiz1 = new Quiz(), quiz2 = new Quiz(), quiz3 = new Quiz(), quiz4 = new Quiz();
		SubCategory subCategory1 = new SubCategory(), subCategory2 = new SubCategory(), subCategory3 = new SubCategory();
		List<SubCategory> subCategoryList = new ArrayList<SubCategory>();

		subCategoryList.add(subCategory1);
		subCategoryList.add(subCategory2);
		subCategoryList.add(subCategory3);

		category.setName("JEE");
		category.setSubCategories(subCategoryList);

		subCategory1.setName("JPA");
		subCategory2.setName("EJB");
		subCategory3.setName("JSF");
		subCategory1.setCategory(category);
		subCategory2.setCategory(category);
		subCategory3.setCategory(category);

		quiz1.setQuestion("JPA question one");
		quiz1.setAnswerOne("blank");
		quiz1.setAnswerTwo("blank");
		quiz1.setAnswerThree("blank");
		quiz1.setAnswerFour("blank");
		quiz1.setSubCategory(subCategory1);

		quiz2.setQuestion("JPA question two");
		quiz2.setAnswerOne("blank");
		quiz2.setAnswerTwo("blank");
		quiz2.setAnswerThree("blank");
		quiz2.setAnswerFour("blank");
		quiz2.setSubCategory(subCategory1);

		quiz3.setQuestion("EJB question one");
		quiz3.setAnswerOne("blank");
		quiz3.setAnswerTwo("blank");
		quiz3.setAnswerThree("blank");
		quiz3.setAnswerFour("blank");
		quiz3.setSubCategory(subCategory2);

		quiz4.setQuestion("JSF question one");
		quiz4.setAnswerOne("blank");
		quiz4.setAnswerTwo("blank");
		quiz4.setAnswerThree("blank");
		quiz4.setAnswerFour("blank");
		quiz4.setSubCategory(subCategory3);

		assertTrue(util.persistInATransaction(category,subCategory1,subCategory2,subCategory3,quiz1,quiz2,quiz3,quiz4));

		util.getEntityManager().clear();

		TypedQuery<Quiz> query1 = util.getEntityManager().createNamedQuery(Quiz.GET_ALL_QUIZZES_IN_SUBCATEGORY_JPA, Quiz.class);
		List<Quiz> quizzes = query1.getResultList();

		assertEquals(2, quizzes.size());
		assertTrue(quizzes.stream().anyMatch(quiz -> quiz.getQuestion().equals("JPA question one")));
		assertFalse(quizzes.stream().anyMatch(quiz -> quiz.getQuestion().equals("EJB question one")));

		TypedQuery<Quiz> query2 = util.getEntityManager().createNamedQuery(Quiz.GET_ALL_QUIZZES, Quiz.class);
		List<Quiz> quizzes1 = query2.getResultList();

		assertEquals(4, quizzes1.size());

	}
}
