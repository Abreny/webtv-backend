package com.webtv.controllers;

import java.io.IOException;

import javax.validation.Valid;

import com.webtv.forms.PitchForm;
import com.webtv.services.FileUploader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/files")
public class FileUpload {
    public static final String UPLOADS_DIR = "uploads";

    @Autowired
    private FileUploader uploader;

    @PostMapping("upload")
    Object uploadStream(@Valid PitchForm form, BindingResult bindingResult) {
        try {
            if (this.uploader.uploadAll(form.getPitchs()) != null) {
                return "success!";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error!";
    }
}