package com.dxjunkyard.community.repository.dao.mapper;

import com.dxjunkyard.community.domain.CommunitySummary;
import com.dxjunkyard.community.domain.UserProperty;
import com.dxjunkyard.community.domain.request.UserLoginRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    UserProperty getUserProperty(String user_id);
    void registerUserProperty(UserProperty userProperty);
    void updateUserProperty(UserProperty userProperty);
    UserProperty login(UserLoginRequest login);
}
