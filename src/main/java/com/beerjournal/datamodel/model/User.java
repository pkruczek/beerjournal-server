package com.beerjournal.datamodel.model;

import org.springframework.data.annotation.Id;

import java.util.Collection;

public class User {

    @Id
    public String id;

    public String username;
    public String password;

    public Collection<String> roles;

    public User() {
    }

    public User(String username, String password, Collection<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User {id=" + id +
                ", username=" + username +
                ", password=" + password +
                ", roles=[" + String.join(", ", roles) + "]}";
    }
}
