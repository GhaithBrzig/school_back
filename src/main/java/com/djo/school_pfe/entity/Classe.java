package com.djo.school_pfe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Classes")
@JsonIgnoreProperties({"eleves", "enseignants", "evaluations"})
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String niveau;
    private String nom;
    private int nbrEleves;

    @JsonIgnore
    @OneToMany(mappedBy = "classe")
    @JsonIgnoreProperties("classe")
    private List<Eleve> eleves;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "classe_enseignant",
            joinColumns = @JoinColumn(name = "classe_id"),
            inverseJoinColumns = @JoinColumn(name = "enseignant_id"))
    private List<Enseignant> enseignants = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "classe_evaluation",
            joinColumns = @JoinColumn(name = "classe_id"),
            inverseJoinColumns = @JoinColumn(name = "evaluation_id")
    )
    private List<Evaluation> evaluations = new ArrayList<>();

    public void addEvaluation(Evaluation evaluation) {
        this.evaluations.add(evaluation);
        evaluation.getClasses().add(this);
    }
}
