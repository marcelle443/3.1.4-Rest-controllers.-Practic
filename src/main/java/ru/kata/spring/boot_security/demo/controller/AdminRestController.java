package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.mapper.UserMapper;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public AdminRestController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(UserMapper::userDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@RequestBody UserDTO userDTO) {
        Set<Role> roles = userDTO.getRoles().stream()
                .map(roleRepository::findByName)
                .collect(Collectors.toSet());
        User user = UserMapper.fromDTO(userDTO, roles);
        userService.saveUser(user, roles.stream().map(Role::getId).collect(Collectors.toList()));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        Set<Role> roles = userDTO.getRoles().stream()
                .map(roleRepository::findByName)
                .collect(Collectors.toSet());
        User user = UserMapper.fromDTO(userDTO, roles);
        user.setId(id);
        userService.updateUser(user, roles.stream().map(Role::getId).collect(Collectors.toList()));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
