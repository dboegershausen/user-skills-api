package com.diogob.user.skills.domain.service;

import com.diogob.user.skills.domain.dto.SkillDTO;
import com.diogob.user.skills.domain.model.Skill;
import com.diogob.user.skills.domain.mapper.SkillMapper;
import com.diogob.user.skills.domain.repository.SkillRepository;
import com.github.f4b6a3.ulid.UlidCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    private final SkillMapper skillMapper;

    public SkillDTO create(SkillDTO skillDTO) {
        Skill skill = skillMapper.toEntity(skillDTO);
        skill.setId(UlidCreator.getMonotonicUlid().toUuid());
        return skillMapper.toDto(skillRepository.save(skill));
    }

    public SkillDTO getOrCreateIfNotExist(SkillDTO skillDTO) {
        Optional<Skill> optionalSkill = skillRepository.findByName(skillDTO.getName());
        if (optionalSkill.isPresent()) {
            return skillMapper.toDto(optionalSkill.get());
        }
        return create(skillDTO);
    }

}