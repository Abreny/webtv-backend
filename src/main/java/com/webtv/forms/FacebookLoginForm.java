package com.webtv.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FacebookLoginForm {

    @NotNull
    @NotBlank
    private String fb_id;

    public String getFb_id() {
        return fb_id;
    }

    public void setFb_id(String fb_id) {
        this.fb_id = fb_id;
    }
}