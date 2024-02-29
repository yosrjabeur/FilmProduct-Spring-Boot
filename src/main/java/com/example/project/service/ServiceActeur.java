package com.example.project.service;

import com.example.project.entities.Acteur;
import com.example.project.repository.ActeurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ServiceActeur implements IServiceActeur{

    @Autowired
    ActeurRepository acteurRepository;

    @Override
    public List<Acteur> findAllActeurs() { return acteurRepository.findAll();}
    @Override
    public Acteur findActeurById(int id) { return acteurRepository.findById(id).get();}
    @Override
    public Acteur createActeur(Acteur a) {
        return acteurRepository.save(a);
    }

    @Override
    public Acteur updateActeur(Acteur a) {
        return acteurRepository.save(a);
    }

    @Override
    public List<Acteur> findActeursByIds(List<Integer> ids) {
        return acteurRepository.findAllById(ids);
    }

    @Override
    public void deleteActeur(int id){
        acteurRepository.deleteById(id);
    }

    @Override
    public Boolean ActeurExist(int id) {
        return acteurRepository.existsById(id);
    }
}
