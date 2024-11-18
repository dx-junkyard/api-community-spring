package com.dxjunkyard.community.service;

import com.dxjunkyard.community.domain.CommunityMembers;
import com.dxjunkyard.community.domain.request.FavoriteRequest;
import com.dxjunkyard.community.repository.dao.mapper.CommunityMemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommunityMemberService {
    @Autowired
    CommunityMemberMapper communityMemberMapper;

    public Integer  updateFavoriteStatus(String userId, Integer roleId, FavoriteRequest request) {
        try {
            CommunityMembers member = CommunityMembers.builder()
                    .userId(userId)
                    .role(roleId)
                    .communityId(request.getCommunityId())
                    .fav(request.getStatus())
                    .status(0)
                    .build();
            communityMemberMapper.insertOrUpdateCommunityMember(member);
            //List<CommunityMembers> checkMember = communityMemberMapper.selectCommunityMember(request.getCommunityId(), userId);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
}
