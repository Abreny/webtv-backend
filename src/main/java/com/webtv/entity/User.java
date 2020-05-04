package com.webtv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(length = 100)
    private String fonction;

    @NotNull
    @Email
    @Size(min = 2, max = 255)
    @Column(unique = true, length = 100)
    private String email;

    @JsonIgnore
    @NotNull
    @Size(min = 2, max = 255)
    private String password;

    @NotNull
    @Size(min = 2, max = 255)
    private String telephone;

    @NotNull
    @Size(min = 2, max = 255)
    private String ville;

    @NotNull
    @Size(min = 2, max = 255)
    private String pays = "Côte d'Ivoire";

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }
    public String getFonction() {
        return fonction;
    }

    public User setFonction(String fonction) {
        this.fonction = fonction;
        return this;
    }
    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }
    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }
    public String getTelephone() {
        return telephone;
    }

    public User setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }
    public String getPays() {
        return pays;
    }

    public User setPays(String pays) {
        this.pays = pays;
        return this;
    }
    public String getVille() {
        return ville;
    }

    public User setVille(String ville) {
        this.ville = ville;
        return this;
    }
}