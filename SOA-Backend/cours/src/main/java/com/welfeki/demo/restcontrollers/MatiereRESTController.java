package com.welfeki.demo.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.welfeki.demo.entities.Matiere;
import com.welfeki.demo.service.MatiereService;

@RestController
@RequestMapping("/api/matieres")
public class MatiereRESTController {

    @Autowired
    MatiereService matiereService;

    @GetMapping
    public List<Matiere> getAllMatieres() {
        return matiereService.getAllMatieres();
    }

    @GetMapping("/{id}")
    public Matiere getMatiereById(@PathVariable("id") Long id) {
        return matiereService.getMatiere(id);
    }

    @PostMapping
    public Matiere createMatiere(@RequestBody Matiere matiere) {
        return matiereService.saveMatiere(matiere);
    }

    @PutMapping("/{id}")
    public Matiere updateMatiere(@PathVariable("id") Long id, @RequestBody Matiere matiere) {
        matiere.setId(id);
        return matiereService.updateMatiere(matiere);
    }

    @DeleteMapping("/{id}")
    public void deleteMatiereById(@PathVariable("id") Long id) {
        matiereService.deleteMatiereById(id);
    }

    @GetMapping("/search/name")
    public List<Matiere> findMatieresByName(@RequestParam("name") String name) {
        return matiereService.findByName(name);
    }

    @GetMapping("/search/nameContains")
    public List<Matiere> findMatieresByNameContains(@RequestParam("name") String name) {
        return matiereService.findByNameContains(name);
    }

    @GetMapping("/sort")
    public List<Matiere> sortMatieresByName() {
        return matiereService.findByOrderByNameAsc();
    }
}
