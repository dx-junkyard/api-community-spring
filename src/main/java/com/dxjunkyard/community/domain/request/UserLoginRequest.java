package com.dxjunkyard.community.domain.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UserLoginRequest {
    @NotBlank(message = "Title cannot be empty")
    private String userId;

    @NotBlank(message = "Title cannot be empty")
    private String password;
}
