package com.dxjunkyard.community.repository.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.dxjunkyard.community.domain.Community;

import java.util.List;

@Mapper
public interface CommunityMapper {
    void addCommunity(Community community);
    void addIncludeSportCommunity(Integer sportCommunityId);
    List<Community> getCommunity(Integer communityId);
}
