package com.example.project.service;

import com.example.project.entities.Film;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IServiceFilm {
    public Film createFilm(Film f);
    public Film findFilmById(int id);
    public List<Film> findAllFilms();
    public Film updateFilm(Film f);
    public void deleteFilm (int id);
    List<Film> searchByTitre(String titre);
    List<Film> findByCategorieId(int categorieId);
    Page <Film> findAllFilmsPage(int pageNum, int pageSize, String sortField, String sortDir);

}
