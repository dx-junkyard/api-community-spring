package com.dxjunkyard.community.repository.dao.mapper;

import com.dxjunkyard.community.domain.Invitations;

import java.sql.Timestamp;

public interface InvitationMapper {
    void createInvitation(Invitations invitation);
    Invitations getInvitation(String invitationCode);
    void decreaseInvitationCount(String invitationCode);
    void updateExpirationDate(Timestamp expirationAt, String invitationCode);
    //public void updateInvitation(String )
}
