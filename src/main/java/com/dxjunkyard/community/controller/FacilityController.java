package com.dxjunkyard.community.controller;

import com.dxjunkyard.community.domain.request.FacilityReservationRequest;
import com.dxjunkyard.community.service.FacilityRentalService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//public class FacilityController {
@RestController
@RequestMapping("/v1/api")
@Slf4j
public class FacilityController {
    private Logger logger = LoggerFactory.getLogger(FacilityController.class);

    @Autowired
    private FacilityRentalService rentalService;

    /**
     *
     */
    @PostMapping("/facility-reserve")
    @ResponseBody
    public ResponseEntity<?> makeReservation(
            @RequestBody FacilityReservationRequest request){
        logger.info("rental API");
        try {
            // todo : parameter validation
            rentalService.reserve(request);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            logger.debug("rental" + e.getMessage());
            return ResponseEntity.badRequest().body("facility-reserve error.");
        }
    }

    /**
     *
     */
    @GetMapping("/check-in")
    @ResponseBody
    public ResponseEntity<?> checkIn(
            @RequestParam("counterId") String counterId
            ,@RequestParam("userId") String userId
    ){
        logger.info("rental API");
        try {
            // todo : parameter validation
            /*
            return CheckInResponse.builder()
                    .rentalList(rentalService.checkin(counterId,userId))
                    .status("OK")
                    .build();
             */
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            logger.debug("rental" + e.getMessage());
            return ResponseEntity.badRequest().body("check-in error.");
        }
    }

    /**
     *
     */
    @GetMapping("/check-out")
    @ResponseBody
    public ResponseEntity<?> checkOut(
            @RequestParam("counterId") String counterId
            ,@RequestParam("userId") String userId
    ){
        logger.info("rental API");
        try {
            // todo : parameter validation
            /*
            return CheckInResponse.builder()
                    .rentalList(rentalService.checkin(counterId,userId))
                    .status("OK")
                    .build();
             */
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            logger.debug("rental" + e.getMessage());
            return ResponseEntity.badRequest().body("check-out error.");
            //return CheckInResponse.builder().status("NG").build();
        }
    }

    /**
     *
     */
    @PostMapping("/rental-confirm")
    @ResponseBody
    public ResponseEntity<?> rentalConfirm(
            @RequestBody FacilityReservationRequest request ) {
            //@RequestBody RentalConfirmationRequest request ) {
            logger.info("rental API");
        try {
            // todo : parameter validation
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            logger.debug("rental" + e.getMessage());
            return ResponseEntity.badRequest().body("rental-confirm error.");
        }
    }


    @GetMapping("/hello")
    @ResponseBody
    public ResponseEntity<?> checkin(){
        logger.info("疎通確認 URL");
        return ResponseEntity.ok("ok");
    }

}
