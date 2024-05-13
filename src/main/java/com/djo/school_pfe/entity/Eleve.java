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
public class Eleve{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eleveId;

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;

    @ManyToOne
    @JsonIgnoreProperties("eleves")
    @JsonIgnore
    private Classe classe;

    @ManyToMany
    private List<Parent> parents;

    @ManyToMany
    private List<Evaluation> evaluations;

}
