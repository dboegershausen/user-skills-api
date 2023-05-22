package com.diogob.user.skills.domain.service;

import com.diogob.user.skills.domain.dto.UserDTO;
import com.diogob.user.skills.domain.dto.UserSearchResponseDTO;
import com.diogob.user.skills.domain.exception.MissingBirthDateException;
import com.diogob.user.skills.domain.exception.NotFoundException;
import com.diogob.user.skills.domain.model.User;
import com.diogob.user.skills.domain.repository.UserRepository;
import com.diogob.user.skills.domain.specification.UserSpecification;
import com.diogob.user.skills.domain.dto.SkillDTO;
import com.diogob.user.skills.domain.dto.UserFieldsFilterDTO;
import com.diogob.user.skills.domain.mapper.SkillMapper;
import com.diogob.user.skills.domain.mapper.UserMapper;
import com.diogob.user.skills.domain.model.Skill;
import com.github.f4b6a3.ulid.UlidCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final SkillMapper skillMapper;

    private final SkillService skillService;

    @Transactional
    public UserDTO create(UserDTO userDTO) {
        userDTO.setId(UlidCreator.getMonotonicUlid().toUuid());
        return save(userDTO);
    }

    @Transactional
    public void update(UserDTO userDTO) {
        findById(userDTO.getId());
        save(userDTO);
    }

    public void delete(UUID id) {
        User user = findById(id);
        userRepository.delete(user);
    }

    public UserSearchResponseDTO search(UserFieldsFilterDTO fieldsFilterDTO, Pageable pageable) {
        Page<User> users = userRepository.findAll(Specification.where(UserSpecification.build(fieldsFilterDTO)), pageable);
        return UserSearchResponseDTO.builder()
                .data(userMapper.toDtoList(users.getContent()))
                .hasNext(users.hasNext())
                .totalElements(users.getNumberOfElements())
                .totalPages(users.getTotalPages())
                .build();
    }

    public UserDTO find(UUID id) {
        return userMapper.toDto(findById(id));
    }

    private User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User", id.toString()));
    }

    private UserDTO save(UserDTO userDTO) {
        validate(userDTO);

        User user = userMapper.toEntity(userDTO);

        if (Objects.isNull(user.getId())) {
            user.setId(UlidCreator.getMonotonicUlid().toUuid());
        }

        Set<Skill> skills = userDTO.getSkills().stream().map(skill ->
            skillMapper.toEntity(skillService.getOrCreateIfNotExist(SkillDTO.builder().name(skill).build())))
                .collect(Collectors.toSet());
        user.setSkills(skills);

        return userMapper.toDto(userRepository.save(user));
    }

    private void validate(UserDTO userDTO) {
        if (userDTO.getAge() >= 18 && Objects.isNull(userDTO.getBirthDate())) {
            throw new MissingBirthDateException();
        }
    }

}