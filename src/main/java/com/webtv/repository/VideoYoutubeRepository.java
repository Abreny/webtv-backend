package com.webtv.repository;

import javax.transaction.Transactional;

import com.webtv.entity.VideoYoutube;
import com.webtv.repository.custom.DeleteVideoRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoYoutubeRepository extends JpaRepository<VideoYoutube, Long>, DeleteVideoRepository {
    @Transactional
    @Modifying
    @Query(value="", nativeQuery = true)
    void deleteAllTagsByVideoId(@Param("video_id") Long videoId);
}