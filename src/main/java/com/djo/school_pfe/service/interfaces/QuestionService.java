package com.djo.school_pfe.service.interfaces;

import com.djo.school_pfe.entity.Question;

import java.util.List;

public interface QuestionService {



    Question getQuestionById(Long id);
    List<Question> getAllQuestions();
    Question updateQuestion(Long id, Question question);
    void deleteQuestion(Long id);

    String add(Question question);
}
