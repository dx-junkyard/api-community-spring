package com.dxjunkyard.community.repository.dao.mapper;

import com.dxjunkyard.community.domain.CommunityMembers;
import com.dxjunkyard.community.domain.CommunityRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityMemberMapper {
    void insertOrUpdateCommunityMember(CommunityMembers member);
    List<CommunityMembers> selectCommunityMember(Long communityId, String userId);
    //List<String > getMemberNameList(Long communityId);
    void addCommunityMember(Long community_id, String user_id, CommunityRole role_id, Integer status, Integer fav);
    void updateRole(Long community_id, String user_id, CommunityRole role_id);
    void deleteCommunityMember(Long community_id, String user_id);
}
