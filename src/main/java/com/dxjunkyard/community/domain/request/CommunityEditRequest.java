package com.dxjunkyard.community.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityEditRequest {
    private Long communityId;
    private String ownerId;
    private String name;
    private String profile;
}
