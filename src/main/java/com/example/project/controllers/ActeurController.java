package com.example.project.controllers;

import com.example.project.entities.Acteur;
import com.example.project.service.IServiceActeur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/acteur/")
public class ActeurController {

    @Autowired
    IServiceActeur iServiceActeur;

    @GetMapping("all")
    public String listActeurs(Model model) {
        model.addAttribute("acteurs", iServiceActeur.findAllActeurs());
        return "listeActeur";
    }
    @GetMapping("newacteur")
    public String afficheNewForm(Model model){
        model.addAttribute("acteurs",iServiceActeur.findAllActeurs());
        return "addActeur";
    }
    @PostMapping("add")
    public String add(Acteur a ){
        iServiceActeur.createActeur(a);
        return "redirect:/acteur/all";
    }
    @GetMapping("delete/{id}")
    public String delete(@PathVariable int id) {
        iServiceActeur.deleteActeur(id);
        return "redirect:/acteur/all";
    }
    @PostMapping("edit")
    public String edit(@ModelAttribute Acteur a) {
        iServiceActeur.updateActeur(a);
        return "editActeur";
    }
    @GetMapping("modifier/{id}")
    public String afficherModifierForm(@PathVariable int id, Model model){
        Acteur acteur = iServiceActeur.findActeurById(id);
        model.addAttribute("acteurs", acteur);
        return "redirect:/acteur/all";
    }
}