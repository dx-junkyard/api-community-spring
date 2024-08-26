package com.dxjunkyard.community.controller;

import com.dxjunkyard.community.domain.request.AssignRoleRequest;
import com.dxjunkyard.community.domain.request.UserLoginRequest;
import com.dxjunkyard.community.domain.request.UserRegisterRequest;
import com.dxjunkyard.community.domain.response.AdminResponse;
import com.dxjunkyard.community.domain.response.InviteMemberRequest;
import com.dxjunkyard.community.domain.response.MemberResponse;
import com.dxjunkyard.community.domain.response.UserProfileResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import com.dxjunkyard.community.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/api/users")
@Slf4j
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private CommunityService communityService;


    // 権限の付与/変更
    @PostMapping("/register")
    public ResponseEntity<String> assignRole(
            @RequestBody UserRegisterRequest request) {
        logger.info("user register API");
        try {
            return ResponseEntity.ok("user register ok.");
        } catch (Exception e) {
            logger.debug("user register error : " + e.getMessage());
            return ResponseEntity.badRequest().body("user register failed.");
        }
    }

    // 権限の確認
    @GetMapping("/login")
    public ResponseEntity<String> getRole(
            @RequestBody UserLoginRequest request) {
        logger.info("login API");
        // 権限の確認ロジック
        try {
            String token = "valid-token";
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            logger.debug("login error : " + e.getMessage());
            return ResponseEntity.badRequest().body("login failed.");
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getUserProfile(
            @RequestHeader("Authorization") String authHeader) {
        logger.info("profile API");

        String userId = authService.checkAuthHeader(authHeader);
        if (userId == null) {
            return ResponseEntity.status(404).body(null);  // Not Found
        }

        // ユーザーIDに基づいてプロフィールを取得（Mockのデータ使用）
        UserProfileResponse profile = getUserProfileById(userId);
        if (profile == null) {
            return ResponseEntity.status(404).body(null);  // Not Found
        }

        return ResponseEntity.ok(profile);  // 成功時にプロフィールを返す
    }

    // Mock関数: ユーザーIDに基づいてユーザープロフィールを返す
    private UserProfileResponse getUserProfileById(String userId) {
        if (userId.equals("12345")) {
            return new UserProfileResponse("12345", "John Doe", "john@example.com","profile");
        }
        return null;
    }

    @GetMapping("/hello")
    @ResponseBody
    public ResponseEntity checkin(){
        logger.info("疎通確認 URL");
        return ResponseEntity.ok("OK");
    }

}
