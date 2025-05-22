package ru.kata.spring.boot_security.demo.mapper;

import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserDTO userDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                null,
                user.getFirstName(),
                user.getLastName(),
                user.getAge(),
                user.getEmail(),
                user.getRoles().stream().map(Role::getName).collect(Collectors.toSet())
        );
    }

    public static User fromDTO(UserDTO dto, Set<Role> roles) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setAge(dto.getAge());
        user.setEmail(dto.getEmail());
        user.setRoles(roles);
        return user;
    }
}
