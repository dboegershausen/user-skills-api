package com.diogob.user.skills.domain.mapper;

import com.diogob.user.skills.domain.dto.SkillDTO;
import com.diogob.user.skills.domain.model.Skill;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SkillMapper {

    Skill toEntity(SkillDTO skillDTO);

    SkillDTO toDto(Skill skill);

    default String toSkillName(Skill skill) {
        return skill.getName();
    }

    default void convertSkillName(Skill skill) {
        skill.setName(skill.getName().toLowerCase());
    }

}