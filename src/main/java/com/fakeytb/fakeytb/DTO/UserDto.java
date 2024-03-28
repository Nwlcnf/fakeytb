package com.fakeytb.fakeytb.DTO;

import com.fakeytb.fakeytb.Model.Role;

import java.util.UUID;

public class UserDto {
    private UUID id;
    private String username;
    private RoleDto role;

    // Constructeurs
    public UserDto() {
    }

    public UserDto(UUID id, String username, RoleDto role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    // Getters et setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RoleDto getRole() {
        return role;
    }

    public void setRole(RoleDto role) {
        this.role = role;
    }
}
