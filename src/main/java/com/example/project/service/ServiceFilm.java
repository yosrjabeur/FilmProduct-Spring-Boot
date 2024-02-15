package com.example.project.service;

import com.example.project.entities.Film;
import com.example.project.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServiceFilm implements IServiceFilm{

    @Autowired
    FilmRepository filmRepository;

     @Override
    public Film createFilm(Film f){
         return filmRepository.save(f);
     }
     @Override
    public Film findFilmById(int id) {
         return filmRepository.findById(id).get();
     }
     @Override
     public List<Film> findAllFilms(){
         return filmRepository.findAll();
     }
     @Override
     public Film updateFilm(Film f){
         return filmRepository.save(f);
     }
     @Override
     public void deleteFilm(int id){
         filmRepository.deleteById(id);
     }

    @Override
    public List<Film> searchByTitre(String titre) {
        return filmRepository.findByTitre(titre);
    }
    public  List<Film> findByCategorieId(int categorieId){
         return  filmRepository.findByCategorieId(categorieId);
    }

}


