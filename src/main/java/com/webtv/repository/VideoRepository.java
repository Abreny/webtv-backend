package com.webtv.repository;

import java.util.List;

import com.webtv.entity.User;
import com.webtv.entity.Video;
import com.webtv.repository.custom.SharedVideoRepository;
import com.webtv.service.serializer.VideoSharedDetails;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long>, SharedVideoRepository {
    List<Video> findAllByAuthor(User author);

    @EntityGraph("videos.authors")
    @Query("select v from Video v")
    List<Video> findAllForAdmin();

    @Query(name = "findAllVideoWithDetails")
    List<VideoSharedDetails> findAllVideoWithDetails();
}