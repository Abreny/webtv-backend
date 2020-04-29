package com.webtv.forms;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class PitchForm {
    private String name;

    private List<MultipartFile> pitchs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MultipartFile> getPitchs() {
        return pitchs;
    }

    public void setPitchs(List<MultipartFile> pitchs) {
        this.pitchs = pitchs;
    }
}