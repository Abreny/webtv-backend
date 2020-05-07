package com.webtv.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.webtv.commons.ResponseDataBuilder;
import com.webtv.commons.ResponseModel;
import com.webtv.commons.Validator;
import com.webtv.entity.Video;
import com.webtv.exception.BadRequest;
import com.webtv.exception.ServerError;
import com.webtv.forms.UploadVideoForm;
import com.webtv.service.FileUploader;
import com.webtv.service.FileUploaderInterface;
import com.webtv.service.Translator;
import com.webtv.service.endpoints.VideoService;
import com.webtv.service.security.SecurityHelper;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "API Enpoints for uploading a video.")
@RestController
@RequestMapping("api/v1/videos")
public class UploadVideoController {
    public static final String UPLOADS_DIR = "uploads";

    @Autowired
    private FileUploader uploader;

    @Autowired
    private Translator tranlator;

    @Autowired
    private VideoService videoService;

    @Value("${app.host}")
    private String host;

    @ApiOperation("UploadVideo. upload a new video.")
    @PostMapping("upload")
    ResponseModel<Video> uploadStream(@Valid UploadVideoForm form, BindingResult bindingResult) {
        Validator.checkCreate(bindingResult);
        try {
            File uploadedFile = this.uploader.upload(form.getVideo());
            if (uploadedFile != null) {
                Video video = new Video();
                video.setAuthor(SecurityHelper.user());
                video.setFilename(uploadedFile.getAbsolutePath());
                video.setContentType(Files.probeContentType(uploadedFile.toPath()));
                video.setUrl(String.format("%s/api/v1/videos/show/%s", host, uploadedFile.getName()));
                return this.videoService.create(video, null);
            }
            throw new BadRequest(ResponseDataBuilder.of("video", this.tranlator.get("error.upload.video")).get());
        } catch (IOException e) {
            throw new ServerError(e);
        }
    }

    @ApiOperation("DeleteVideo. Delete a video by a given id. This endpoint does not yet control the author of the video.")
    @DeleteMapping("{videoId:[0-9]+}")
    ResponseModel<Video> remove(@PathVariable(name = "videoId") Long videoId) {
        return videoService.delete(videoId);
    }

    @ApiOperation("ShowVideo. Show a video by a ginven name.")
    @GetMapping("show/{filename:.*}")
    public void getVideoAsByteArray(HttpServletResponse response, @PathVariable("filename") String filename) throws IOException {
        final File file = new File(new StringBuilder().append(FileUploaderInterface.UPLOADS_DIR).append("/").append(filename).toString());
        response.setContentType(Files.probeContentType(file.toPath()));
        IOUtils.copy(new FileInputStream(file), response.getOutputStream());
    }

    @ApiOperation("MesVideo. Get all the user's uploaded video.")
    @GetMapping("mes")
    ResponseModel<List<Video>> mes() {
        return videoService.mesVideos();
    }
}