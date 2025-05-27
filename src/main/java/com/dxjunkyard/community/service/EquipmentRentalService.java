package com.dxjunkyard.community.service;

import com.dxjunkyard.community.domain.EquipmentRental;
import com.dxjunkyard.community.domain.dto.ReservationDTO;
import com.dxjunkyard.community.domain.request.EquipmentReservationRequest;
import com.dxjunkyard.community.repository.dao.mapper.EquipmentMapper;
import com.dxjunkyard.community.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class EquipmentRentalService {
    private Logger logger = LoggerFactory.getLogger(EquipmentRentalService.class);

    @Autowired
    EquipmentMapper rentalMapper;

    // ReservationRequestを受け取った後の変換例
    public EquipmentRental convertToReservation(EquipmentReservationRequest request, String renterId) {
        EquipmentRental rental = new EquipmentRental();
        rental.setEquipmentId(request.getEquipmentId());
        rental.setEventId(request.getCommunityId());
        rental.setRenterId(renterId);
        rental.setEquipmentN(1); // 通常は1台予約と仮定

        // 日付と時刻を結合してLocalDateTimeに変換する
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDateTime = LocalDateTime.parse(request.getDate() + " " + request.getStartTime(), formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(request.getDate() + " " + request.getEndTime(), formatter);

        rental.setStartDate(startDateTime);
        rental.setEndDate(endDateTime);
        // usage_date は業務要件に合わせて設定（ここでは start_date と同じとする）
        rental.setUsageDate(startDateTime);

        // recruitFriends に応じたコメント設定（例）
        if(request.isRecruitFriends()){
            rental.setComment("仲間募集を希望");
        } else {
            rental.setComment(null);
        }

        // status は初期値 0 を設定
        rental.setStatus(0);

        return rental;
    }

    public void reserve(EquipmentReservationRequest request,String myId) {
        try {

            EquipmentRental rental = convertToReservation(request, myId);
            rentalMapper.reserve(rental);
            // todo: 各備品について、その日の在庫があるかどうかをチェックする必要がる
            /*
            List<RentalFlatten> reservationList= RentalDto.reserve(request);
            rentalMapper.addReservation(reservationList);
             */
        } catch (Exception e) {
            logger.info("error" + e.getMessage());
        }
    }
    
    /**
     * 指定された設備IDと日付範囲に基づいて予約情報を取得する
     *
     * @param equipmentId 設備ID
     * @param startDateStr 開始日（yyyy-MM-dd形式）
     * @param endDateStr 終了日（yyyy-MM-dd形式）
     * @return 予約情報DTOのリスト
     */
    public List<ReservationDTO> getReservations(Long equipmentId, String startDateStr, String endDateStr) {
        try {
            LocalDateTime startDate = LocalDate.parse(startDateStr).atStartOfDay();
            LocalDateTime endDate = LocalDate.parse(endDateStr).atTime(23, 59, 59);
            
            List<EquipmentRental> rentals = rentalMapper.findReservationsByEquipmentIdAndDateRange(equipmentId, startDate, endDate);
            
            List<ReservationDTO> reservationDTOs = new ArrayList<>();
            for (EquipmentRental rental : rentals) {
                ReservationDTO dto = ReservationDTO.builder()
                    .day(DateTimeUtil.getDayOfWeek(rental.getStartDate()))
                    .date(DateTimeUtil.formatDate(rental.getStartDate()))
                    .startTime(DateTimeUtil.formatTime(rental.getStartDate()))
                    .endTime(DateTimeUtil.formatTime(rental.getEndDate()))
                    .eventText("備品予約: " + equipmentId)
                    .build();
                reservationDTOs.add(dto);
            }
            
            return reservationDTOs;
        } catch (Exception e) {
            logger.error("備品予約情報取得エラー", e);
            throw e;
        }
    }
}

