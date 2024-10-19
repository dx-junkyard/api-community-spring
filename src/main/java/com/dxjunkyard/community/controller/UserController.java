package com.dxjunkyard.community.controller;

import com.dxjunkyard.community.domain.UserProperty;
import com.dxjunkyard.community.domain.request.UserLoginRequest;
import com.dxjunkyard.community.domain.request.UserRegisterRequest;
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
    private UserService userService;


    // ユーザー登録
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @RequestBody UserRegisterRequest request) {
        logger.info("user register API");
        try {
            String user_id = userService.createUser(request);
            String token = authService.createToken(user_id);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            logger.debug("user register error : " + e.getMessage());
            return ResponseEntity.badRequest().body("user register failed.");
        }
    }

    // ユーザー登録
    @PostMapping("/register-lineid")
    public ResponseEntity<String> registerUser(
            @RequestBody String lineId) {
        logger.info("user register API");
        try {
            // 該当のlineIdユーザーがいない場合だけつくる
            String user_id = userService.createUserByLineId(lineId);
            String token = authService.createToken(user_id);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            logger.debug("user register error : " + e.getMessage());
            return ResponseEntity.badRequest().body("user register failed.");
        }
    }

    // login
    @GetMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody UserLoginRequest request) {
        logger.info("login API");
        try {
            String user_id = userService.login(request);
            String token = authService.createToken(user_id);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            logger.debug("login error : " + e.getMessage());
            return ResponseEntity.badRequest().body("login failed.");
        }
    }

    // ユーザーprofile取得
    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getUserProfile(
            @RequestHeader("Authorization") String authHeader) {
        logger.info("profile API");

        // ユーザー認証 & OKならuser id取得
        String userId = authService.checkAuthHeader(authHeader);
        if (userId == null) {
            return ResponseEntity.status(404).body(null);  // 認証失敗
        }

        // ユーザーIDに基づいてプロフィールを取得
        UserProperty property = userService.getUserProperty(userId);
        UserProfileResponse profile = UserProfileResponse.builder()
                .userId(property.getUser_id())
                .email(property.getEmail())
                .name(property.getName())
                .profile(property.getProfile())
                .build();
        if (profile == null) {
            return ResponseEntity.status(404).body(null);  // Not Found
        }
        return ResponseEntity.ok(profile);  // 成功時にプロフィールを返す
    }

    @GetMapping("/hello")
    @ResponseBody
    public ResponseEntity checkin(){
        logger.info("疎通確認 URL");
        return ResponseEntity.ok("OK");
    }

}
