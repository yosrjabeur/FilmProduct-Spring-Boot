package com.example.project.service;

import com.example.project.entities.Categorie;
import com.example.project.entities.Film;
import com.example.project.repository.CategorieRepository;
import com.example.project.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class  ServiceCategorie implements IServiceCategorie {
    @Autowired
    CategorieRepository categorieRepository;
    FilmRepository filmRepository;

    @Override
    public Categorie createCategorie(Categorie c){
        return categorieRepository.save(c);
    }
    @Override
    public Categorie findCategorieById(int id) { return categorieRepository.findById(id).get(); }
    @Override
    public Categorie updateCategorie(Categorie c){
        return categorieRepository.save(c);
    }
    @Override
    public void deleteCategorie(int id){
        categorieRepository.deleteById(id);
    }

    @Override
    public Categorie findCategorieByName(String nom) {
        return categorieRepository.findByNom(nom);
    }

    @Override
    public List<Categorie> findAllCategories(){ return categorieRepository.findAll();}
}
