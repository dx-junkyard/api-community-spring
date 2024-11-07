package com.dxjunkyard.community.repository.dao.mapper;

import com.dxjunkyard.community.domain.CommunityMembers;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityMemberMapper {
    void insertOrUpdateCommunityMember(CommunityMembers member);
    List<CommunityMembers> selectCommunityMember(Long communityId, String userId);
}
