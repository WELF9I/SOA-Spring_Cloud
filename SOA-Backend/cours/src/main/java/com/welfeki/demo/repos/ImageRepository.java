package com.welfeki.demo.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.welfeki.demo.entities.Image;

public interface ImageRepository extends JpaRepository<Image , Long> {
}