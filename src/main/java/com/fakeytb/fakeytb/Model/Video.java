package com.fakeytb.fakeytb.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import java.util.UUID;
import java.util.Set;
import jakarta.persistence.ManyToMany;

@Entity
public class Video {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String shortDescription;

    @Column
    private String longDescription;

    @ManyToMany
    private Set<Tag> tags;

    // Constructeur par défaut
    public Video() {
    }

    // Constructeur avec paramètres
    public Video(String title, String shortDescription, String longDescription) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Set<Tag> getTags() {
        return tags;
}

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
