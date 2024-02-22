package com.example.project.controllers;

import com.example.project.entities.Categorie;
import com.example.project.entities.Film;
import com.example.project.service.IServiceCategorie;
import com.example.project.service.IServiceFilm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categorie/")
public class CategorieController {
    @Autowired
    IServiceCategorie iServiceCategorie;

    @Autowired
    IServiceFilm iServiceFilm;

    @GetMapping("all")
    public String listCtegories(Model model) {
        model.addAttribute("categories", iServiceCategorie.findAllCategories());
        return "index";
    }
    @GetMapping("newcategorie")
    public String afficheNewForm(Model model){
        model.addAttribute("films",iServiceFilm.findAllFilms());
        model.addAttribute("categories", iServiceCategorie.findAllCategories());
        return "addCategorie";
    }

    @PostMapping("add")
    public String add(Categorie c ){
        iServiceCategorie.createCategorie(c);
        return "redirect:/categorie/all";
    }

    @GetMapping("delete/{id}")
    public String deletedelete(@PathVariable int id){
        Categorie sansCategorie = iServiceCategorie.findCategorieById(3);
        Page<Film> films= iServiceFilm.findByCategorieId(id);
        for (Film film : films) {
            film.setCategorie(sansCategorie);
            iServiceFilm.updateFilm(film);
        }
        iServiceCategorie.deleteCategorie(id);
        return "redirect:/categorie/all";
    }

    @GetMapping("modifier/{id}")
    public String afficherModifierForm(@PathVariable int id, Model model){
        Categorie categorie = iServiceCategorie.findCategorieById(id);
        model.addAttribute("categories", categorie);
        return "editCategorie";
    }

    @PostMapping("edit")
    public String edit(@ModelAttribute Categorie c) {
        iServiceCategorie.updateCategorie(c);
        return "redirect:/categorie/all";
    }
}
