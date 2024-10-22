package com.dxjunkyard.community.domain.response;

import com.dxjunkyard.community.domain.CommunitySelector;
import com.dxjunkyard.community.domain.EventInvitation;
import com.dxjunkyard.community.domain.FavoriteCommunity;
import com.dxjunkyard.community.domain.UpcommingEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyPage {
    private List<CommunitySelector> communitySelectorList;
    private List<UpcommingEvent> upcommingEventList;
    private List<EventInvitation> eventInvitationList;
    private List<FavoriteCommunity> favoriteCommunityList;
}

