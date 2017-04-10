package com.beerjournal.datamodel.entity;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;

public class UserEntity {

    private Optional<String> id = Optional.empty();
    private String username;
    private String password;
    private Collection<String> roles;

    /**
     * @param id       - object identifier on data base
     * @param username - name of user
     * @param password - user's password
     * @param roles    - collection of role names
     */
    public UserEntity(String id, String username, String password, Collection<String> roles) {
        this.id = Optional.of(id);
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    /**
     * @param username - name of user
     * @param password - user's password
     * @param roles    - collection of role names
     */
    public UserEntity(String username, String password, Collection<String> roles) {
        this.id = Optional.empty();
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }

    public void addRole(String role) {
        if (Objects.isNull(roles)) {
            roles = new LinkedList<>();
        }
        roles.add(role);
    }

    public void removeRole(String role) {
        if (Objects.nonNull(roles)) {
            roles.remove(role);
        }
    }

    /**
     * @return data base identifier if object is already persisted
     */
    public Optional<String> getId() {
        return id;
    }

    /**
     * DO NOT POPULATE - id is automatically set during persisting process
     *
     * @param id - object identifier on data base
     */
    public void setId(String id) {
        this.id = Optional.of(id);
    }

    @Override
    public String toString() {
        String roles = String.join(", ", this.roles);

        return "UserEntity {" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
