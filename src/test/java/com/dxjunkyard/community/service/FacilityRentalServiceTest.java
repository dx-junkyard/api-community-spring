package com.dxjunkyard.community.service;

import com.dxjunkyard.community.domain.FacilityRental;
import com.dxjunkyard.community.domain.request.FacilityReservationRequest;
import com.dxjunkyard.community.repository.dao.mapper.FacilityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FacilityRentalServiceTest {

    @Mock
    private FacilityMapper facilityMapper;

    @InjectMocks
    private FacilityRentalService facilityRentalService;

    private FacilityReservationRequest request;
    private DateTimeFormatter formatter;

    @BeforeEach
    void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        request = FacilityReservationRequest.builder()
                .facilityId(1)
                .eventId(2)
                .renterId("testUser")
                .startDate("2025-05-25 10:00")
                .endDate("2025-05-25 12:00")
                .comment("テスト予約")
                .build();
    }

    @Test
    void convertToReservation_ShouldSetAllFields() {
        FacilityRental result = facilityRentalService.convertToReservation(request);

        assertNotNull(result);
        assertEquals(request.getFacilityId(), result.getFacilityId());
        assertEquals(request.getEventId(), result.getEventId());
        assertEquals(request.getRenterId(), result.getRenterId());
        
        LocalDateTime expectedStart = LocalDateTime.parse(request.getStartDate(), formatter);
        LocalDateTime expectedEnd = LocalDateTime.parse(request.getEndDate(), formatter);
        
        assertEquals(expectedStart, result.getStartDate());
        assertEquals(expectedEnd, result.getEndDate());
        assertEquals(request.getComment(), result.getComment());
        
        assertEquals(expectedStart, result.getUsageDate());
        assertEquals(Integer.valueOf(1), result.getEquipmentN());
        assertEquals(Integer.valueOf(0), result.getStatus());
    }

    @Test
    void reserve_ShouldCallMapperWithCorrectData() {
        doNothing().when(facilityMapper).reserve(any(FacilityRental.class));

        facilityRentalService.reserve(request);

        verify(facilityMapper, times(1)).reserve(any(FacilityRental.class));
    }

    @Test
    void reserve_WhenMapperThrowsException_ShouldRethrow() {
        doThrow(new RuntimeException("Database error")).when(facilityMapper).reserve(any(FacilityRental.class));

        assertThrows(RuntimeException.class, () -> {
            facilityRentalService.reserve(request);
        });
    }
}
