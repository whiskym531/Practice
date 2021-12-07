package com.example.carrot.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.guava.GuavaModule;

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Author: warm
 * Date: 2021/7/9
 * Description:
 */
public abstract class Criteria {
    protected static final ObjectMapper MAPPER = new ObjectMapper();

    public Criteria() {
    }

    public Map<String, Object> toMap() {
        return (Map)MAPPER.convertValue(this, Map.class);
    }

    static {
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//        MAPPER.registerModule(new GuavaModule());
        MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }
}