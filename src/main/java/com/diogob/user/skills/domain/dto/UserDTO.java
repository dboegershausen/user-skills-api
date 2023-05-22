package com.diogob.user.skills.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@Builder
public class UserDTO {

    private UUID id;

    @NotNull(message = "Name must be informed")
    @Pattern(regexp="^(?!\\s*$)[a-zA-Z .]*$", message = "Name must have only letters")
    private String name;

    @Pattern(regexp = "^[a-zA-Z\\d_\\.+]+@(gmail|hotmail|outlook|yahoo)(\\.com)" , message = "Email must be a well-formed email address from gmail, hotmail, outlook or yahoo")
    private String email;

    @NotNull(message = "Age must be informed")
    @Positive(message = "Age must be a positive number")
    private Integer age;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @Pattern(regexp="^(?!\\s*$)[a-zA-Z\\d .,\\-\\/]*$", message = "Address cannot have special characters")
    private String address;

    private List<String> skills;

}
