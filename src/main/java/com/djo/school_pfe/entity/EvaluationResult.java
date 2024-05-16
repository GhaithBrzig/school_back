package com.djo.school_pfe.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "EvaluationResults")
public class EvaluationResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eleve_id")
    @JsonIgnoreProperties({"evaluationResults", "passedEvaluations", "hibernateLazyInitializer", "handler"})
    private Eleve eleve;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_id")
    @JsonIgnoreProperties({"questions", "classes", "hibernateLazyInitializer", "handler"})
    private Evaluation evaluation;

    private int score;


    private boolean expired;

    // Add equals and hashCode methods for proper entity comparison if needed

    // Additional method to check if the evaluation is expired


    // Add equals and hashCode methods for proper entity comparison if needed
}
