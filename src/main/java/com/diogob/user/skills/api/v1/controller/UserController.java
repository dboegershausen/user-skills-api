package com.diogob.user.skills.api.v1.controller;

import com.diogob.user.skills.domain.dto.UserDTO;
import com.diogob.user.skills.domain.dto.UserSearchResponseDTO;
import com.diogob.user.skills.domain.service.UserService;
import com.diogob.user.skills.domain.dto.UserFieldsFilterDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@Valid @RequestBody UserDTO userDTO) {
        return userService.create(userDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable(value="id") UUID id, @Valid @RequestBody UserDTO userDTO) {
        userDTO.setId(id);
        userService.update(userDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value="id") UUID id) {
        userService.delete(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UserSearchResponseDTO search(@RequestParam(value="name", required = false) String name,
                                        @RequestParam(value="skill", required = false) String skill,
                                        @RequestParam(value="page", defaultValue = "0")Integer page,
                                        @RequestParam(value="size", defaultValue = "20")Integer size) {
        UserFieldsFilterDTO params = UserFieldsFilterDTO.builder().name(name).skill(skill).build();
        return userService.search(params, PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO read(@PathVariable(value="id") UUID id) {
        return userService.find(id);
    }

}