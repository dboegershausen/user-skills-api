package com.diogob.user.skills.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class UserSearchResponseDTO {

    private List<UserDTO> data;

    private boolean hasNext;

    private int totalPages;

    private int totalElements;

}
