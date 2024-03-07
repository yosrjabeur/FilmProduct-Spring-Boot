package com.example.project.controllers;

import com.example.project.entities.Film;
import com.example.project.exception.FilmNotfoundException;
import com.example.project.service.IServiceFilm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/films")
public class RestFilmController {

    @Autowired
    IServiceFilm iServiceFilm;

    @GetMapping("")
    // public List<Film> getAll() {
    //return iServiceFilm.findAllFilms();}
    public ResponseEntity<List<Film>> getAll(){
        return new ResponseEntity<>(iServiceFilm.findAllFilms(),HttpStatus.OK) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getById(@PathVariable int id) {
        if (!iServiceFilm.filmExist(id)) {
            throw new FilmNotfoundException();
        }
        Film film = iServiceFilm.findFilmById(id);
        return new ResponseEntity<>(film, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Film> add(@RequestBody Film film) {
        Film addedFilm = iServiceFilm.createFilm(film);
        return new ResponseEntity<>(addedFilm, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        Film film = iServiceFilm.findFilmById(id);
        if (film == null) {
            throw new FilmNotfoundException();
        }
        iServiceFilm.deleteFilm(id);
        return new ResponseEntity<>("Film avec l'ID " + id + " a été supprimé avec succès.", HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Film> updateFilm(@PathVariable int id, @RequestBody Film film) {
        Film existingFilm = iServiceFilm.findFilmById(id);
        if (existingFilm == null) {
            throw new FilmNotfoundException();
        }
        existingFilm.setTitre(film.getTitre());
        existingFilm.setDescription(film.getDescription());
        if (film.getPhoto() != null) {
            existingFilm.setPhoto(film.getPhoto());
        }
        Film updatedFilm = iServiceFilm.updateFilm(existingFilm);
        return new ResponseEntity<>(updatedFilm, HttpStatus.OK);
    }
}
