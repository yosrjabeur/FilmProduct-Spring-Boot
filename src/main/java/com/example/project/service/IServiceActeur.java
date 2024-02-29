package com.example.project.service;

import com.example.project.entities.Acteur;

import java.util.List;

public interface IServiceActeur {

    public List<Acteur> findAllActeurs();
    public Acteur findActeurById(int id);
    public Acteur createActeur(Acteur a);
    public Acteur updateActeur(Acteur a);
    public void deleteActeur(int id);
    List<Acteur> findActeursByIds(List<Integer> ids);

    Boolean ActeurExist(int id);
}
