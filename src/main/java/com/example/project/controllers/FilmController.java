package com.example.project.controllers;


import com.example.project.entities.Acteur;
import com.example.project.entities.Film;

import com.example.project.service.IServiceActeur;
import com.example.project.service.IServiceCategorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.project.service.IServiceFilm;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/film/")
public class FilmController {
   @Autowired
   IServiceFilm iServiceFilm;

   @Autowired
   IServiceCategorie iServiceCategorie;

   @Autowired
    IServiceActeur iServiceActeur;
   private String upoloadDirectory=System.getProperty("user.dir")+"\\src\\main\\resources\\static\\photos";
    @GetMapping("all")
    public String listeFilms(Model model){
        model.addAttribute("films",iServiceFilm.findAllFilms());
        model.addAttribute("categories", iServiceCategorie.findAllCategories());
        return "affiche";
    }

    @GetMapping("new")
    public String afficheNewForm(Model model){
        model.addAttribute("categories",iServiceCategorie.findAllCategories());
        model.addAttribute("acteurs", iServiceActeur.findAllActeurs());
        return "ajout";
    }

    @PostMapping("add")
    public String aff(@ModelAttribute Film f, @RequestParam(value = "acteurs", required = false) List<Integer> acteurIds, Model model, @RequestParam("file") MultipartFile multipartFile){
        try {
            if (acteurIds != null && !acteurIds.isEmpty()) {
                List<Acteur> acteurs = iServiceActeur.findActeursByIds(acteurIds);
                f.setActeurs(acteurs);

            }
            // Récupérer le nom original de la photo
            String fileName = multipartFile.getOriginalFilename();

            // Définir le chemin complet où sera sauvegardée l'image
            Path filePath = Paths.get(upoloadDirectory, fileName);

            // Écrire physiquement le fichier de l'image
            try {
                Files.write(filePath, multipartFile.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Affecter le nom de l'image à l'attribut photo du film
            f.setPhoto(fileName);

            iServiceFilm.createFilm(f);
            return "redirect:/film/all";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Le titre du film doit être unique.");
            model.addAttribute("categories", iServiceCategorie.findAllCategories());
            model.addAttribute("acteurs", iServiceActeur.findAllActeurs());
            return "ajout";
        }
    }
    @GetMapping("delete/{id}")
    public String delete(@PathVariable int id){
        iServiceFilm.deleteFilm(id);
        return "redirect:/film/all";
    }

    @GetMapping("modifier/{id}")
    public String afficherModifierForm(@PathVariable int id, Model model){
        Film film = iServiceFilm.findFilmById(id);
        model.addAttribute("films", film);
        model.addAttribute("categories",iServiceCategorie.findAllCategories());
        model.addAttribute("acteurs", iServiceActeur.findAllActeurs());
        return "update";
    }

    @PostMapping("update")
    public String update(@ModelAttribute Film film, @RequestParam("file") MultipartFile multipartFile){
        try {
            // Vérifier si une nouvelle image a été fournie
            if (!multipartFile.isEmpty()) {
                // Récupérer le nom original de la nouvelle photo
                String fileName = multipartFile.getOriginalFilename();

                // Définir le chemin complet où sera sauvegardée la nouvelle image
                Path filePath = Paths.get(upoloadDirectory, fileName);

                // Écrire physiquement le fichier de la nouvelle image
                try {
                    Files.write(filePath, multipartFile.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Mettre à jour le nom de la nouvelle image dans l'objet Film
                film.setPhoto(fileName);
            }
            // Mettre à jour le film dans la base de données
            iServiceFilm.updateFilm(film);
            return "redirect:/film/all";
        } catch (Exception e) {
            // Gérer les erreurs
            e.printStackTrace();
            return "redirect:/film/modifier/" + film.getId();
        }
    }

    @PostMapping("search")
    public String searchFilms(@RequestParam("searchKeyword") String searchKeyword, Model model) {
        List<Film> films = iServiceFilm.searchByTitre(searchKeyword);
        model.addAttribute("films", films);
        model.addAttribute("searchKeyword", searchKeyword); // Ajouter cette ligne pour transmettre la valeur de recherche à la vue
        return "affiche";
    }

    @PostMapping("filterByCategory")
    public String filterByCategory(@RequestParam("categorieId") int categorieId, Model model) {
        List<Film> films;
        if (categorieId == 0) {
            films = iServiceFilm.findAllFilms();
        } else {
            films = iServiceFilm.findByCategorieId(categorieId);
        }
        model.addAttribute("films", films);
        model.addAttribute("selectedCategoryId", categorieId);
        model.addAttribute("categories", iServiceCategorie.findAllCategories());
        return "affiche";
    }

    @GetMapping("details/{id}")
    public String showFilmDetails(@PathVariable int id, Model model) {
        Film film = iServiceFilm.findFilmById(id);
        model.addAttribute("film", film);
        return "details";
    }


}
