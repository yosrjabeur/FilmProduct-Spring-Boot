package com.example.project.controllers;

import com.example.project.entities.Acteur;
import com.example.project.entities.Categorie;
import com.example.project.exception.CategorieNotfoundException;
import com.example.project.service.IServiceActeur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/acteurs")
public class RestActeurController {
    @Autowired
    IServiceActeur iServiceActeur;
    @GetMapping("")
    public List<Acteur> getAllActeurs() {
        return iServiceActeur.findAllActeurs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Acteur> getActeurById(@PathVariable int id) {
        if (!iServiceActeur.ActeurExist(id)) {
            throw new CategorieNotfoundException();
        }
        Acteur acteur = iServiceActeur.findActeurById(id);
        return new ResponseEntity<>(acteur, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Acteur> addActeur(@RequestBody Acteur acteur) {
        Acteur addedActeur = iServiceActeur.createActeur(acteur);
        return new ResponseEntity<>(addedActeur, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Acteur> updateActeur(@RequestBody Acteur acteur) {
        Acteur updatedActeur = iServiceActeur.updateActeur(acteur);
        return new ResponseEntity<>(updatedActeur, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteActeur(@PathVariable int id) {
        iServiceActeur.deleteActeur(id);
        return new ResponseEntity<>("Acteur with id " + id + " has been deleted.", HttpStatus.OK);
    }
}
