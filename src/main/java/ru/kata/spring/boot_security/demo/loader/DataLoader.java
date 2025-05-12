package ru.kata.spring.boot_security.demo.loader;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;


import javax.annotation.PostConstruct;
import java.beans.Transient;
import java.util.Set;


@Component
public class DataLoader {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public DataLoader(PasswordEncoder passwordEncoder,
                      UserRepository userRepository,
                      RoleRepository roleRepository) {

        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    @PostConstruct
    public void dataInit() {

        if (userRepository.count() == 0) {

            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setName("ROLE_ADMIN");
                roleRepository.save(adminRole);
            }

            Role userRole = roleRepository.findByName("ROLE_USER");
            if (userRole == null) {
                userRole = new Role();
                userRole.setName("ROLE_USER");
                roleRepository.save(userRole);
            }


            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setFirstName("Peter");
            admin.setLastName("Parker");
            admin.setAge(28);
            admin.setEmail("pparker@gmail.com");
            admin.setRoles(Set.of(adminRole));
            userRepository.save(admin);


            User user2 = new User();
            user2.setUsername("user");
            user2.setPassword(passwordEncoder.encode("user"));
            user2.setFirstName("Sarah");
            user2.setLastName("Connor");
            user2.setAge(36);
            user2.setEmail("ddayconnor@gmail.com");
            user2.setRoles(Set.of(userRole));
            userRepository.save(user2);
        }


    }

}


