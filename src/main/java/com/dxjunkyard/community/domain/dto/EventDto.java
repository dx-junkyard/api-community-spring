package com.dxjunkyard.community.domain.dto;


import com.dxjunkyard.community.domain.Events;
import com.dxjunkyard.community.domain.request.AddEventRequest;
import com.dxjunkyard.community.domain.response.EventPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventDto {
    public static EventPage eventPage(Events event) {
        EventPage eventPage = EventPage.builder()
                .id(event.getId())
                .title(event.getTitle())
                .communityId(event.getCommunityId())
                .description(event.getDescription())
                .recruitmentMessage(event.getRecruitmentMessage())
                .applicationStart(event.getApplicationStart().toLocalDateTime())
                .applicationEnd(event.getApplicationEnd().toLocalDateTime())
                .eventStart(event.getEventStart().toLocalDateTime())
                .eventEnd(event.getEventEnd().toLocalDateTime())
                .placeId(event.getPlaceId())
                .visibility(event.getVisibility())
                .status(event.getStatus())
                .build();
        return eventPage;
    }
    public static Events event(AddEventRequest request) {
        Logger logger = LoggerFactory.getLogger(EventDto.class);
        Timestamp event_start;
        Timestamp event_end;
        Timestamp application_start;
        Timestamp application_end;
        try {
            event_start = Timestamp.valueOf(LocalDateTime.parse(request.getEventStart(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            event_end = Timestamp.valueOf(LocalDateTime.parse(request.getEventEnd(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            application_start = Timestamp.valueOf(LocalDateTime.parse(request.getApplicationStart(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            application_end = Timestamp.valueOf(LocalDateTime.parse(request.getApplicationEnd(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        } catch (Exception e) {
            logger.info("Date transform error = " + e.getMessage());
            return null;
        }
        return Events.builder()
                .title(request.getTitle())
                .ownerId(request.getOwnerId())
                .communityId(request.getCommunityId())
                .eventStart(event_start)
                .eventEnd(event_end)
                .applicationStart(application_start)
                .applicationEnd(application_end)
                .recruitmentMessage(request.getRecruitmentMessage())
                .description(request.getDescription())
                .placeId(request.getPlaceId())
                .visibility(request.getVisibility())
                .status(request.getStatus())
                .build();
    }

    public static Events Page2event(EventPage request) {
        Logger logger = LoggerFactory.getLogger(EventDto.class);
        Timestamp event_start;
        Timestamp event_end;
        Timestamp application_start;
        Timestamp application_end;
        try {
            event_start = Timestamp.valueOf(LocalDateTime.parse(request.getEventStart().toString(),
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            event_end = Timestamp.valueOf(LocalDateTime.parse(request.getEventEnd().toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            application_start = Timestamp.valueOf(LocalDateTime.parse(request.getApplicationStart().toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            application_end = Timestamp.valueOf(LocalDateTime.parse(request.getApplicationEnd().toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        } catch (Exception e) {
            logger.info("Date transform error = " + e.getMessage());
            return null;
        }
        return Events.builder()
                .title(request.getTitle())
                .ownerId(request.getOwnerId())
                .communityId(request.getCommunityId())
                .eventStart(event_start)
                .eventEnd(event_end)
                .applicationStart(application_start)
                .applicationEnd(application_end)
                .recruitmentMessage(request.getRecruitmentMessage())
                .description(request.getDescription())
                .placeId(request.getPlaceId())
                .visibility(request.getVisibility())
                .status(request.getStatus())
                .build();
    }
}
