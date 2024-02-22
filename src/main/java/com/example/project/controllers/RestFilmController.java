package com.example.project.controllers;

import com.example.project.entities.Film;
import com.example.project.service.IServiceFilm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/films")
public class RestFilmController {

    @Autowired
    IServiceFilm iServiceFilm;

    @GetMapping("")
    public List<Film> getAll() {
        return iServiceFilm.findAllFilms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getById(@PathVariable int id) {
        Film film = iServiceFilm.findFilmById(id);
        if (film != null) {
            return new ResponseEntity<>(film, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Film> addFilm(@RequestBody Film film) {
        // Traitez l'ajout du film ici en utilisant le service approprié
        Film addedFilm = iServiceFilm.createFilm(film);

        // Retournez une réponse avec le film ajouté et le statut CREATED (201)
        return new ResponseEntity<>(addedFilm, HttpStatus.CREATED);
    }


}
