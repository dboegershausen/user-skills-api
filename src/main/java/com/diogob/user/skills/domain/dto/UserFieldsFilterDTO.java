package com.diogob.user.skills.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserFieldsFilterDTO {

    private String name;

    private String skill;

}