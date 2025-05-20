package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {


    void delete(Long id);

    List<User> getAllUsers();


    void saveUser(User user, List<Long> roleIds);

    void updateUser(User user, List<Long> roleIds);

    List<User> getAllUsersWithRoleString();

}

