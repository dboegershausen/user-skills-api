package com.diogob.user.skills.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "SKILL")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Skill {

    @Id
    private UUID id;

    @Column(name = "NAME")
    private String name;

}
