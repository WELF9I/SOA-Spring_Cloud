package com.welfeki.demo.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.welfeki.demo.entities.Matiere;

public interface MatiereRepository extends JpaRepository<Matiere, Long> {

    List<Matiere> findByName(String name);

    List<Matiere> findByNameContains(String name);

    List<Matiere> findByOrderByNameAsc();

    @Query("SELECT m FROM Matiere m WHERE m.name LIKE %:name% AND m.teacherName = :teacherName")
    List<Matiere> findByNameAndTeacherName(@Param("name") String name, @Param("teacherName") String teacherName);
}
