package com.diogob.user.skills.domain.controller;

import com.diogob.user.skills.api.handler.ApiExceptionHandler;
import com.diogob.user.skills.api.v1.controller.UserController;
import com.diogob.user.skills.domain.dto.UserDTO;
import com.diogob.user.skills.domain.exception.NotFoundException;
import com.diogob.user.skills.domain.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {
    UserService.class, UserController.class, ApiExceptionHandler.class
})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService userService;

    @Test
    void shouldGetUser() throws Exception {
        UUID id = UUID.randomUUID();
        UserDTO userDTO = UserDTO.builder().id(id).build();

        when(userService.find(id)).thenReturn(userDTO);

        mockMvc.perform(get("/api/v1/users/" + id))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(userService).find(id);
    }

    @Test
    void shouldThrowUserNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        when(userService.find(id)).thenThrow(new NotFoundException("User", id.toString()));

        mockMvc.perform(get("/api/v1/users/" + id))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(userService).find(id);
    }

}