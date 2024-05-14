package com.djo.school_pfe.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "classe")
    @JsonIgnoreProperties("classe")
    private List<Eleve> eleves;

    @ManyToMany(mappedBy = "classes")
    @JsonIgnoreProperties("classes")
    private List<Enseignant> enseignants;

    @ManyToMany
    @JoinTable(
            name = "classe_evaluation",
            joinColumns = @JoinColumn(name = "classe_id"),
            inverseJoinColumns = @JoinColumn(name = "evaluation_id"))
    @JsonIgnoreProperties("classes")
    private List<Evaluation> evaluations;

    // Getters and setters
}
