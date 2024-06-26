package com.djo.school_pfe.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Eleves")
public class Eleve extends UserEntity{

    @JsonIgnoreProperties({"eleve", "hibernateLazyInitializer", "handler"})
    @ManyToOne
    private Classe classe;

    @ManyToMany
    @JsonIgnoreProperties({"eleve", "hibernateLazyInitializer", "handler"})
    private List<Parent> parents;

    @ManyToMany
    private List<Evaluation> evaluations;

    @OneToMany(mappedBy = "eleve")
    @JsonIgnoreProperties({"eleve", "hibernateLazyInitializer", "handler"})
    private List<EvaluationResult> evaluationResults;

    // Getters and setters
    @ManyToMany
    @JsonIgnoreProperties({"eleve", "hibernateLazyInitializer", "handler"})
    private List<Evaluation> passedEvaluations;

}
