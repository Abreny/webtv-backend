package com.webtv.repository.custom;

import java.util.ArrayList;
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
        List<VideoYoutube> details = new ArrayList<>();
        Set<Video> videos = new HashSet<>();
        result.stream().forEach(o -> {
            final Video video = (Video) o[0];
            final VideoYoutube d = (VideoYoutube) o[1];
            videoIds.add(video.getId());
            if(d != null) {
                details.add(d);
            }
            videos.add(video);
        });
        Map<Long, List<VideoYoutube>> mapDetails = details.stream()
                .collect(Collectors.groupingBy(v -> v.getVideo().getId()));
        final Map<Long, User> users = (Map<Long, User>) entityManager.createNamedQuery("getAllUsersByVideoId")
                .getResultStream().collect(Collectors.toMap((Object[] user) -> (Long) user[1], user -> (User) user[0]));
        return videos.stream().map(v -> {
            final VideoSharedDetails d = new VideoSharedDetails(v);
            d.setAuthor(users.get(v.getId()));
            d.setDetails(mapDetails.get(v.getId()));
            return d;
        }).collect(Collectors.toList());
    }

    @Override
    public List<VideoSharedDetails> findAllSharedByUser(User u) {
        Query query = entityManager.createNamedQuery("findAllVideoWithDetailsByUser");
        query.setParameter("author_id", u.getId());
        List<Object[]> result = query.getResultList();
        List<VideoYoutube> details = new ArrayList<>();
        Set<Video> videos = new HashSet<>();
        // fetch the tags attribute
        entityManager.createQuery("SELECT v FROM VideoYoutube v LEFT JOIN FETCH v.tags", VideoYoutube.class)
                .getResultList();
        result.stream().forEach(o -> {
            final VideoYoutube d = (VideoYoutube) o[1];
            if(d != null) {
                details.add(d);
            }
            videos.add((Video) o[0]);
        });
        Map<Long, List<VideoYoutube>> mapDetails = details.stream()
                .collect(Collectors.groupingBy(v -> v.getVideo().getId()));
        return videos.stream().map(v -> {
            final VideoSharedDetails d = new VideoSharedDetails(v);
            d.setDetails(mapDetails.get(v.getId()));
            return d;
        }).collect(Collectors.toList());
    }
}