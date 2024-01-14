package com.etiqa.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class BaseResponse {
    String status;
    Integer statusCode;
    String description;
    private Map<String, Object> properties = new HashMap<>();

    public BaseResponse(String description) {
        super();
        this.description = description;
    }

    public BaseResponse(Integer statusCode, String description) {
        super();
        this.statusCode = statusCode;
        this.description = description;
    }

    public BaseResponse(Map<String, Object> properties) {
        this.properties = properties;
    }

    @JsonAnyGetter
    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public BaseResponse put(String key, Object value) {
        properties.put(key, value);
        return this;
    }

}
