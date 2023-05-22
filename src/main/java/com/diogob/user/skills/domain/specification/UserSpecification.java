package com.diogob.user.skills.domain.specification;

import com.diogob.user.skills.domain.model.User;
import com.diogob.user.skills.domain.dto.UserFieldsFilterDTO;
import com.diogob.user.skills.domain.model.Skill;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class UserSpecification {

    public static Specification<User> build(UserFieldsFilterDTO fields) {
        if (Objects.isNull(fields)) {
            return null;
        }

        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!StringUtils.isEmpty(fields.getName())) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + fields.getName() + "%"));
            }

            if (!StringUtils.isEmpty(fields.getSkill())) {
                Join<User, Skill> skills = root.join("skills");
                predicates.add(criteriaBuilder.like(skills.get("name"), "%" + fields.getSkill().toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        };
    }

}