package com.dxjunkyard.community.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private Integer eventId;
    private String title;
    private Long communityId;
    private String ownerId;
    private LocalDateTime eventStart;
    private LocalDateTime eventEnd;
    private LocalDateTime applicationStart;
    private LocalDateTime applicationEnd;
    private String comment;
    private Integer placeId;
    private Integer visibility;
}

