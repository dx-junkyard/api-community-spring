package com.dxjunkyard.community.repository.dao.mapper;

import com.dxjunkyard.community.domain.CommunitySummary;
import org.apache.ibatis.annotations.Mapper;
import com.dxjunkyard.community.domain.Community;

import java.util.List;

@Mapper
public interface CommunityMapper {
    void addCommunity(Community community);
    void addCommunityMember(Long community_id, String user_id, Integer role_id, Integer status, Integer fav);
    Community editCommunity(Community community);
    Community getCommunity(Long community_id);
    List<CommunitySummary> getCommunityList();
    List<CommunitySummary> searchCommunity(String keyword);
    List<CommunitySummary> getMyCommunityList(String my_id);
}
