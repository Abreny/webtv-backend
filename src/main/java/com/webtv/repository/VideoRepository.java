package com.webtv.repository;

import java.util.List;

import com.webtv.entity.User;
import com.webtv.entity.Video;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findAllByAuthor(User author);
}