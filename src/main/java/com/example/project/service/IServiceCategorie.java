package com.example.project.service;

import com.example.project.entities.Categorie;

import java.util.List;

public interface IServiceCategorie {

    public List<Categorie> findAllCategories();
    public Categorie createCategorie(Categorie f);
    public Categorie findCategorieById(int id);
    public Categorie updateCategorie(Categorie c);
    public void deleteCategorie (int id);
    Categorie findCategorieByName(String nom);
}
