package com.fakeytb.fakeytb.Controller;

import com.fakeytb.fakeytb.Model.Role;
import com.fakeytb.fakeytb.Model.User;
import com.fakeytb.fakeytb.Repository.RoleRepository;
import com.fakeytb.fakeytb.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
public class RoleController{
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> roles = roleRepository.findAll();
        return ResponseEntity.ok(roles);
    }
    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        String title = role.getTitle();
        if (roleRepository.findByTitle(title) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Un rôle avec le même titre existe déjà");
        }

        Role createdRole = roleRepository.save(role);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    @GetMapping("/roles/id/{roleId}")
    public ResponseEntity<Role> getRoleById(@PathVariable UUID roleId) {
        Role role = roleRepository.findById(roleId).orElse(null);
        if (role == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(role);
    }

    @GetMapping("/roles/title/{roleTitle}")
    public ResponseEntity<Role> getRoleByTitle(@PathVariable String roleTitle) {
        Role role = roleRepository.findByTitle(roleTitle);
        return ResponseEntity.ok(role);
    }

    @GetMapping("/roles/{roleTitle}/users")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String roleTitle) {
        // Trouver le rôle par son titre
        Role role = roleRepository.findByTitle(roleTitle);

        if (role == null) {
            return ResponseEntity.notFound().build();
        }

        // Récupérer tous les utilisateurs appartenant à ce rôle
        List<User> users = userRepository.findByRole(role);

        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/roles/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable UUID roleId) {
        roleRepository.deleteById(roleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


