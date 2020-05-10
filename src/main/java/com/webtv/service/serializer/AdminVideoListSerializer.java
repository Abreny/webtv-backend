package com.webtv.service.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.webtv.entity.Video;

import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class AdminVideoListSerializer extends JsonSerializer<AdminVideoList> {

    @Override
    public void serialize(AdminVideoList value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        for (Video video: value.getVideos()) {
            gen.writeStartObject();
            writeVideo(gen, video);
            if(value.isAuthorSerialized()) {
                gen.writeObjectField("author", video.getAuthor());
            }
            gen.writeEndObject();
        }
        gen.writeEndArray();
    }
    public void writeVideo(JsonGenerator gen, Video value) throws IOException {
        gen.writeNumberField("id", value.getId());
        gen.writeStringField("content_type", value.getContentType());
        gen.writeStringField("url", value.getUrl());
        gen.writeStringField("status", value.getStatus().name());
    }
}