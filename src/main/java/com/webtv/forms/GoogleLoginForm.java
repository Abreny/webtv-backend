package com.webtv.forms;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.webtv.entity.User;

public class GoogleLoginForm {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String google_id;

    public String getGoogle_id() {
        return google_id;
    }

    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User user() {
        return new User().setEmail(email)
            .setName(name)
            .setGoogleId(google_id);
    }
}