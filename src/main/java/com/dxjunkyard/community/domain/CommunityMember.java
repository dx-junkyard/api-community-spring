package com.dxjunkyard.community.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityMember {
    private Long userId;
    private String userName;
    private String userRole;
    private String profile;
    private List<ActivityHistory> activityHistory;
}

