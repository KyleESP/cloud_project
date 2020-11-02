package com.grouped.cloudserver.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RestException{
    public ResourceNotFoundException() {
        super(HttpStatus.NOT_FOUND.value(), "Resource not found");
    }

    public ResourceNotFoundException(String detailedMessage) {
        super(HttpStatus.NOT_FOUND.value(), "Resource not found", detailedMessage);
    }

}
