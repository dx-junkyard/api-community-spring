package com.dxjunkyard.community.service;

import com.dxjunkyard.community.domain.request.CombinedReservationRequest;
import com.dxjunkyard.community.domain.request.EquipmentReservationRequest;
import com.dxjunkyard.community.domain.request.FacilityReservationRequest;
import com.dxjunkyard.community.util.ReservationConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private FacilityRentalService facilityRentalService;

    @Mock
    private EquipmentRentalService equipmentRentalService;

    @InjectMocks
    private ReservationService reservationService;

    private CombinedReservationRequest request;
    private String userId;
    private FacilityReservationRequest facilityRequest;
    private EquipmentReservationRequest equipmentRequest;

    @BeforeEach
    void setUp() {
        userId = "testUser";
        request = CombinedReservationRequest.builder()
                .communityId(1L)
                .facilityId(2L)
                .equipmentId(3L)
                .date("2025-05-25")
                .startTime("10:00")
                .endTime("12:00")
                .recruitFriends(true)
                .build();

        facilityRequest = FacilityReservationRequest.builder()
                .facilityId(2)
                .eventId(1)
                .renterId(userId)
                .startDate("2025-05-25 10:00")
                .endDate("2025-05-25 12:00")
                .comment("仲間募集を希望")
                .build();

        equipmentRequest = EquipmentReservationRequest.builder()
                .communityId(1L)
                .facilityId(2L)
                .equipmentId(3L)
                .date("2025-05-25")
                .startTime("10:00")
                .endTime("12:00")
                .recruitFriends(true)
                .build();
    }

    @Test
    void reserveCombined_Success() {
        try (MockedStatic<ReservationConverter> mockedConverter = Mockito.mockStatic(ReservationConverter.class)) {
            mockedConverter.when(() -> ReservationConverter.toFacilityRequest(eq(request), eq(userId)))
                    .thenReturn(facilityRequest);
            mockedConverter.when(() -> ReservationConverter.toEquipmentRequest(eq(request)))
                    .thenReturn(equipmentRequest);

            reservationService.reserveCombined(request, userId);

            verify(facilityRentalService, times(1)).reserve(eq(facilityRequest));
            verify(equipmentRentalService, times(1)).reserve(eq(equipmentRequest), eq(userId));
        }
    }

    @Test
    void reserveCombined_FacilityReservationFails_ShouldRollbackBoth() {
        try (MockedStatic<ReservationConverter> mockedConverter = Mockito.mockStatic(ReservationConverter.class)) {
            mockedConverter.when(() -> ReservationConverter.toFacilityRequest(eq(request), eq(userId)))
                    .thenReturn(facilityRequest);
            mockedConverter.when(() -> ReservationConverter.toEquipmentRequest(eq(request)))
                    .thenReturn(equipmentRequest);

            doThrow(new RuntimeException("Facility reservation failed"))
                    .when(facilityRentalService).reserve(any());

            assertThrows(RuntimeException.class, () -> {
                reservationService.reserveCombined(request, userId);
            });

            verify(facilityRentalService, times(1)).reserve(eq(facilityRequest));
            verify(equipmentRentalService, never()).reserve(any(), any());
        }
    }

    @Test
    void reserveCombined_EquipmentReservationFails_ShouldRollbackBoth() {
        try (MockedStatic<ReservationConverter> mockedConverter = Mockito.mockStatic(ReservationConverter.class)) {
            mockedConverter.when(() -> ReservationConverter.toFacilityRequest(eq(request), eq(userId)))
                    .thenReturn(facilityRequest);
            mockedConverter.when(() -> ReservationConverter.toEquipmentRequest(eq(request)))
                    .thenReturn(equipmentRequest);

            doThrow(new RuntimeException("Equipment reservation failed"))
                    .when(equipmentRentalService).reserve(any(), any());

            assertThrows(RuntimeException.class, () -> {
                reservationService.reserveCombined(request, userId);
            });

            verify(facilityRentalService, times(1)).reserve(eq(facilityRequest));
            verify(equipmentRentalService, times(1)).reserve(eq(equipmentRequest), eq(userId));
        }
    }
}
