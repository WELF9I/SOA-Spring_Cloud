package com.welfeki.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.welfeki.demo.entities.Matiere;
import com.welfeki.demo.repos.MatiereRepository;

@Service
public class MatiereServiceImpl implements MatiereService {

    @Autowired
    MatiereRepository matiereRepository;

    @Override
    public Matiere getMatiereById(Long id) {
        return matiereRepository.findById(id).orElse(null);
    }

    @Override
    public List<Matiere> getAllMatieres() {
        return matiereRepository.findAll();
    }

    @Override
    public Matiere saveMatiere(Matiere m) {
        return matiereRepository.save(m);
    }

    @Override
    public Matiere updateMatiere(Matiere m) {
        return matiereRepository.save(m);
    }

    @Override
    public void deleteMatiere(Matiere m) {
        matiereRepository.delete(m);
    }

    @Override
    public void deleteMatiereById(Long id) {
        matiereRepository.deleteById(id);
    }

    @Override
    public Matiere getMatiere(Long id) {
        return matiereRepository.findById(id).orElse(null);
    }

    @Override
    public List<Matiere> findByName(String name) {
        return matiereRepository.findByName(name);
    }

    @Override
    public List<Matiere> findByNameContains(String name) {
        return matiereRepository.findByNameContains(name);
    }

    @Override
    public List<Matiere> findByOrderByNameAsc() {
        return matiereRepository.findByOrderByNameAsc();
    }
}
