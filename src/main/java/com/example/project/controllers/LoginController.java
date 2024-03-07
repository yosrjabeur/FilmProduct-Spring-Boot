package com.example.project.controllers;

import com.example.project.entities.Categorie;
import com.example.project.service.IServiceCategorie;
import com.example.project.service.IServiceFilm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}

