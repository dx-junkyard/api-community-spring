package com.dxjunkyard.community.domain.response;


import lombok.Data;

@Data
public class MemberResponse {
    public MemberResponse(Long uid, String uname, String urole) {
        userId = uid;
        userName = uname;
        userRole = urole;
    }
    private Long userId;
    private String userName;
    private String userRole;
}

