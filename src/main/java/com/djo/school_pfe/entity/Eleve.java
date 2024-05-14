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




    @Column(name = "classe_id", insertable = false, updatable = false)
    private Long classeId;


    @ManyToOne
    @JsonIgnoreProperties("eleves")
    @JsonIgnore
    private Classe classe;

    @JsonIgnore
    @ManyToMany
    private List<Parent> parents;

    @JsonIgnore
    @ManyToMany
    private List<Evaluation> evaluations;

}
