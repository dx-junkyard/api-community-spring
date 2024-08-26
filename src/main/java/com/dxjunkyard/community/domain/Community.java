package com.dxjunkyard.community.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Community {
    private Long communityId;
    private String ownerId;
    private String name;
    private String profile;
}

