package com.webtv.service.endpoints;

import com.google.api.client.auth.oauth2.Credential;
import com.webtv.commons.ResponseModel;
import com.webtv.entity.Video;
import com.webtv.entity.VideoStatus;
import com.webtv.entity.VideoYoutube;
import com.webtv.exception.GoogleAuthException;
import com.webtv.repository.VideoRepository;
import com.webtv.repository.VideoYoutubeRepository;
import com.webtv.service.actions.EntityNotFound;
import com.webtv.service.youtube.GoogleAuth;
import com.webtv.service.youtube.YoutubeUploader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class ShareService extends AbstractEntityService<VideoYoutube, Long> {

    // private final VideoYoutubeRepository youtubes;

    private final EntityNotFound notFound;

    private final VideoRepository videos;

    private final GoogleAuth googleAuth;

    private Credential googleCredential;

    @Autowired
    public ShareService(VideoYoutubeRepository repository, VideoRepository videos, EntityNotFound notFound,
            GoogleAuth googleAuth) {
        super(repository);
        // youtubes = repository;
        this.notFound = notFound;
        this.videos = videos;
        this.googleAuth = googleAuth;
    }

    public ResponseModel<VideoYoutube> share(VideoYoutube youtubeVideo, BindingResult bindingResult, Long videoId) {
        final Video video = this.notFound.checkId(videos, "uploaded_video_id", videoId);
        youtubeVideo.setVideo(video);
        final VideoYoutube createdVideo = this.create(youtubeVideo, bindingResult).getData();
        video.setStatus(VideoStatus.WAITING);
        videos.save(video);
        final YoutubeUploader youtubeUploader = new YoutubeUploader(googleCredential, createdVideo, videos);
        new Thread(youtubeUploader).start();
        return ResponseModel.success(createdVideo);
    }

    @Override
    protected void beforeSave(VideoYoutube entity) {
        final Credential credential = googleAuth.authorize("fanabned@gmail.com");
        if(credential == null) throw new GoogleAuthException(googleAuth.authorizeUrl().build());
        googleCredential = credential;
    }
}