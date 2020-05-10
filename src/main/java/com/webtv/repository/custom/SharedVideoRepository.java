package com.webtv.repository.custom;

import java.util.List;

import com.webtv.entity.User;
import com.webtv.service.serializer.VideoSharedDetails;

public interface SharedVideoRepository {
    List<VideoSharedDetails> findAllShared();
    List<VideoSharedDetails> findAllSharedByUser(User u);
}