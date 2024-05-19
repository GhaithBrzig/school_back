package com.djo.school_pfe.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Parents")
public class Parent extends UserEntity{

    @Lob
    @Column(name="photo",length = 1000000)
    private byte[] photo;


    @Enumerated(EnumType.STRING)
    @Column(name = "photo_state")
    private PhotoState photoState;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "parent_eleve",
            joinColumns = @JoinColumn(name = "parent_id"),
            inverseJoinColumns = @JoinColumn(name = "eleve_id")
    )
    @JsonIgnoreProperties({"parents", "hibernateLazyInitializer", "handler"})
    private List<Eleve> enfants;

    public Parent(byte[] photo, PhotoState photoState, List<Eleve> enfants) {
        super();
        this.photo = photo;
        this.photoState = photoState;
        this.enfants = enfants;
    }

}
