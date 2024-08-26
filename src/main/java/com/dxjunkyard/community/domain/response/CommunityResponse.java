package com.dxjunkyard.community.domain.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityResponse {
    private Long communityId;
    private String name;
    private String email;
    private String profile;
    private String systemMessage;
}
