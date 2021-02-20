package com.example.demo.domain.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class ErrorResponse implements Serializable {

    public static final String DEFAULT_MESSAGE = "Operation failed.";

    @JsonProperty("error_code")
    private final String errorCode;
    @JsonProperty
    private final String message;
    @JsonProperty
    private final String description;


    @JsonCreator
    public ErrorResponse(
            @JsonProperty("error_code") String errorCode,
            @JsonProperty String message,
            @JsonProperty String description) {
        this.errorCode = errorCode;
        this.message = message;
        this.description = description;
    }
}
