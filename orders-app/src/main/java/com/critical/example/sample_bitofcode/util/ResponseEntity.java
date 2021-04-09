package com.critical.example.sample_bitofcode.util;

import javax.json.bind.annotation.JsonbProperty;
import java.io.Serializable;

public class ResponseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonbProperty
    private String message;

    @JsonbProperty
    private Object entity;

    public ResponseEntity(String message) {
        this.message = message;
    }

    public ResponseEntity(String message, Object entity) {
        this.message = message;
        this.entity = entity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }
}
