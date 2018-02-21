package com.ncorp.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.*;

@Singleton @Startup
public class DefaultDataInitializerEjb {

	@EJB
	private QuizEjb quizEjb;

	@EJB
	private CategoryEjb categoryEjb;

	@PostConstruct @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void init(){
		Long firstCategoryId = categoryEjb.createCategory("first category"),
				secondCategoryId = categoryEjb.createCategory("second category");

		Long firstSubCategoryId = categoryEjb.createSubCategory(firstCategoryId, "first sub category"),
				secondSubCategoryId = categoryEjb.createSubCategory(firstCategoryId, "second sub category"),
				thirdSubCategoryId = categoryEjb.createSubCategory(secondCategoryId, "third sub category"),
				fourthSubCategoryId = categoryEjb.createSubCategory(secondCategoryId, "fourth sub category"),
				fifthSubCategoryId = categoryEjb.createSubCategory(secondCategoryId, "fifth sub category");

		Long firstQuiz = quizEjb.createQuiz(firstSubCategoryId,
				"What is 1 + (-1)?",
				"0",
				"1",
				"2",
				"3",
				0),
		secondQuiz = quizEjb.createQuiz(firstSubCategoryId,
				"What is the capital of Norway?",
				"N",
				"Oslo",
				"Europe",
				"Scandinavia",
				1),
		thirdQuiz = quizEjb.createQuiz(secondSubCategoryId,
				"What is the capital of Italy?",
				"Florence",
				"Milan",
				"Rome",
				"Turin",
				2),
		fourthQuiz = quizEjb.createQuiz(secondSubCategoryId,
				"When ended the second world war?",
				"1925",
				"1935",
				"1945",
				"1955",
				2),
		fifthQuiz = quizEjb.createQuiz(secondSubCategoryId,
				"Name the seventh planet from the sun?",
				"Uranus",
				"Earth",
				"Jupiter",
				"Saturn",
				0),
		sixthQuiz = quizEjb.createQuiz(thirdSubCategoryId,
				"Name the world's biggest island?",
				"Island",
				"Greenland",
				"Australia",
				"North pole",
				1),
		seventhQuiz = quizEjb.createQuiz(fourthSubCategoryId,
				"What is the world's longest river?",
				"Nile",
				"Danube",
				"Ganges",
				"Amazon",
				3),
		eigthQuiz = quizEjb.createQuiz(fourthSubCategoryId,
				"When did the Cold War end?",
				"1789",
				"1889",
				"1989",
				"2009",
				2),
		nithQuiz = quizEjb.createQuiz(fifthSubCategoryId,
				"What colour is Absynthe?",
				"Yellow",
				"Red",
				"Blue",
				"Green",
				3),
		tenthQuiz = quizEjb.createQuiz(fifthSubCategoryId,
				"Who is Real Madrid's all-time top goalscorer?",
				"Raul",
				"Di Stefano",
				"Guti",
				"Cristiano Ronaldo",
				3);


	}
}
