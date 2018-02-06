package com.ncorp.homework;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@NamedQueries({
		@NamedQuery(name = Quiz.GET_ALL_QUIZZES_IN_SUBCATEGORY_JPA, query = "select u from Quiz u where u.subCategory.name = 'JPA' "),
		@NamedQuery(name = Quiz.GET_ALL_QUIZZES, query = "select u from Quiz u where u.subCategory.category.name = 'JEE' ")
})

@Entity
public class Quiz {

	public static final String GET_ALL_QUIZZES_IN_SUBCATEGORY_JPA = "GET_ALL_QUIZZES_IN_SUBCATEGORY_JPA";
	public static final String GET_ALL_QUIZZES = "GET_ALL_QUIZZES";

	@Id @GeneratedValue
	private Long id;

	@Range(max = 3)
	private int correctAnswer;

	@Size(max = 128) @Column(unique = true) @NotBlank
	private String question;

	@NotBlank @Size(max = 128)
	private String answerOne, answerTwo, answerThree, answerFour;

	@ManyToOne @NotNull
	private SubCategory subCategory;

	public Quiz() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswerOne() {
		return answerOne;
	}

	public void setAnswerOne(String answerOne) {
		this.answerOne = answerOne;
	}

	public String getAnswerTwo() {
		return answerTwo;
	}

	public void setAnswerTwo(String answerTwo) {
		this.answerTwo = answerTwo;
	}

	public String getAnswerThree() {
		return answerThree;
	}

	public void setAnswerThree(String answerThree) {
		this.answerThree = answerThree;
	}

	public String getAnswerFour() {
		return answerFour;
	}

	public void setAnswerFour(String answerFour) {
		this.answerFour = answerFour;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}
}
