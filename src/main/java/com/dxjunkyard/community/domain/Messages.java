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
public class Messages {
    private String messageId;
    private String userId;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

