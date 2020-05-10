package com.webtv.repository.custom;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.webtv.entity.User;
import com.webtv.entity.Video;
import com.webtv.entity.VideoYoutube;
import com.webtv.service.serializer.VideoSharedDetails;

@SuppressWarnings("unchecked")
public class SharedVideoRepositoryImpl implements SharedVideoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<VideoSharedDetails> findAllShared() {
        Query query = entityManager.createNamedQuery("findAllVideoWithDetails");
        List<Object[]> result = query.getResultList();
        // fetch the tags attribute
        entityManager.createQuery("SELECT v FROM VideoYoutube v LEFT JOIN FETCH v.tags", VideoYoutube.class)
                .getResultList();
        Set<Long> videoIds = new HashSet<>();
        List<VideoSharedDetails> videoDetails = result.stream().map(o -> {
            final Video video = (Video) o[0];
            videoIds.add(video.getId());
            return new VideoSharedDetails(video, (VideoYoutube) o[1]);
        }).collect(Collectors.toList());
        final Map<Long, User> users = (Map<Long, User>) entityManager.createNamedQuery("getAllUsersByVideoId")
                .getResultStream().collect(Collectors.toMap((Object[] user) -> (Long) user[1], user -> (User) user[0]));
        videoDetails = videoDetails.stream().map(v -> {
            v.setAuthor(users.get(v.getVideo().getId()));
            return v;
        }).collect(Collectors.toList());
        return videoDetails;
    }

    @Override
    public List<VideoSharedDetails> findAllSharedByUser(User u) {
        Query query = entityManager.createNamedQuery("findAllVideoWithDetailsByUser");
        query.setParameter("author_id", u.getId());
        List<Object[]> result = query.getResultList();

        // fetch the tags attribute
        entityManager.createQuery("SELECT v FROM VideoYoutube v LEFT JOIN FETCH v.tags", VideoYoutube.class)
                .getResultList();
        return result.stream().map(o -> new VideoSharedDetails((Video) o[0], (VideoYoutube) o[1]))
                .collect(Collectors.toList());
    }
}