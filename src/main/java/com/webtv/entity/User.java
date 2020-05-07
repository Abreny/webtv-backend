package com.webtv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @Size(min = 1, max = 100)
    @Column(length = 100)
    private String fonction;

    @NotNull
    @Email
    @Size(min = 2, max = 255)
    @Column(unique = true, length = 100)
    private String email;

    @Enumerated(EnumType.ORDINAL)
    private UserRole role = UserRole.USER;

    @JsonIgnore
    @NotNull
    @Size(min = 2, max = 255)
    private String password;

    @Size(min = 2, max = 255)
    private String telephone;

    @Size(min = 2, max = 255)
    private String ville;

    @Size(min = 2, max = 255)
    private String pays = "Côte d'Ivoire";

    @JsonProperty("fb_id")
    @Column(name = "fb_id", nullable = true)
    private String fbId;

    @JsonProperty("google_id")
    @Column(name = "google_id", nullable = true)
    private String googledId;

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

    public String getFbId() {
        return this.fbId;
    }

    public User setFbId(String fbId) {
        this.fbId = fbId;
        return this;
    }

    public String getGoogledId() {
        return this.googledId;
    }

    public User setGoogledId(String googledId) {
        this.googledId = googledId;
        return this;
    }


    public UserRole getRole() {
        return this.role;
    }

    public User setRole(UserRole role) {
        this.role = role;
        return this;
    }
}