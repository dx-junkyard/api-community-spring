package com.dxjunkyard.community.repository.dao.mapper;

import com.dxjunkyard.community.domain.CommunityMembers;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityMemberMapper {
    void insertOrUpdateCommunityMember(CommunityMembers member);
    List<CommunityMembers> selectCommunityMember(Long communityId, String userId);
    void addCommunityMember(Long community_id, String user_id, Integer role_id, Integer status, Integer fav);
}
