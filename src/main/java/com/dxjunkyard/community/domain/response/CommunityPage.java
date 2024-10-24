package com.dxjunkyard.community.domain.response;

import com.dxjunkyard.community.domain.ActivityHistory;
import com.dxjunkyard.community.domain.EventSchedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityPage {
    private Long id;
    private String location;
    private String name;
    private String summaryImageUrl;
    private String profileImageUrl;
    private String description;
    private Integer visibility;
    private List<EventSchedule> eventScheduleList;
    private List<ActivityHistory> activityHistoryList;
}
