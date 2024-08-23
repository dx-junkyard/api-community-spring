package com.dxjunkyard.community.domain.response;


import lombok.Data;

@Data
public class AdminResponse {
    public AdminResponse(Long uid, String uname) {
        userId = uid;
        userName = uname;
    }
    private Long userId;
    private String userName;
}

