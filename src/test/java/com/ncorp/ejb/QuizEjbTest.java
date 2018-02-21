package com.ncorp.ejb;

import com.ncorp.entity.Quiz;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class QuizEjbTest extends EjbTestBase {

	@EJB
	private QuizEjb quizEjb;

	@EJB
	private CategoryEjb categoryEjb;

	@Test
	public void testNoQuiz(){
		List<Quiz> quizzes = quizEjb.getQuizzes();

		assertEquals(0, quizzes.size());
	}

	@Test
	public void testCreateQuiz(){
		long categoryId = categoryEjb.createCategory("category name");
		long subCategoryId = categoryEjb.createSubCategory(categoryId, "sub cat name");
		long quiz = quizEjb.createQuiz(subCategoryId,
				"question",
				"firstAnswer",
				"secondAnswer",
				"thirdAnswer",
				"fourthAnswer",
				0);

		List<Quiz> quizzes = quizEjb.getQuizzes();

		assertEquals(1,quizzes.size());
		assertEquals("question",quizEjb.getQuiz(quiz).getQuestion());
	}

	@Test
	public void testNotEnoughQuizzes(){
		long categoryId = categoryEjb.createCategory("category name");
		long subCategoryId = categoryEjb.createSubCategory(categoryId, "sub cat name");

		long firstQuiz = quizEjb.createQuiz(subCategoryId,
				"What is 1 + (-1)?",
				"0",
				"1",
				"2",
				"3",
				0),
				secondQuiz = quizEjb.createQuiz(subCategoryId,
						"What is the capital of Norway?",
						"N",
						"Oslo",
						"Europe",
						"Scandinavia",
						1),
				thirdQuiz = quizEjb.createQuiz(subCategoryId,
						"What is the capital of Italy?",
						"Florence",
						"Milan",
						"Rome",
						"Turin",
						2);

		try {
			List<Quiz> quizList = quizEjb.getRandomQuizzes(5,categoryId);
			fail();
		}
		catch (Exception e){

		}
	}

	@Test
	public void testGetRandomQuizzes(){
		long categoryId = categoryEjb.createCategory("category name");
		long subCategoryId = categoryEjb.createSubCategory(categoryId, "sub cat name");
		String questionOne = "What is 1 + (-1)?",
				questionTwo = "What is the capital of Norway?",
				questionThree = "What is the capital of Italy?";

		long firstQuiz = quizEjb.createQuiz(subCategoryId,
				questionOne,
				"0",
				"1",
				"2",
				"3",
				0),
				secondQuiz = quizEjb.createQuiz(subCategoryId,
						questionTwo,
						"N",
						"Oslo",
						"Europe",
						"Scandinavia",
						1),
				thirdQuiz = quizEjb.createQuiz(subCategoryId,
						questionThree,
						"Florence",
						"Milan",
						"Rome",
						"Turin",
						2);

		Set<String> questions = new HashSet<>();

		for (int i = 0; i < 50; i++){
			List<Quiz> quizzes = quizEjb.getRandomQuizzes(2,categoryId);

			assertEquals(2,quizzes.size());
			assertNotEquals(quizzes.get(0).getQuestion(),quizzes.get(1).getQuestion());

			questions.add(quizzes.get(0).getQuestion());
			questions.add(quizzes.get(1).getQuestion());
		}

		assertEquals(3,questions.size());
		assertTrue(questions.contains(questionOne));
		assertTrue(questions.contains(questionTwo));
		assertTrue(questions.contains(questionThree));


	}


}
