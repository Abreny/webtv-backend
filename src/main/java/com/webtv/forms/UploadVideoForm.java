package com.webtv.forms;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public class UploadVideoForm {

    @NotNull
    private MultipartFile video;

    public MultipartFile getVideo() {
        return video;
    }

    public void setVideo(MultipartFile video) {
        this.video = video;
    }
}