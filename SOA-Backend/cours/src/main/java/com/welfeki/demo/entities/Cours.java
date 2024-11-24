package com.welfeki.demo.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private Integer duration;
    private String videoUrl;
    private LocalDateTime createdDate;

    @ManyToOne
    private Matiere matiere;

    /*@OneToOne
   private Image image;*/
    @OneToMany (mappedBy = "cours")
    private List<Image> images;

    public Cours() {}

    public Cours(String title, String content, Integer duration, String videoUrl, LocalDateTime createdDate) {
        this.title = title;
        this.content = content;
        this.duration = duration;
        this.videoUrl = videoUrl;
        this.createdDate = createdDate;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }


    @Override
    public String toString() {
        return "Cours{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", duration=" + duration +
                ", videoUrl='" + videoUrl + '\'' +
                ", createdDate=" + createdDate +
                ", matiere=" + matiere +
                '}';
    }

}