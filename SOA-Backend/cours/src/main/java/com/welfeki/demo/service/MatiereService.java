package com.welfeki.demo.service;

import java.util.List;
import com.welfeki.demo.entities.Matiere;

public interface MatiereService {

    Matiere saveMatiere(Matiere m);
    Matiere updateMatiere(Matiere m);
    void deleteMatiere(Matiere m);
    void deleteMatiereById(Long id);
    Matiere getMatiere(Long id);
    List<Matiere> getAllMatieres();
    List<Matiere> findByName(String name);
    List<Matiere> findByNameContains(String name);
    List<Matiere> findByOrderByNameAsc();
    Matiere getMatiereById(Long id);
}
