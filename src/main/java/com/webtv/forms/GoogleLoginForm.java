package com.webtv.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class GoogleLoginForm {

    @NotNull
    @NotBlank
    private String google_id;

    public String getGoogle_id() {
        return google_id;
    }

    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
    }
}