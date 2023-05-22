package com.diogob.user.skills.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class SkillDTO {

    private UUID id;

    private String name;

}
