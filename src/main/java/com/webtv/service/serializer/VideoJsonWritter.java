package com.webtv.service.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.webtv.entity.Video;
import com.webtv.entity.VideoYoutube;

public class VideoJsonWritter {
    public void writeVideo(JsonGenerator gen, Video value) throws IOException {
        gen.writeNumberField("id", value.getId());
        gen.writeStringField("content_type", value.getContentType());
        gen.writeStringField("url", value.getUrl());
        gen.writeStringField("status", value.getStatus().name());
    }
    public void writeVideo(JsonGenerator gen, VideoYoutube value) throws IOException {
        gen.writeNumberField("id", value.getId());
        gen.writeStringField("description", value.getDescription());
        gen.writeStringField("title", value.getTitle());
        gen.writeObjectField("tags", value.getTags());
    }
    public void writeFieldVideo(JsonGenerator gen, VideoYoutube value) throws IOException {
        gen.writeObjectFieldStart("video_youtube");
        writeVideo(gen, value);
        gen.writeEndObject();
    }
}