package com.webtv.repository.custom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

public class DeleteVideoRepositoryImpl implements DeleteVideoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void deleteAllByVideoId(Long videoId) {
       Query q = entityManager.createNativeQuery("delete you from video_youtube_tags you INNER JOIN video_youtube vy on vy.id = you.video_youtube_id where vy.id = :video_id");
        q.setParameter("video_id", videoId);
        q.executeUpdate();
        entityManager.flush();
        
        q = entityManager.createNativeQuery("delete from video_youtube where video_id = :video_id");
        q.setParameter("video_id", videoId);
        q.executeUpdate();
        entityManager.flush();
    }

}