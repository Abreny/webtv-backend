package com.webtv.forms;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.webtv.entity.User;

public class FacebookLoginForm {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String fb_id;

    public String getFb_id() {
        return fb_id;
    }

    public void setFb_id(String fb_id) {
        this.fb_id = fb_id;
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
            .setFbId(fb_id);
    }
}