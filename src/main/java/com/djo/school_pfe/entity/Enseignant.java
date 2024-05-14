package com.djo.school_pfe.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Enseignant extends UserEntity{

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Matiere matiere;

    @ManyToOne
    @JoinColumn(name = "classe_id")
    private Classe classe;


    @OneToMany(mappedBy = "enseignant")
    @JsonIgnore
    private List<Evaluation> evaluations;


}