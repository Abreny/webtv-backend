package com.webtv.repository;

import com.webtv.entity.VideoYoutube;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoYoutubeRepository extends JpaRepository<VideoYoutube, Long> {
    
}