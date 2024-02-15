package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.project.entities.Film;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film,Integer>{
    List<Film> findByTitre(String titre);
    List<Film> findByCategorieId(int categorieId);


}
