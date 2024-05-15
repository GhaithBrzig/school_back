package com.djo.school_pfe.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


import javax.persistence.*;

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
    private String question;
    private String reponse;

    @JsonIgnore
    @ManyToOne
    private Classe classe;


    @JsonIgnore
    @ManyToOne
    private Enseignant enseignant;

    // Getters and setters
}
