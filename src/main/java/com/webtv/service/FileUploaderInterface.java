package com.webtv.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploaderInterface {
    public static final String UPLOADS_DIR = "uploads";

    default File upload(MultipartFile file) throws IOException {
        return this.uploadInto(file, UPLOADS_DIR);
    }

    default File uploadInto(MultipartFile file, String directory) throws IOException {
        if(file == null) {
            return null;
        }
        return this.uploadAllInto(Arrays.asList(file), directory).get(0);
    }

    default List<File> uploadAll(List<MultipartFile> files) throws IOException {
        if(files == null) {
            return null;
        }
        if(files.size() == 0) {
            return new ArrayList<>();
        }
        return this.uploadAllInto(files, UPLOADS_DIR);
    }
    
    List<File> uploadAllInto(List<MultipartFile> files, String directory) throws IOException;
    String getFilename(InputStream stream, String filename);
}