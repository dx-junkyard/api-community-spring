package com.dxjunkyard.community.util;

import com.dxjunkyard.community.domain.request.CombinedReservationRequest;
import com.dxjunkyard.community.domain.request.EquipmentReservationRequest;
import com.dxjunkyard.community.domain.request.FacilityReservationRequest;

public class ReservationConverter {

    public static EquipmentReservationRequest toEquipmentRequest(CombinedReservationRequest request) {
        return EquipmentReservationRequest.builder()
                .communityId(request.getCommunityId())
                .facilityId(request.getFacilityId())
                .equipmentId(request.getEquipmentId())
                .date(request.getDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .recruitFriends(request.isRecruitFriends())
                .build();
    }

    public static FacilityReservationRequest toFacilityRequest(CombinedReservationRequest request, String userId) {
        return FacilityReservationRequest.builder()
                .facilityId(request.getFacilityId().intValue())
                .eventId(request.getCommunityId().intValue())
                .renterId(userId)
                .startDate(request.getDate() + " " + request.getStartTime())
                .endDate(request.getDate() + " " + request.getEndTime())
                .comment(request.isRecruitFriends() ? "仲間募集を希望" : null)
                .build();
    }
}
