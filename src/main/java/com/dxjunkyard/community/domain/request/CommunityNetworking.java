package com.dxjunkyard.community.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityNetworking {
    private Long myCommunityId;
    private List<Long> partnerCommunityId;
    private List<String> partnerCommunityName;
}