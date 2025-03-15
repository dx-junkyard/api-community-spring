package com.dxjunkyard.community.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invitations {
    Long communityId;
    Long eventId;
    Integer remainingUses;
    String invitationCode;
    Timestamp expirationAt;
}
