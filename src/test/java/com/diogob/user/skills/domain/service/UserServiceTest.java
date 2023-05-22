package com.diogob.user.skills.domain.service;

import com.diogob.user.skills.domain.dto.UserDTO;
import com.diogob.user.skills.domain.dto.UserFieldsFilterDTO;
import com.diogob.user.skills.domain.dto.UserSearchResponseDTO;
import com.diogob.user.skills.domain.mapper.UserMapper;
import com.diogob.user.skills.domain.model.Skill;
import com.diogob.user.skills.domain.model.User;
import com.diogob.user.skills.domain.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Test
    void shouldGetListAccountAliases(){
        User firstUser = User.builder()
                .id(UUID.randomUUID())
                .age(18)
                .birthDate(LocalDate.of(2000, 10, 10))
                .name("John Doe")
                .address("Fake Street 144")
                .skills(Set.of(Skill.builder().id(UUID.randomUUID()).name("python").build())).build();
        User secondUser = User.builder()
                .id(UUID.randomUUID())
                .age(20)
                .birthDate(LocalDate.of(1998, 5, 6))
                .name("Jake Smith")
                .address("New Street 85")
                .skills(Set.of(Skill.builder().id(UUID.randomUUID()).name("javascript").build())).build();
        Page<User> usersPage = new PageImpl(List.of(firstUser, secondUser));

        when(userRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(usersPage);
        when(userMapper.toDtoList(anyList())).thenReturn(List.of(UserDTO.builder().id(UUID.randomUUID()).build(),
                UserDTO.builder().id(UUID.randomUUID()).build()));

        UserSearchResponseDTO response = userService.search(UserFieldsFilterDTO.builder().build(), PageRequest.of(0, 10));

        Assertions.assertEquals(2, response.getData().size());
        verify(userRepository, atLeastOnce()).findAll(any(Specification.class), any(Pageable.class));

    }

}
