package com.example.project.controllers;


import com.example.project.entities.Acteur;
import com.example.project.entities.Film;

import com.example.project.service.IServiceActeur;
import com.example.project.service.IServiceCategorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        //model.addAttribute("films",iServiceFilm.findAllFilms());
        model.addAttribute("categories", iServiceCategorie.findAllCategories());
        return findPagineted(1,"titre","asc",model);
        //return "affiche";
    }

    @GetMapping("page/{pageNum}")
    public String findPagineted( @PathVariable int pageNum,@RequestParam String sortField,@RequestParam String sortDir,Model model) {
        int PAGE_SIZE=4;
        Page<Film> page = iServiceFilm.findAllFilmsPage(pageNum, PAGE_SIZE,sortField,sortDir);
        model.addAttribute("films", page.getContent());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());

        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",sortDir.equals("asc")? "desc":"asc");
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
            String fileName = multipartFile.getOriginalFilename();
            Path filePath = Paths.get(upoloadDirectory, fileName);

            try {
                Files.write(filePath, multipartFile.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
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
    public String update(@ModelAttribute Film film, @RequestParam("file") MultipartFile multipartFile, @RequestParam(value = "acteurs", required = false) List<Integer> acteurIds) {
        try {
            if (!multipartFile.isEmpty()) {
                String fileName = multipartFile.getOriginalFilename();
                Path filePath = Paths.get(upoloadDirectory, fileName);
                try {
                    Files.write(filePath, multipartFile.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                film.setPhoto(fileName);
            } else {
                Film existingFilm = iServiceFilm.findFilmById(film.getId());
                film.setPhoto(existingFilm.getPhoto());
            }
            if (acteurIds != null && !acteurIds.isEmpty()) {
                List<Acteur> acteurs = iServiceActeur.findActeursByIds(acteurIds);
                film.setActeurs(acteurs);
            } else {
                Film existingFilm = iServiceFilm.findFilmById(film.getId());
                film.setActeurs(existingFilm.getActeurs());
            }
            iServiceFilm.updateFilm(film);
            return "redirect:/film/all";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/film/modifier/" + film.getId();
        }
    }

    @PostMapping("search")
    public String searchFilms(@RequestParam("searchKeyword") String searchKeyword, Model model) {
        int PAGE_SIZE = 4;
        int pageNum = 0;
        String sortField = "titre";
        String sortDir = "asc";
        Pageable pageable = PageRequest.of(pageNum, PAGE_SIZE, Sort.by(Sort.Direction.fromString(sortDir), sortField));
        Page<Film> page = iServiceFilm.searchByTitre(searchKeyword);
        List<Film> films = page.getContent();
        model.addAttribute("films", films);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("searchKeyword", searchKeyword);

        return "affiche";
    }

    @PostMapping("filterByCategory")
    public String filterByCategory(@RequestParam("categorieId") int categorieId, Model model) {
        int PAGE_SIZE = 4;
        int pageNum = 0;
        String sortField = "titre";
        String sortDir = "asc";

        Page<Film> page = iServiceFilm.findByCategorieId(categorieId);
        List<Film> films = page.getContent();
        model.addAttribute("films", films);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
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
