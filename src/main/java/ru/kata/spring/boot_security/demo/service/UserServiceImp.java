package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImp implements UserService {

    private final UserDao userDao;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserDao userDao,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }


    @Override
    public void addWithRoles(User user, List<Long> roleIds) {
        Set<Role> roles = roleIds.stream()
                .map(roleRepository::getById)
                .collect(Collectors.toSet());

        user.setRoles(roles);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userDao.add(user);
    }

    @Override
    public void updateWithRoles(User user, List<Long> roleIds) {
        User existingUser = userDao.getUserById(user.getId());

        existingUser.setUsername(user.getUsername());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setAge(user.getAge());

        //  хешировать пароль только если поле заполнено!
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        Set<Role> roles = roleIds.stream()
                .map(roleRepository::getById)
                .collect(Collectors.toSet());
        existingUser.setRoles(roles);

        userDao.update(existingUser);
    }

    @Override
    public List<User> getAllUsersWithRoleString() {
        List<User> users = getAllUsers();
        for (User u : users) {
            String roleString = u.getRoles().stream()
                    .map(role -> role.getName().replace("ROLE_", ""))
                    .collect(Collectors.joining(" "));
            u.setRoleString(roleString);
        }
        return users;
    }

}