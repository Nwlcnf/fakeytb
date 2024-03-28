package com.fakeytb.fakeytb.Controller;
import com.fakeytb.fakeytb.DTO.RoleDto;
import com.fakeytb.fakeytb.Model.Role;
import com.fakeytb.fakeytb.Model.User;
import com.fakeytb.fakeytb.DTO.UserDto;
import com.fakeytb.fakeytb.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream()
                .map(user -> convertToDto(user))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }

    private UserDto convertToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setRole(modelMapper.map(user.getRole(), RoleDto.class));
        return userDto;
    }
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        String username = user.getUsername();
        if (userRepository.findByUsername(username) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Un utilisateur avec le même nom d'utilisateur existe déjà");
        }

        User createdUser = userRepository.save(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

        @GetMapping("/username/{username}")
        public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            UserDto userDto = modelMapper.map(user, UserDto.class);
            return ResponseEntity.ok(userDto);
        }

        @GetMapping("/id/{userId}")
        public ResponseEntity<UserDto> getUserById(@PathVariable UUID userId) {
            User user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            UserDto userDto = modelMapper.map(user, UserDto.class);
            return ResponseEntity.ok(userDto);
        }

        @GetMapping("/search")
        public ResponseEntity<List<UserDto>> searchUsersByUsername(@RequestParam String username) {
            List<User> users = userRepository.findByUsernameContaining(username);
            List<UserDto> userDtos = users.stream()
                    .map(user -> modelMapper.map(user, UserDto.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(userDtos);
        }

        @GetMapping("/{userId}/role")
        public ResponseEntity<RoleDto> getUserRole(@PathVariable UUID userId) {
            User user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            Role role = user.getRole();
            RoleDto roleDto = modelMapper.map(role, RoleDto.class);
            return ResponseEntity.ok(roleDto);
        }

        @DeleteMapping("/{userId}")
        public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
            userRepository.deleteById(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
}

