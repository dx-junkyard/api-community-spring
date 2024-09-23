package com.dxjunkyard.community.service;


import com.dxjunkyard.community.domain.UserProperty;
import com.dxjunkyard.community.domain.request.UserLoginRequest;
import com.dxjunkyard.community.domain.request.UserRegisterRequest;
import com.dxjunkyard.community.repository.dao.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private SHA256PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Value("${encrypt.db.secret}")
    private String encrypt_key;

    public String createUser(UserRegisterRequest request) {
        UUID uuid = UUID.randomUUID();
        userMapper.registerUserProperty(
                UserProperty.builder()
                        .user_id(uuid.toString())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .name(request.getName())
                        .status(1)
                        .build()
        );
        return uuid.toString();
    }

    public void registerUserInfo(
            String user_id,
            UserRegisterRequest request) {
        userMapper.updateUserProperty(
                UserProperty.builder()
                        .user_id(user_id)
                        .name(request.getName())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .status(1)
                        .build());
    }

    public String login(
            UserLoginRequest request) {
        UserProperty userProperty = userMapper.login(
                UserLoginRequest.builder()
                        .email(request.getEmail())
                        .build());
        if (userProperty == null) {
            return null;
        }
        if (passwordEncoder.matches(request.getPassword(), userProperty.getPassword())) {
            return userProperty.getUser_id();
        } else {
            return null;
        }
    }

    public UserProperty getUserProperty(
            String userId) {
        UserProperty userProperty = userMapper.getUserProperty(userId);
        return userProperty;
    }
}
