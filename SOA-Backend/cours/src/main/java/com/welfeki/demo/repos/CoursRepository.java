package com.welfeki.demo.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.welfeki.demo.entities.Cours;
import com.welfeki.demo.entities.Matiere;

public interface CoursRepository extends JpaRepository<Cours, Long> {

    List<Cours> findByTitle(String title);

    List<Cours> findByTitleContains(String title);

    @Query("select c from Cours c where c.title like %:title% and c.matiere = :matiere")
    List<Cours> findByTitleAndMatiere(@Param("title") String title, @Param("matiere") Matiere matiere);

    @Query("select c from Cours c where c.matiere.id = :matiereId")
    List<Cours> findByMatiereId(@Param("matiereId") Long matiereId);

    List<Cours> findByOrderByTitleAsc();

    List<Cours> findByTitleAndDuration(String title, Integer duration);

    List<Cours> findByMatiere(Matiere matiere);

    @Query("select c from Cours c order by c.title asc, c.duration asc")
    List<Cours> trierCoursTitleDuration();
}
