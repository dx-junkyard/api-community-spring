package com.dxjunkyard.community.domain.request;


import com.dxjunkyard.community.domain.CommunityRole;
import lombok.Data;

@Data
public class AssignRoleRequest {
    private String userId;
    private CommunityRole role;
}
