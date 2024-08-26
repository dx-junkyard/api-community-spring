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
public class ActivityHistory {
    private String userId;
    private LocalDateTime timeFrom;
    private LocalDateTime timeTo;
    // date
    // 活動内容
}

