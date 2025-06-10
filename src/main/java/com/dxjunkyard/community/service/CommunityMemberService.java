package com.dxjunkyard.community.service;

import com.dxjunkyard.community.domain.CommunityMembers;
import com.dxjunkyard.community.domain.CommunityRole;
import com.dxjunkyard.community.domain.Invitations;
import com.dxjunkyard.community.domain.request.FavoriteRequest;
import com.dxjunkyard.community.repository.dao.mapper.CommunityMemberMapper;
import com.dxjunkyard.community.repository.dao.mapper.InvitationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CommunityMemberService {
    @Autowired
    CommunityMemberMapper communityMemberMapper;
    @Autowired
    private InvitationMapper invitationMapper;

    public Integer  updateFavoriteStatus(String userId, CommunityRole roleId, FavoriteRequest request) {
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

    public CommunityRole getRole(Long communityId, String userId) {
        List<CommunityMembers> members = communityMemberMapper.selectCommunityMember(communityId, userId);
        if (members.isEmpty()) {
            return CommunityRole.VIEWER;
        }
        return members.get(0).getRole();
    }

    public void assignRole(Long communityId, String targetUser, CommunityRole role) {
        CommunityMembers member = CommunityMembers.builder()
                .communityId(communityId)
                .userId(targetUser)
                .role(role)
                .status(1)
                .fav(1)
                .build();
        communityMemberMapper.insertOrUpdateCommunityMember(member);
    }

    public void removeMember(Long communityId, String targetUser) {
        communityMemberMapper.deleteCommunityMember(communityId, targetUser);
    }

    public List<String> getMemberNameList(Long communityId) {
        try {
            //List<String> nameList = //communityMemberMapper.getMemberNameList(communityId);
            List<String> nameList = new ArrayList<String>();
            return nameList;
        } catch (Exception e) {
            return new ArrayList<String>();
        }
    }
}
