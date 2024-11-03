package com.dxjunkyard.community.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Events {
    private Long id;
    private String title;
    private Long communityId;
    private String ownerId;
    private Timestamp eventStart;
    private Timestamp eventEnd;
    private Timestamp applicationStart;
    private Timestamp applicationEnd;
    private String recruitmentMessage;
    private String description;
    private Integer placeId;
    private Integer visibility;
    private Integer status; // 0 : 募集期間前, 1 : 募集期間中, 2 : 募集期間終了, 3: イベント期間中, 4 : イベント終了
}

