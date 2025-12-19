package com.dxjunkyard.community.service;

import com.dxjunkyard.community.domain.FacilityRental;
import com.dxjunkyard.community.domain.dto.ClosureScheduleDTO;
import com.dxjunkyard.community.domain.dto.ReservationDTO;
import com.dxjunkyard.community.domain.request.FacilityReservationRequest;
import com.dxjunkyard.community.repository.dao.mapper.FacilityMapper;
import com.dxjunkyard.community.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class FacilityRentalService {
    private Logger logger = LoggerFactory.getLogger(FacilityRentalService.class);

    @Autowired
    FacilityMapper facilityMapper;

    public int checkIn(Long facilityId, String userId) {
        try {
            return facilityMapper.updateStatusByFacilityAndRenter(facilityId, userId, 0, 1);
        } catch (Exception e) {
            logger.info("error" + e.getMessage());
            throw e;
        }
    }

    @Transactional
    public int checkOut(String userId) {
        try {
            return facilityMapper.updateStatusByRenter(userId, 1, 2);
        } catch (Exception e) {
            logger.info("error" + e.getMessage());
            throw e;
        }
    }

    public FacilityRental convertToReservation(FacilityReservationRequest request) {
        FacilityRental rental = new FacilityRental();
        rental.setFacilityId(request.getFacilityId());
        rental.setEventId(request.getEventId());
        rental.setRenterId(request.getRenterId());
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDateTime = LocalDateTime.parse(request.getStartDate(), formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(request.getEndDate(), formatter);
        
        rental.setStartDate(startDateTime);
        rental.setEndDate(endDateTime);
        
        rental.setUsageDate(startDateTime);
        rental.setEquipmentN(1); // デフォルト値
        
        rental.setComment(request.getComment());
        
        rental.setStatus(0);
        
        return rental;
    }

    public void reserve(FacilityReservationRequest request) {
        try {
            FacilityRental rental = convertToReservation(request);
            
            facilityMapper.reserve(rental);
        } catch (Exception e) {
            logger.error("Facility reservation error: " + e.getMessage(), e);
            throw e; // 上位層でトランザクション処理するために例外を再スロー
        }
    }
    
    /**
     * 指定された施設IDと日付範囲に基づいて予約情報を取得する
     *
     * @param facilityId 施設ID
     * @param startDateStr 開始日（yyyy-MM-dd形式）
     * @param endDateStr 終了日（yyyy-MM-dd形式）
     * @return 予約情報DTOのリスト
     */
    public List<ReservationDTO> getReservations(Long facilityId, String startDateStr, String endDateStr) {
        try {
            LocalDateTime startDate = LocalDate.parse(startDateStr).atStartOfDay();
            LocalDateTime endDate = LocalDate.parse(endDateStr).atTime(23, 59, 59);
            
            List<FacilityRental> rentals = facilityMapper.findReservationsByFacilityIdAndDateRange(facilityId, startDate, endDate);
            
            List<ReservationDTO> reservationDTOs = new ArrayList<>();
            for (FacilityRental rental : rentals) {
                ReservationDTO dto = ReservationDTO.builder()
                    .day(DateTimeUtil.getDayOfWeek(rental.getStartDate()))
                    .date(DateTimeUtil.formatDate(rental.getStartDate()))
                    .startTime(DateTimeUtil.formatTime(rental.getStartDate()))
                    .endTime(DateTimeUtil.formatTime(rental.getEndDate()))
                    .eventText("設備予約: " + facilityId)
                    .build();
                reservationDTOs.add(dto);
            }
            
            return reservationDTOs;
        } catch (Exception e) {
            logger.error("設備予約情報取得エラー", e);
            throw e;
        }
    }

    public List<ClosureScheduleDTO> getClosures(Long facilityId, String startDateStr, String endDateStr) {
        try {
            LocalDateTime startDate = LocalDate.parse(startDateStr).atStartOfDay();
            LocalDateTime endDate = LocalDate.parse(endDateStr).atTime(23, 59, 59);

            List<FacilityRental> rentals = facilityMapper.findReservationsByFacilityIdAndDateRange(facilityId, startDate, endDate);

            List<ClosureScheduleDTO> closureScheduleDTOs = new ArrayList<>();
            for (FacilityRental rental : rentals) {
                ClosureScheduleDTO dto = ClosureScheduleDTO.builder()
                        .day(DateTimeUtil.getDayOfWeek(rental.getStartDate()))
                        .date(DateTimeUtil.formatDate(rental.getStartDate()))
                        .startTime(DateTimeUtil.formatTime(rental.getStartDate()))
                        .endTime(DateTimeUtil.formatTime(rental.getEndDate()))
                        .eventText("予約不可: " + facilityId)
                        .build();
                closureScheduleDTOs.add(dto);
            }

            return closureScheduleDTOs;
        } catch (Exception e) {
            logger.error("設備予約情報取得エラー", e);
            throw e;
        }
    }
}
