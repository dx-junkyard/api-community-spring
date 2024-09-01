package com.dxjunkyard.community.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {
    @NotBlank(message = "e-mail cannot be empty")
    private String email;

    @NotBlank(message = "password cannot be empty")
    private String password;
}
