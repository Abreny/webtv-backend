package com.webtv.repository.custom;

public interface DeleteVideoRepository {
    void deleteAllByVideoId(Long videoId);
}