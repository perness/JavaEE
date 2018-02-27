package com.ncorp.ejb;

import com.ncorp.entity.Quiz;
import com.ncorp.entity.SubCategory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Stateless
public class QuizEjb {

	@PersistenceContext
	private EntityManager entityManager;

	public long createQuiz(long subCategoryId, String question, String firstAnswer, String secondAnswer,
						   String thirdAnswer, String fourthAnswer, int indexOfCorrectAnswer){
		Quiz quiz = new Quiz();
		SubCategory subCategory = entityManager.find(SubCategory.class, subCategoryId);

		if (subCategory == null) throw new IllegalArgumentException("Subcategory with id " + subCategoryId + "could not be found");

		quiz.setSubCategory(subCategory);
		quiz.setQuestion(question);
		quiz.setAnswerOne(firstAnswer);
		quiz.setAnswerTwo(secondAnswer);
		quiz.setAnswerThree(thirdAnswer);
		quiz.setAnswerFour(fourthAnswer);
		quiz.setCorrectAnswer(indexOfCorrectAnswer);

		entityManager.persist(quiz);

		return quiz.getId();
	}

	public List<Quiz> getQuizzes(){
		TypedQuery<Quiz> getAllQuizzes = entityManager.createQuery("select q from Quiz q", Quiz.class);

		return getAllQuizzes.getResultList();
	}

	public Quiz getQuiz(long id){
		Quiz quiz = entityManager.find(Quiz.class, id);

		if (quiz == null) throw new IllegalArgumentException("Quiz with id " + id + "could not be found");

		return quiz;
	}

	public List<Quiz> getRandomQuizzes(int n, long categoryId){
		TypedQuery<Long> sizeQuery= entityManager.createQuery("select count(q) from Quiz q where q.subCategory.category.id=?1", Long.class);
		sizeQuery.setParameter(1, categoryId);
		long size = sizeQuery.getSingleResult();

		if(n > size){
			throw new IllegalArgumentException("Cannot choose " + n + " unique quizzes out of the " + size + " existing");
		}

		Random random = new Random();

		List<Quiz> quizzes = new ArrayList<>();
		Set<Integer> chosen = new HashSet<>();

		while(chosen.size() < n) {

			int k = random.nextInt((int)size);
			if(chosen.contains(k)){
				continue;
			}
			chosen.add(k);

			TypedQuery<Quiz> query = entityManager.createQuery(
					"select q from Quiz q where q.subCategory.category.id=?1", Quiz.class);
			query.setParameter(1, categoryId);
			query.setMaxResults(1);
			query.setFirstResult(k);

			quizzes.add(query.getSingleResult());
		}

		return  quizzes;
	}




}
