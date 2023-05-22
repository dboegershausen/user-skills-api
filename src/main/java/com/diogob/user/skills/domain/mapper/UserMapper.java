package com.diogob.user.skills.domain.mapper;

import com.diogob.user.skills.domain.dto.UserDTO;
import com.diogob.user.skills.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = SkillMapper.class)
public interface UserMapper {

    @Mapping(target = "skills", ignore = true)
    User toEntity(UserDTO userDTO);

    UserDTO toDto(User user);

    List<UserDTO> toDtoList(List<User> users);

}