package com.diogob.user.skills.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String entity, String id) {
        super(String.format("%s with id %s not found", new String[]{entity, id}));
    }

}
