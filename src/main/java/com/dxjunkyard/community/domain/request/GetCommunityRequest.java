package com.dxjunkyard.community.domain.request;

import lombok.Data;

@Data
public class GetCommunityRequest {
    private Long communityId;
    private String ownerId;
    private Long placeId;
    private String name;
    private String thumbnailImageUrl;
    private String thumbnailMessage;
    private String thumbnailPr;
    private String description;
    private String profileImageUrl;
    private Integer memberCount;
    private Integer visibility;
    private String profile;
    private Integer status;
}
