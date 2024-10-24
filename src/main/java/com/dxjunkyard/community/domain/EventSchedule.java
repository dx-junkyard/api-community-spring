package com.dxjunkyard.community.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventSchedule {
    private Long eventId;
    private LocalDateTime dateTime;
    private String eventName;
    private String location;
    private Long favN;
}
