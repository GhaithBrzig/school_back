package com.djo.school_pfe.service.implementation;

import com.djo.school_pfe.entity.*;
import com.djo.school_pfe.repository.EvaluationRepository;
import com.djo.school_pfe.repository.QuestionRepository;
import com.djo.school_pfe.repository.RoleRepository;
import com.djo.school_pfe.service.interfaces.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluationServiceImpl implements EvaluationService {
    private final EvaluationRepository evaluationRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public EvaluationServiceImpl(EvaluationRepository evaluationRepository, QuestionRepository questionRepository) {
        this.evaluationRepository = evaluationRepository;
        this.questionRepository = questionRepository;
    }
    @Autowired
    Validation validation;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Override
    public String add(Evaluation evaluation) {
        for (Question question : evaluation.getQuestions()) {
            question.setEvaluation(evaluation);
            for (Answer answer : question.getAnswers()) {
                answer.setQuestion(question);
            }
        }
        this.evaluationRepository.save(evaluation);
        return "Evaluation saved successfully";
    }




    @Override
    public Evaluation getEvaluationById(Long id) {
        return evaluationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    @Override
    public Evaluation updateEvaluation(Long id, Evaluation evaluation) {
        Optional<Evaluation> optionalEvaluation = evaluationRepository.findById(id);
        if (optionalEvaluation.isPresent()) {
            Evaluation existingEvaluation = optionalEvaluation.get();

            // Get all questions for the existing evaluation and delete them
            List<Question> existingQuestions = questionRepository.findByEvaluationId(existingEvaluation.getId());
            for (Question question : existingQuestions) {
                questionRepository.delete(question);
            }

            // Set the new questions for the evaluation
            for (Question question : evaluation.getQuestions()) {
                question.setEvaluation(existingEvaluation);
                for (Answer answer : question.getAnswers()) {
                    answer.setQuestion(question);
                }
                existingEvaluation.getQuestions().add(question);
            }

            // Update the evaluation's details
            existingEvaluation.setNom(evaluation.getNom()); // Update other attributes if needed

            return evaluationRepository.save(existingEvaluation);
        } else {
            throw new IllegalArgumentException("Evaluation with id " + id + " does not exist!");
        }
    }








    @Override
    public void deleteEvaluation(Long id) {
        evaluationRepository.deleteById(id);
    }
    @Override
    public List<Evaluation> getEvaluationsByEnseignantId(Long enseignantId) {
        return evaluationRepository.findByEnseignant_UserId(enseignantId);
    }
}
