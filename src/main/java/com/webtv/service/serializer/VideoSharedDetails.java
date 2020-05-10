package com.webtv.service.serializer;

import com.webtv.entity.User;
import com.webtv.entity.Video;
import com.webtv.entity.VideoYoutube;

public class VideoSharedDetails {
    private Video video;
    private VideoYoutube details;
    private User author;
    
    public VideoSharedDetails() {

    }

    public VideoSharedDetails(Video v, VideoYoutube details) {
        this.video = v;
        this.details = details;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public VideoYoutube getDetails() {
        return details;
    }

    public void setDetails(VideoYoutube details) {
        this.details = details;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}