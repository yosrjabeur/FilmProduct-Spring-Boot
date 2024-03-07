package com.example.project.controllers;

import com.example.project.entities.Categorie;
import com.example.project.entities.Film;
import com.example.project.exception.CategorieNotfoundException;
import com.example.project.exception.FilmNotfoundException;
import com.example.project.service.IServiceCategorie;
import com.example.project.service.IServiceFilm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class RestCategorieController {
    @Autowired
    IServiceCategorie iServiceCategorie;

    @Autowired
    IServiceFilm iServiceFilm;
    @GetMapping("")
    public List<Categorie> getAllCategories() {
        return iServiceCategorie.findAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categorie> getCategoryById(@PathVariable int id) {
        if (!iServiceCategorie.CategorieExist(id)) {
            throw new CategorieNotfoundException();
        }
        Categorie categorie = iServiceCategorie.findCategorieById(id);
        return new ResponseEntity<>(categorie, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Categorie> addCategory(@RequestBody Categorie categorie) {
        Categorie addedCategorie = iServiceCategorie.createCategorie(categorie);
        return new ResponseEntity<>(addedCategorie, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Categorie> updateCategory(@RequestBody Categorie categorie) {
        Categorie updatedCategorie = iServiceCategorie.updateCategorie(categorie);
        return new ResponseEntity<>(updatedCategorie, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int id) {
        iServiceCategorie.deleteCategorie(id);
        return new ResponseEntity<>("Category with id " + id + " has been deleted.", HttpStatus.OK);
    }
}