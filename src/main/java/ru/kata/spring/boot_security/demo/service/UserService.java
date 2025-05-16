package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {


    void delete(Long id);

    List<User> getAllUsers();

    User getUserById(Long id);

    void addWithRoles(User user, List<Long> roleIds);

    void updateWithRoles(User user, List<Long> roleIds);

    List<Role> getAllRoles();

    public List<User> getAllUsersWithRoleString();
    }

