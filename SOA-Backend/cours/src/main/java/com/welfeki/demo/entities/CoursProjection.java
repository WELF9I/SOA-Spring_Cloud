package com.welfeki.demo.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "title", types = { Cours.class })
public interface CoursProjection {
    public String getTitle();
}
