package com.webtv.service.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class VideoDetailsSerializer extends JsonSerializer<VideoSharedDetails> {

    @Override
    public void serialize(VideoSharedDetails value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        VideoJsonWritter jsonWriter = new VideoJsonWritter();
        gen.writeStartObject();
        jsonWriter.writeVideo(gen, value.getVideo());
        if (value.getDetails() != null) {
            jsonWriter.writeFieldVideo(gen, value.getDetails());
        }
        if (null != value.getAuthor()) {
            gen.writeObjectField("author", value.getAuthor());
        }
        gen.writeEndObject();
    }

}