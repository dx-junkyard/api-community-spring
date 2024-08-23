package com.dxjunkyard.community.domain.response;


import lombok.Data;

@Data
public class InviteMemberRequest {
    public InviteMemberRequest(Long uid) {
        userId = uid;
    }
    private Long userId;
}

