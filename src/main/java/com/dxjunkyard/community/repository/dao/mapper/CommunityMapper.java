package com.dxjunkyard.community.repository.dao.mapper;

import com.dxjunkyard.community.domain.CommunitySelector;
import com.dxjunkyard.community.domain.CommunitySummary;
import org.apache.ibatis.annotations.Mapper;
import com.dxjunkyard.community.domain.Community;

import java.util.List;

@Mapper
public interface CommunityMapper {
    void addCommunity(Community community);
    void updateCommunity(Community community);
    void updatePhotoPath(Long communityId, String photoPath);
    Community getCommunity(Long community_id);
    String getCommunityName(Long community_id);
    List<CommunitySummary> getCommunityList(String myId);
    List<CommunitySummary> searchCommunity(String myId, String keyword);
    List<CommunitySelector> getMyCommunitySelector(String my_id);
}
