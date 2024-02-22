package com.example.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.project.entities.Film;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film,Integer>{
    List<Film> findByTitre(String titre);
    Page<Film> findByTitreContainingIgnoreCase(String titre, Pageable pageable);

    Page<Film> findByCategorieId(int categorieId, Pageable pageable);


}
