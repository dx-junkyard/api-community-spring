package com.dxjunkyard.community.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditCommunityRequest {
    private Long communityId;
    private String ownerId;
    private Long placeId;
    private String name;
    private String summaryMessage;
    private String summaryPr;
    private String description;
    private String profileImageUrl;
    private Integer memberCount;
    private Integer visibility;
}
