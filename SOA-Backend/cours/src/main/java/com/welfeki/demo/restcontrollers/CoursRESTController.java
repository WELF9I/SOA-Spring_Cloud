package com.welfeki.demo.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.welfeki.demo.entities.Cours;
import com.welfeki.demo.entities.Matiere;
import com.welfeki.demo.service.CoursService;
import com.welfeki.demo.service.MatiereService;

@RestController
@RequestMapping("/api/cours")
public class CoursRESTController {
    @Autowired
    CoursService coursService;

    @Autowired
    MatiereService matiereService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Cours> getAllCours() {
        return coursService.getAllCours();
    }

    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public Cours getCoursById(@PathVariable("id") Long id) {
        return coursService.getCours(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Cours createCours(@RequestBody Cours cours) {
        if (cours.getMatiere() != null && cours.getMatiere().getId() != null) {
            Matiere matiere = matiereService.getMatiereById(cours.getMatiere().getId());
            cours.setMatiere(matiere);
        }
        return coursService.saveCours(cours);
    }

    @PutMapping("/{id}")
    public Cours updateCours(@PathVariable("id") Long id, @RequestBody Cours cours) {
        Cours existingCours = coursService.getCours(id);
        if (existingCours != null) {
            existingCours.setTitle(cours.getTitle());
            existingCours.setContent(cours.getContent());
            existingCours.setDuration(cours.getDuration());
            existingCours.setVideoUrl(cours.getVideoUrl());
            existingCours.setCreatedDate(cours.getCreatedDate());

            if (cours.getMatiere() != null && cours.getMatiere().getId() != null) {
                Matiere matiere = matiereService.getMatiereById(cours.getMatiere().getId());
                existingCours.setMatiere(matiere);
            }
            return coursService.updateCours(existingCours);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteCours(@PathVariable("id") Long id) {
        coursService.deleteCoursById(id);
    }

    @RequestMapping(value="/matiere/{id}",method = RequestMethod.GET)
    public List<Cours> getCoursByMatiereId(@PathVariable("id") Long id) {
        return coursService.findByMatiereId(id);
    }
}