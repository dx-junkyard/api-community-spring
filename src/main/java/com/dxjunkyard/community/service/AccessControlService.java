package com.dxjunkyard.community.service;

import com.dxjunkyard.community.domain.CommunityRole;
import com.dxjunkyard.community.domain.PermissionType;
import com.dxjunkyard.community.repository.dao.mapper.CommunityMemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessControlService {
    @Autowired
    private CommunityMemberMapper communityMemberMapper;

    public boolean hasPermission(String userId, Long communityId, PermissionType action) {
        CommunityRole role = getRole(userId, communityId);
        switch (action) {
            case VIEW:
                return true;
            case JOIN:
                return role.getId() >= CommunityRole.PARTICIPANT.getId();
            case PLAN_EVENT:
                return role.getId() >= CommunityRole.PLANNER.getId();
            case ISSUE_INVITE:
                return role.getId() >= CommunityRole.RECRUITER.getId();
            case MANAGE_COMMUNITY:
                return role.getId() >= CommunityRole.ADMIN.getId();
            default:
                return false;
        }
    }

    private CommunityRole getRole(String userId, Long communityId) {
        return communityMemberMapper.selectCommunityMember(communityId, userId)
                .stream()
                .map(m -> m.getRole())
                .findFirst()
                .orElse(CommunityRole.VIEWER);
    }
}
