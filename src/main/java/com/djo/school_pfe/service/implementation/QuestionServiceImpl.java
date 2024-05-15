package com.djo.school_pfe.service.implementation;

import com.djo.school_pfe.entity.Answer;
import com.djo.school_pfe.entity.Question;
import com.djo.school_pfe.entity.Validation;
import com.djo.school_pfe.repository.QuestionRepository;
import com.djo.school_pfe.repository.RoleRepository;
import com.djo.school_pfe.service.interfaces.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }
    @Autowired
    Validation validation;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Override
    public String add(Question question) {
        for (Answer answer : question.getAnswers()) {
            answer.setQuestion(question); // Set the question for each answer
        }
        this.questionRepository.save(question);
        return "Question saved successfully";
    }




    @Override
    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Question updateQuestion(Long id, Question question) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if (optionalQuestion.isPresent()) {
            // Update the question's details
            for (Answer answer : question.getAnswers()) {
                answer.setQuestion(question); // Set the question for each answer
            }
            question.setId(id); // Set the ID of the question to be updated
            return questionRepository.save(question);
        } else {
            throw new IllegalArgumentException("Question with id " + id + " does not exist!");
        }
    }

    @Override
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
}
