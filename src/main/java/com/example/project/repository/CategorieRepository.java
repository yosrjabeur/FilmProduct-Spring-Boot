package com.example.project.repository;

import com.example.project.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie,Integer> {
    Categorie findByNom(String nom);

}
