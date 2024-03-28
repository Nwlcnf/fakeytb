package com.fakeytb.fakeytb.DTO;

import java.util.Set;

public class TagDTO {

    private String title;

    // Constructeur par défaut
    public TagDTO() {
    }

    // Constructeur avec paramètres
    public TagDTO(String title) {
        this.title = title;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
