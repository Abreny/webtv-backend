package com.webtv.service.endpoints;

import java.util.List;

import com.webtv.commons.FileHelper;
import com.webtv.commons.ResponseModel;
import com.webtv.entity.Video;
import com.webtv.repository.VideoRepository;
import com.webtv.service.actions.EntityNotFound;
import com.webtv.service.security.SecurityHelper;
import com.webtv.service.serializer.AdminVideoList;
import com.webtv.service.serializer.VideoSharedDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class VideoService extends AbstractEntityService<Video, Long> {
    
    private EntityNotFound notFound;

    private VideoRepository videos;

    @Autowired
    public VideoService(VideoRepository videoRepository, EntityNotFound entityNotFound) {
        super(videoRepository);
        this.videos = videoRepository;
        this.notFound = entityNotFound;
    }

    @Override
    protected  void validate(BindingResult bResult, boolean update) {
        
    }

    @Override
    protected Video findById(Long object) {
        return this.notFound.checkId(videos, "video_id", object);
    }

    @Override
    public ResponseModel<Video> delete(Long id) {
        final ResponseModel<Video> respo = super.delete(id);
        FileHelper.removeFile(respo.getData().getFilename());
        return respo;
    }

    public ResponseModel<List<Video>> mesVideos() {
        return ResponseModel.success(videos.findAllByAuthor(SecurityHelper.user()));
    }
    public ResponseModel<List<VideoSharedDetails>> mesVideos(boolean isAdmin) {
        if(isAdmin) {
            return this.sharedVideos();
        }
        return sharedVideosByUser();
    }

    public ResponseModel<AdminVideoList> allWithAuthor() {
        return ResponseModel.success(new AdminVideoList(videos.findAllForAdmin()));
    }

	public ResponseModel<List<VideoSharedDetails>> sharedVideos() {
		return ResponseModel.success(videos.findAllShared());
	}
	public ResponseModel<List<VideoSharedDetails>> sharedVideosByUser() {
		return ResponseModel.success(videos.findAllSharedByUser(SecurityHelper.user()));
	}
}