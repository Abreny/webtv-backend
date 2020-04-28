package com.webtv.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

@Component
public class FileUploader {
    public String upload(HttpServletRequest request, boolean isStrem) throws FileUploadException, IOException {
        if(isStrem) {
            return uploadWithStream(request);
        }
        return uploadWithTemp(request);
    }

    private String uploadWithStream(HttpServletRequest request) throws FileUploadException, IOException {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(isMultipart) {
            ServletFileUpload upload = new ServletFileUpload();
            FileItemIterator iterStream = upload.getItemIterator(request);
            while (iterStream.hasNext()) {
                FileItemStream item = iterStream.next();
                String name = item.getFieldName();
                InputStream stream = item.openStream();
                if (!item.isFormField()) {
                    System.out.println("File field " + name + " with file name " + item.getName() + " detected.");
                    OutputStream out = new FileOutputStream("file.mp4");
                    IOUtils.copy(stream, out);
                    stream.close();
                } else {
                    //process form fields
                    // String formFieldValue = Streams.asString(stream);
                    System.out.println("Form field " + name + " with value " + Streams.asString(stream) + " detected.");
                }
            }
            return "success!";
        }
        return "form upload must be multipart/form-data!";
    }
    private String uploadWithTemp(HttpServletRequest request) throws FileUploadException, IOException {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(isMultipart) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
            factory.setSizeThreshold(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD);
            factory.setFileCleaningTracker(null);

            ServletFileUpload upload = new ServletFileUpload(factory);

            List<FileItem> items = upload.parseRequest(request);

            Iterator<FileItem> iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = iter.next();
                InputStream uploadedStream = item.getInputStream();
                if (!item.isFormField()) {
                    System.out.println("File field " + item.getFieldName() + " with file name " + item.getName() + " detected.");
                    OutputStream out = new FileOutputStream("file.mp4");
                    IOUtils.copy(uploadedStream, out);
                    out.close();
                } else {
                    System.out.println("Form field " + item.getFieldName()+ " with value " + Streams.asString(uploadedStream)+ " detected.");
                }
            }
            return "success!";
        }
        return "form upload must be multipart/form-data!";
    }

}