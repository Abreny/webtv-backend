package com.webtv.commons;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileHelper {
    static final Logger log = LoggerFactory.getLogger(FileHelper.class);
    
    public static Optional<String> detectFileExtension(InputStream inputStream) {

        InputStream bufferedInputStream = new BufferedInputStream(inputStream);
    
        String extension = null;
        try {
            MimeTypes mimeRepository = getMimeRepository();
    
            MediaType mediaType = mimeRepository.detect(bufferedInputStream, new Metadata());
            MimeType mimeType = mimeRepository.forName(mediaType.toString());
            extension = mimeType.getExtension();
            log.info("File Extension detected: {}", extension);
            inputStream.reset();
            bufferedInputStream.close();
    
        } catch (MimeTypeException | IOException ignored) {
            log.error("Unable to detect extension of the file from the provided stream");
        }
        return Optional.ofNullable(extension);
    }
    
    private static MimeTypes getMimeRepository() {
        TikaConfig config = TikaConfig.getDefaultConfig();
        return config.getMimeRepository();
    }

    public static String getFileExtension(String filename) {
        String extension = "";
        if(filename == null) {
            return extension;
        }
        int i = filename.lastIndexOf('.');
        int p = Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'));

        if (i > p) {
            extension = filename.substring(i + 1);
        }
        return extension;
    }
}