package com.djo.school_pfe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Evaluations")
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    @OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();


    @ManyToOne
    private Enseignant enseignant;

    @ManyToMany(mappedBy = "evaluations")
    private List<Classe> classes = new ArrayList<>();
}
