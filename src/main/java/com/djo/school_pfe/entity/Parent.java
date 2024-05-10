package com.djo.school_pfe.entity;
import lombok.*;


import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Parents")
public class Parent extends UserEntity{
    @ManyToMany(mappedBy = "parents")
    private List<Eleve> enfants;
}
