package com.diogob.user.skills.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class MissingBirthDateException extends RuntimeException {

    public MissingBirthDateException() {
        super("Birth date is required for users over 18 years old");
    }

}
