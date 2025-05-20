package ru.kata.spring.boot_security.demo.dto;

import java.util.Set;

public class UserDTO {


    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private Set<String> roles;

    public UserDTO() {
    }


    public UserDTO(Long id, String username, String password, String firstName,
                   String lastName, Integer age, String email, Set<String> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.roles = roles;
    }

        public Long getId () {
            return id;
        }

        public String getUsername () {
            return username;
        }

        public String getPassword () {
            return password;
        }

        public String getFirstName () {
            return firstName;
        }

        public String getLastName () {
            return lastName;
        }

        public Integer getAge () {
            return age;
        }

        public String getEmail () {
            return email;
        }

        public Set<String> getRoles () {
            return roles;
        }

        public void setId (Long id){
            this.id = id;
        }

        public void setUsername (String username){
            this.username = username;
        }

        public void setPassword (String password){
            this.password = password;
        }

        public void setFirstName (String firstName){
            this.firstName = firstName;
        }

        public void setLastName (String lastName){
            this.lastName = lastName;
        }

        public void setAge (Integer age){
            this.age = age;
        }

        public void setEmail (String email){
            this.email = email;
        }

        public void setRoles (Set <String> roles) {
            this.roles = roles;
        }

    }


