package com.etiqa.response;

import com.etiqa.contant.Constants;
import com.etiqa.util.PropertiesUtil;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
@Component
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonPropertyOrder({"status", "statusCode", "messageCode", "description"})
public class ApiResponse extends BaseResponse {
    @JsonIgnore
    Map<String, Object> properties = new LinkedHashMap<>();
    String messageCode;
    Integer pageNumber;
    Integer pageSize;
    Integer totalPages;
    Long totalElements;
    Integer numberOfElements;
    List<?> results;

    public void setPagination(Page<?> page) {
        this.numberOfElements = (page != null) ? page.getNumberOfElements() : 0;
        this.pageNumber = (page != null) ? page.getNumber() + 1 : 0;
        this.pageSize = (page != null) ? page.getSize() : 0;
        this.totalPages = (page != null) ? page.getTotalPages() : 0;
        this.totalElements = (page != null) ? page.getTotalElements() : 0L;
    }

    @JsonAnySetter
    public void set(String property, Object obj) {
        this.properties.put(property, obj);
    }

    public Object get(String property) {
        return this.properties.get(property);
    }

    public static String errorMap(Map<String, Object> messageMap, String message) {
        var desc = message;
        for (var key : messageMap.keySet()) {
            desc = desc.replace("{" + key + "}", messageMap.get(key).toString());
        }
        return desc;
    }

    public static ResponseEntity<ApiResponse> forbidden() {
        var msg = PropertiesUtil.loadMessageProperties("E0004");
        var response = ApiResponse.builder().status(Constants.ERROR).statusCode(HttpServletResponse.SC_FORBIDDEN).messageCode("E0004").description(msg).build();
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    public static ResponseEntity<ApiResponse> unauthorized() {
        var msg = PropertiesUtil.loadMessageProperties("E0008");
        var response = ApiResponse.builder().status(Constants.ERROR).statusCode(HttpServletResponse.SC_UNAUTHORIZED).messageCode("E0008").description(msg).build();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<ApiResponse> noContent() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public static ResponseEntity<ApiResponse> success() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public static ResponseEntity<ApiResponse> success(String description) {
        var response = ApiResponse.builder().description(description).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static ResponseEntity<ApiResponse> success(String property, Object obj) {
        var response = ApiResponse.builder().build();
        if (obj instanceof Page<?> page) {
            response.set("numberOfElements", (page != null) ? page.getNumberOfElements() : 0);
            response.set("pageNumber", (page != null) ? page.getNumber() + 1 : 0);
            response.set("pageSize", (page != null) ? page.getSize() : 0);
            response.set("totalPages", (page != null) ? page.getTotalPages() : 0);
            response.set("totalElements", (page != null) ? page.getTotalElements() : 0L);
            response.set(property, page.getContent());
        } else {
            response.set(property, obj);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static ResponseEntity<ApiResponse> success(String property, String description, Object obj) {
        var response = ApiResponse.builder().description(description).build();
        if (obj instanceof Page<?> page) {
            response.set("numberOfElements", (page != null) ? page.getNumberOfElements() : 0);
            response.set("pageNumber", (page != null) ? page.getNumber() + 1 : 0);
            response.set("pageSize", (page != null) ? page.getSize() : 0);
            response.set("totalPages", (page != null) ? page.getTotalPages() : 0);
            response.set("totalElements", (page != null) ? page.getTotalElements() : 0L);
            response.set(property, page.getContent());
        } else {
            response.set(property, obj);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static ResponseEntity<ApiResponse> notFound(Object property) {
        var errorMessage = PropertiesUtil.loadMessageProperties("E0009");
        Map<String, Object> variableMap = Map.of("obj", property.toString());
        var description = errorMap(variableMap, errorMessage);
        var response = ApiResponse.builder().status(Constants.ERROR).statusCode(HttpServletResponse.SC_NOT_FOUND).messageCode("E0009").description(description).build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<ApiResponse> notFound(Object property, String messageCode) {
        var errorMessage = PropertiesUtil.loadMessageProperties(messageCode);
        Map<String, Object> variableMap = Map.of("obj", property.toString());
        var description = errorMap(variableMap, errorMessage);
        var response = ApiResponse.builder().status(Constants.ERROR).statusCode(HttpServletResponse.SC_NOT_FOUND).messageCode(messageCode).description(description).build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<ApiResponse> error(String messageCode) {
        var errorMessage = PropertiesUtil.loadMessageProperties(messageCode);
        var response = ApiResponse.builder().status(Constants.ERROR).statusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).messageCode(messageCode).description(errorMessage).build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<ApiResponse> error(Map<String, Object> variableMap, String messageCode) {
        var errorMessage = PropertiesUtil.loadMessageProperties(messageCode);
        var description = errorMap(variableMap, errorMessage);
        var response = ApiResponse.builder().status(Constants.ERROR).statusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).messageCode(messageCode).description(description).build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<ApiResponse> error(String property, String messageCode) {
        var errorMessage = PropertiesUtil.loadMessageProperties(messageCode);
        Map<String, Object> variableMap = Map.of("obj", property);
        var description = errorMap(variableMap, errorMessage);
        var response = ApiResponse.builder().status(Constants.ERROR).statusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).messageCode(messageCode).description(description).build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<ApiResponse> exception(Object obj) {
        var errorMessage = PropertiesUtil.loadMessageProperties("E0001");
        Map<String, Object> variableMap = Map.of("obj", obj);
        var description = errorMap(variableMap, errorMessage);
        var response = ApiResponse.builder().status(Constants.ERROR).statusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).messageCode("E0001").description(description).build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Builder()
    public ApiResponse(String status, Integer statusCode, String messageCode, String description, List<?> results, Page<?> page) {
        this.status = Optional.ofNullable(status).map(s -> s).orElse(Constants.SUCCESS);
        this.statusCode = Optional.ofNullable(statusCode).map(sCode -> sCode).orElse(HttpServletResponse.SC_OK);
        this.messageCode = Optional.ofNullable(messageCode).map(code -> code).orElse("S0001");
        this.description = Optional.ofNullable(description).map(desc -> desc).orElse("");
        this.results = results;
        if (page != null) {
            this.results = page.getContent();
            this.setPagination(page);
        }
    }

}
