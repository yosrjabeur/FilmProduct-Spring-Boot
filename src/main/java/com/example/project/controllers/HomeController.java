package com.example.project.controllers;

import com.example.project.entities.Categorie;
import com.example.project.entities.Film;
import com.example.project.service.IServiceCategorie;
import com.example.project.service.IServiceFilm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private IServiceCategorie iServiceCategorie;
    
    @Autowired
    private IServiceFilm iServiceFilm;
    
    @GetMapping("/")
    public String index(Model model) {
        List<Categorie> categories = iServiceCategorie.findAllCategories();
        model.addAttribute("categories", categories);
        return "index";
    }
    @GetMapping("/categorie/{id}")
    public String filmsParCategorie(@PathVariable("id") int id, Model model) {
        Page<Film> films = iServiceFilm.findByCategorieId(id);
        if (films == null) {
            return "redirect:/";
        }
        model.addAttribute("films", films);
        return "filmsCategorie";
    }
}

