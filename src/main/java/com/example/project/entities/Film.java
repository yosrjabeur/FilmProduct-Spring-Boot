package com.example.project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//annotation à partir de lombok:
//Data remplace: @getter et @setter et @toStong...
//AllArgsConstructor avec des argument
//NoArgsConstructor sans argument
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "film", uniqueConstraints = @UniqueConstraint(columnNames = "titre"))
public class Film {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titre;
    private String description;
    private int anneeparution;
    @ManyToOne
    private Categorie categorie;
    @ManyToMany
    private List <Acteur> acteurs;
    private String photo;
    private String realisateur;
}
