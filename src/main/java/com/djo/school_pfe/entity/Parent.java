package com.djo.school_pfe.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @Lob
    @Column(name="photo",length = 1000000)
    private byte[] photo;


    @ManyToMany(mappedBy = "parents", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"parents", "hibernateLazyInitializer", "handler"})
    private List<Eleve> enfants;

}
