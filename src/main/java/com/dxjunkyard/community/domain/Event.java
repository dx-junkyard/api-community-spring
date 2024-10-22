package com.dxjunkyard.community.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private Integer id;
    private String title;
    private Long communityId;
    private String ownerId;
    private LocalDateTime eventStart;
    private LocalDateTime eventEnd;
    private LocalDateTime applicationStart;
    private LocalDateTime applicationEnd;
    private String recruitmentMessage;
    private String description;
    private Integer placeId;
    private Integer visibility;
    private Integer status; // 0 : 募集期間前, 1 : 募集期間中, 2 : 募集期間終了, 3: イベント期間中, 4 : イベント終了
}

