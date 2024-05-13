package com.djo.school_pfe.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Enseignants")
public class Enseignant extends UserEntity{
    private String matiere;

    @ManyToMany
    private List<Eleve> eleves;

    @ManyToMany
    private List<Classe> classes;
@JsonIgnore
    @OneToMany(mappedBy = "enseignant")
    private List<Evaluation> evaluations;
}
