package com.dxjunkyard.community.util;

import com.dxjunkyard.community.domain.request.CombinedReservationRequest;
import com.dxjunkyard.community.domain.request.EquipmentReservationRequest;
import com.dxjunkyard.community.domain.request.FacilityReservationRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationConverterTest {

    @Test
    void toEquipmentRequest_ShouldConvertCorrectly() {
        CombinedReservationRequest combined = CombinedReservationRequest.builder()
                .communityId(1L)
                .facilityId(2L)
                .equipmentId(3L)
                .date("2025-05-25")
                .startTime("10:00")
                .endTime("12:00")
                .recruitFriends(true)
                .build();

        EquipmentReservationRequest result = ReservationConverter.toEquipmentRequest(combined);

        assertNotNull(result);
        assertEquals(combined.getCommunityId(), result.getCommunityId());
        assertEquals(combined.getFacilityId(), result.getFacilityId());
        assertEquals(combined.getEquipmentId(), result.getEquipmentId());
        assertEquals(combined.getDate(), result.getDate());
        assertEquals(combined.getStartTime(), result.getStartTime());
        assertEquals(combined.getEndTime(), result.getEndTime());
        assertEquals(combined.isRecruitFriends(), result.isRecruitFriends());
    }

    @Test
    void toFacilityRequest_ShouldConvertCorrectly() {
        String userId = "testUser";
        CombinedReservationRequest combined = CombinedReservationRequest.builder()
                .communityId(1L)
                .facilityId(2L)
                .equipmentId(3L)
                .date("2025-05-25")
                .startTime("10:00")
                .endTime("12:00")
                .recruitFriends(true)
                .build();

        FacilityReservationRequest result = ReservationConverter.toFacilityRequest(combined, userId);

        assertNotNull(result);
        assertEquals(combined.getFacilityId().intValue(), result.getFacilityId());
        assertEquals(combined.getCommunityId().intValue(), result.getEventId());
        assertEquals(userId, result.getRenterId());
        assertEquals(combined.getDate() + " " + combined.getStartTime(), result.getStartDate());
        assertEquals(combined.getDate() + " " + combined.getEndTime(), result.getEndDate());
        assertEquals("仲間募集を希望", result.getComment());
    }

    @Test
    void toFacilityRequest_WithRecruitFriendsFalse_ShouldSetCommentToNull() {
        String userId = "testUser";
        CombinedReservationRequest combined = CombinedReservationRequest.builder()
                .communityId(1L)
                .facilityId(2L)
                .equipmentId(3L)
                .date("2025-05-25")
                .startTime("10:00")
                .endTime("12:00")
                .recruitFriends(false)
                .build();

        FacilityReservationRequest result = ReservationConverter.toFacilityRequest(combined, userId);

        assertNotNull(result);
        assertNull(result.getComment());
    }
}
