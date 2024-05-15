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
@Table(name = "Enseignants")
@JsonIgnoreProperties({"eleves"})
public class Enseignant extends UserEntity{

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Matiere matiere;

    @ManyToMany
    private List<Eleve> eleves;

    @ManyToMany
    private List<Classe> classes;
@JsonIgnore
    @OneToMany(mappedBy = "enseignant")
    @JsonIgnore
    private List<Evaluation> evaluations;


}