package com.webtv.commons;

import java.util.HashMap;
import java.util.Map;

public class ResponseDataBuilder<T> {
    private Map<String, T> data;

    public ResponseDataBuilder() {
        this.data = new HashMap<>();
    }

    public ResponseDataBuilder<T> put(String key, T data) {
        this.data.put(key, data);
        return this;
    }

    public Map<String, T> get() {
        return data;
    }

    public static <T> ResponseDataBuilder<T> of(String key, T data) {
        return new ResponseDataBuilder<T>().put(key, data);
    }
}