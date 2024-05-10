package com.djo.school_pfe.entity;

import lombok.*;


import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Admins")
public class Admin extends UserEntity{
    private String role;

}
