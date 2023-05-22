package com.diogob.user.skills.api.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class ApiError {

    private Integer status;

    private String error;

    private LocalDateTime timestamp;

}
