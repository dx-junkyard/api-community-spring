package com.dxjunkyard.community.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProperty {
    private String user_id;
    private String name;
    private String password;
    private String email;
    private String profile;
    private Integer status;
}
