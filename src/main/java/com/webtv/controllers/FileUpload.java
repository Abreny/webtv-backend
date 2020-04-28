package com.webtv.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.webtv.services.FileUploader;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/files")
public class FileUpload {

    @Autowired
    private FileUploader uploader;

    @PostMapping("upload")
    Object uploadFile(HttpServletRequest request) {
        try {
            return uploader.upload(request, false);
        } catch (FileUploadException | IOException e) {
            e.printStackTrace();
        }
        return "error!";
    }

    @PostMapping("upload-stream")
    Object uploadStream(HttpServletRequest request) {
        try {
            return uploader.upload(request, true);
        } catch (FileUploadException | IOException e) {
            e.printStackTrace();
        }
        return "error!";
    }
}