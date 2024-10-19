package com.dxjunkyard.community.service;

import com.dxjunkyard.community.domain.FacilityRental;
import com.dxjunkyard.community.domain.request.FacilityReservationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FacilityRentalService {
    private Logger logger = LoggerFactory.getLogger(FacilityRentalService.class);

    //@Autowired
    //FacilityRentalMapper rentalMapper;

    public List<FacilityRental> checkin(String counterId, String userId) {
        List<FacilityRental> rentalList = new ArrayList<>();
        try {
            LocalDate today = LocalDate.now();
            LocalDateTime midnight = today.atStartOfDay();
            // カウンターID, 借り主のuserid, 貸出日付でレンタルする備品リストを取得
            /*
            List<Integer> reservationIdList = rentalMapper.getReservationIdList(
                    counterId
                    , userId
                    , midnight);

            for (Integer reservationId : reservationIdList) {
                List<RentalFlatten> flattenList = rentalMapper.getRental(reservationId);
                Rental rental = RentalDto.rental(flattenList);
                rentalList.add(rental);
            }

             */
            return rentalList;
        } catch (Exception e) {
            logger.info("error" + e.getMessage());
            return rentalList;
        }
    }

    public void reserve(FacilityReservationRequest request) {
        try {
            // todo: 各備品について、その日の在庫があるかどうかをチェックする必要がる
            /*
            List<RentalFlatten> reservationList= RentalDto.reserve(request);
            rentalMapper.addReservation(reservationList);
             */
        } catch (Exception e) {
            logger.info("error" + e.getMessage());
        }
    }
}
