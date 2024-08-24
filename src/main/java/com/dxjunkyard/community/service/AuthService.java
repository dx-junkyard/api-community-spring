package com.dxjunkyard.community.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuthService {
    private Logger logger = LoggerFactory.getLogger(AuthService.class);

    public String checkAuthHeader(String authHeader) {
        // Authorizationヘッダーの形式が正しいか確認
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;  // Unauthorized
        }

        // トークンを取得
        String token = authHeader.substring(7);  // "Bearer "の部分を除く

        // トークンの正当性をMock関数で検証（実際にはサービス層でJWTの検証を行う）
        String userId = validateTokenAndGetUserId(token);
        if (userId == null) {
            return null;  // Unauthorized
        }
        return userId;
    }

    private String validateTokenAndGetUserId(String token) {
        // JWTの検証を行い、正しい場合はユーザーIDを返す
        if (token.equals("valid-token")) {
            return "12345";  // 正常なユーザーIDを返す
        }
        return null;  // トークンが無効な場合はnullを返す
    }
}
