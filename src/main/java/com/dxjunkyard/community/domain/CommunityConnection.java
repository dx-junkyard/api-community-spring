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
public class CommunityConnection {
    private Long parentId;
    private Long childId;
    private String code;
    private Long status;
    private String expirationAt;
}

