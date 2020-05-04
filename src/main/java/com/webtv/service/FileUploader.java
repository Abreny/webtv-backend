package com.webtv.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.Files;
import com.webtv.commons.FileHelper;
import com.webtv.commons.StringUtils;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploader implements FileUploaderInterface {

    @Override
    public String getFilename(InputStream stream, String filename) {
        String ext = FileHelper.detectFileExtension(stream).orElse(".mp4");
        int index = filename.lastIndexOf(ext);
        String randomStr = StringUtils.randomString(10);
        if (!StringUtils.isBlank(filename)) {
            filename = StringUtils.toSlug(filename.substring(0, index < 0 ? filename.length() : index));
        } else {
            filename = StringUtils.randomString(2);
        }
        return String.format("%s-%s%s", randomStr, filename, ext);
    }

    public String getFilename(MultipartFile file, String directory) throws IOException {
        String filename = file.getOriginalFilename();
        if(filename == null) {
            return this.getFilename(file.getInputStream(), filename);
        }
        String ext = Files.getFileExtension(filename);
        int index = filename.lastIndexOf(ext);
        filename = StringUtils.toSlug(filename.substring(0, index < 0 ? filename.length() : index));
        return String.format("%s-%s.%s", StringUtils.randomString(10), filename, ext);
    }

    @Override
    public List<File> uploadAllInto(List<MultipartFile> files, String directory) throws IOException {
        Assert.notNull(files, "Files to upload must not be null");
        List<File> uploadedFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            File f = new File(String.format("%s%s%s", directory, File.separator, getFilename(file, directory)));
            file.transferTo(f);
            uploadedFiles.add(f);
        }
        return uploadedFiles;
    }

}