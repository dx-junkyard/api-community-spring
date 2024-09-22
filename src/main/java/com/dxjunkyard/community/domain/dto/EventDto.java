package com.dxjunkyard.community.domain.dto;


import com.dxjunkyard.community.domain.Event;
import com.dxjunkyard.community.domain.request.AddEventRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventDto {
    public static Event event(AddEventRequest request) {
        Logger logger = LoggerFactory.getLogger(EventDto.class);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime event_start;
        LocalDateTime event_end;
        LocalDateTime application_start;
        LocalDateTime application_end;
        try {
            event_start = LocalDateTime.parse(request.getEventStart(),dtf);
            event_end = LocalDateTime.parse(request.getEventEnd(),dtf);
            application_start = LocalDateTime.parse(request.getApplicationStart(),dtf);
            application_end = LocalDateTime.parse(request.getApplicationEnd(),dtf);
        } catch (Exception e) {
            logger.info("Date transform error = " + e.getMessage());
            return null;
        }
        return Event.builder()
                .title(request.getTitle())
                .ownerId(request.getOwnerId())
                .communityId(request.getCommunityId())
                .eventStart(event_start)
                .eventEnd(event_end)
                .applicationStart(application_start)
                .applicationEnd(application_end)
                .comment(request.getComment())
                .placeId(request.getPlaceId())
                .visibility(request.getVisibility())
                .build();
    }
}
