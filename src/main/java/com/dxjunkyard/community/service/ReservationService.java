package com.dxjunkyard.community.service;

import com.dxjunkyard.community.domain.request.CombinedReservationRequest;
import com.dxjunkyard.community.domain.request.EquipmentReservationRequest;
import com.dxjunkyard.community.domain.request.FacilityReservationRequest;
import com.dxjunkyard.community.util.ReservationConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService {
    private Logger logger = LoggerFactory.getLogger(ReservationService.class);

    @Autowired
    private FacilityRentalService facilityRentalService;

    @Autowired
    private EquipmentRentalService equipmentRentalService;

    @Transactional
    public void reserveCombined(CombinedReservationRequest request, String userId) {
        try {
            FacilityReservationRequest facilityRequest = ReservationConverter.toFacilityRequest(request, userId);
            EquipmentReservationRequest equipmentRequest = ReservationConverter.toEquipmentRequest(request);

            facilityRentalService.reserve(facilityRequest);
            equipmentRentalService.reserve(equipmentRequest, userId);
        } catch (Exception e) {
            logger.error("Combined reservation failed", e);
            throw e; // トランザクションをロールバックするために例外を再スロー
        }
    }
}
