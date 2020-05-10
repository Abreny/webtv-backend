package com.webtv.service.serializer;

import java.util.List;

import com.webtv.entity.User;
import com.webtv.entity.Video;
import com.webtv.entity.VideoYoutube;

public class VideoSharedDetails {
    private Video video;
    private List<VideoYoutube> details;
    private User author;
    
    public VideoSharedDetails() {

    }

    public VideoSharedDetails(Video v) {
        this.video = v;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public List<VideoYoutube> getDetails() {
        return details;
    }

    public void setDetails(List<VideoYoutube> details) {
        this.details = details;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}