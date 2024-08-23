package com.dxjunkyard.community.domain.dto;


import com.dxjunkyard.community.domain.Community;
import com.dxjunkyard.community.domain.request.AddCommunityRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommunityDto {
    public static Community community(AddCommunityRequest request) {
        Logger logger = LoggerFactory.getLogger(CommunityDto.class);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime time_from;
        LocalDateTime time_to;
        try {
            time_from = LocalDateTime.parse(request.getTimeFrom(),dtf);
            time_to = LocalDateTime.parse(request.getTimeTo(),dtf);
        } catch (Exception e) {
            logger.info("Date transform error = " + e.getMessage());
            return null;
        }
        return Community.builder()
                .name(request.getTitle())
                .ownerId(request.getOwnerId())
                .build();
    }
}
