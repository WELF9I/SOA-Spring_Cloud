package com.welfeki.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welfeki.demo.entities.Cours;
import com.welfeki.demo.entities.Matiere;
import com.welfeki.demo.repos.CoursRepository;

@Service
public class CoursServiceImpl implements CoursService {

    @Autowired
    CoursRepository coursRepository;


    @Override
    public Cours saveCours(Cours c) {
        return coursRepository.save(c);
    }

    @Override
    public Cours updateCours(Cours c) {
        return coursRepository.save(c);
    }

    @Override
    public void deleteCours(Cours c) {
        coursRepository.delete(c);
    }

    @Override
    public void deleteCoursById(Long id) {
        coursRepository.deleteById(id);
    }

    @Override
    public Cours getCours(Long id) {
        return coursRepository.findById(id).get();
    }

    @Override
    public List<Cours> getAllCours() {
        return coursRepository.findAll();
    }

    @Override
    public List<Cours> findByTitle(String title) {
        return coursRepository.findByTitle(title);
    }

    @Override
    public List<Cours> findByTitleContains(String title) {
        return coursRepository.findByTitleContains(title);
    }

    @Override
    public List<Cours> findByTitleAndDuration(String title, Integer duration) {
        return coursRepository.findByTitleAndDuration(title, duration);
    }

    @Override
    public List<Cours> findByMatiere(Matiere matiere) {
        return coursRepository.findByMatiere(matiere);
    }

    @Override
    public List<Cours> findByMatiereId(Long id) {
        return coursRepository.findByMatiereId(id);
    }

    @Override
    public List<Cours> findByOrderByTitleAsc() {
        return coursRepository.findByOrderByTitleAsc();
    }

    @Override
    public List<Cours> trierCoursTitleDuration() {
        return coursRepository.trierCoursTitleDuration();
    }
}