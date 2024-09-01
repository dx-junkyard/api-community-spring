package com.dxjunkyard.community.repository.dao.mapper;

import com.dxjunkyard.community.domain.CommunitySummary;
import org.apache.ibatis.annotations.Mapper;
import com.dxjunkyard.community.domain.Community;

import java.util.List;

@Mapper
public interface CommunityMapper {
    void addCommunity(Community community);
    void addCommunityMember(Long community_id, String user_id, Integer role_id, Integer status, Integer fav);
    void editCommunity(Community community);
    void addIncludeSportCommunity(Integer sportCommunityId);
    List<CommunitySummary> getCommunityList();
    List<CommunitySummary> searchCommunityName(String keyword);
    List<CommunitySummary> searchCommunityDescription(String keyword);
    List<CommunitySummary> searchCommunityPR(String keyword);
    List<CommunitySummary> getMyCommunityList(String my_id);
}
