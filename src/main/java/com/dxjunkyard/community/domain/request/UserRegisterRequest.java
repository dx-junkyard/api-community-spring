package com.dxjunkyard.community.domain.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UserRegisterRequest {
    @NotBlank(message = "name cannot be empty")
    private String name;

    @NotBlank(message = "email cannot be empty")
    private String email;

    @NotBlank(message = "password cannot be empty")
    private String password;
}
