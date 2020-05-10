package com.webtv.service.serializer;

import java.util.ArrayList;
import java.util.List;

import com.webtv.entity.Video;

@SuppressWarnings("serial")
public class AdminVideoList extends ArrayList<Video> {
    private final List<Video> videos;
    private final boolean authorSerialized;

    public AdminVideoList(List<Video> videos) {
        this.videos = videos;
        this.authorSerialized = true;
    }
    public AdminVideoList(List<Video> videos, boolean isAdmin) {
        this.videos = videos;
        this.authorSerialized = isAdmin;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public boolean isAuthorSerialized() {
        return authorSerialized;
    }
}