package com.djo.school_pfe.entity;
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
    @ManyToOne
    private Classe classe;

    @ManyToMany
    private List<Parent> parents;

    @ManyToMany
    private List<Evaluation> evaluations;

    // Getters and setters
}
