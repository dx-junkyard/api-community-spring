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
public class EventSummary {
    private Integer id;
    private String title;
    private Long communityId;
    private LocalDateTime eventStart;
    private LocalDateTime eventEnd;
    private LocalDateTime applicationStart;
    private LocalDateTime applicationEnd;
    private String recruitmentMessage;
    private Integer placeId;
    private Integer status; // 0 : 募集期間前, 1 : 募集期間中, 2 : 募集期間終了, 3: イベント期間中, 4 : イベント終了
}
