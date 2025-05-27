package com.dxjunkyard.community.controller;

import com.dxjunkyard.community.domain.dto.ClosureScheduleDTO;
import com.dxjunkyard.community.domain.dto.ReservationDTO;
import com.dxjunkyard.community.domain.request.CombinedReservationRequest;
import com.dxjunkyard.community.domain.request.EquipmentReservationRequest;
import com.dxjunkyard.community.domain.request.FacilityReservationRequest;
import com.dxjunkyard.community.service.AuthService;
import com.dxjunkyard.community.service.EquipmentRentalService;
import com.dxjunkyard.community.service.FacilityRentalService;
import com.dxjunkyard.community.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/api")
@Slf4j
public class ReservationController {
    private Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @Autowired
    private FacilityRentalService facilityRentalService;

    @Autowired
    private EquipmentRentalService equipmentRentalService;

    @Autowired
    private AuthService authService;
    
    @Autowired
    private ReservationService reservationService;
    /**
     *
     */
    @PostMapping("/equipment-reserve/new")
    @ResponseBody
    public ResponseEntity<?> makeEquipmentReservation(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody EquipmentReservationRequest request){
        logger.info("rental API");
        try {
            String myId = authService.checkAuthHeader(authHeader);
            if (myId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Authorization failed"));
            }
            // todo : parameter validation
            equipmentRentalService.reserve(request,myId);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            logger.debug("rental" + e.getMessage());
            return ResponseEntity.badRequest().body("facility-reserve error.");
        }
    }

    @PostMapping("/facility-reserve")
    @ResponseBody
    public ResponseEntity<?> makeReservation(
            @RequestBody FacilityReservationRequest request){
        logger.info("rental API");
        try {
            // todo : parameter validation
            facilityRentalService.reserve(request);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            logger.debug("rental" + e.getMessage());
            return ResponseEntity.badRequest().body("facility-reserve error.");
        }
    }
    /**
     * 機器予約情報取得 API
     * @param equipmentId 機器ID
     * @param startDate 開始日
     * @param endDate 終了日
     * @return 予約情報リスト
     */
    @GetMapping("/equipment/{equipmentId}/closures")
    @ResponseBody
    public ResponseEntity<List<ClosureScheduleDTO>> getEquipmentClosures(
            @PathVariable String equipmentId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {

        logger.info("備品予約窓口情報取得 API - equipmentId: {}, 期間: {} から {}", equipmentId, startDate, endDate);
        try {
            // TODO: 日付のバリデーション

            // TODO: equipmentRentalServiceで予約情報を取得する処理を実装
            List<ClosureScheduleDTO> schedules = new ArrayList<>();

            // 例として、サービスから取得したデータをDTOに変換する処理
            schedules.add(
                ClosureScheduleDTO.builder()
                    .day(1)  // 月曜日
                    .date("2024-05-05")
                    .startTime("00:00")
                    .endTime("08:00")
                    .eventText("予約不可: " + equipmentId)
                    .build()
            );

            return ResponseEntity.ok(schedules);
        } catch (Exception e) {
            logger.error("機器予約情報取得エラー", e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * 備品予約情報取得 API
     * @param equipmentId 備品ID
     * @param startDate 開始日
     * @param endDate 終了日
     * @return 予約情報リスト
     */
    @GetMapping("/equipment/{equipmentId}/reservations")
    @ResponseBody
    public ResponseEntity<List<ReservationDTO>> getEquipmentReservations(
            @PathVariable String equipmentId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {

        logger.info("備品予約情報取得 API - equipmentId: {}, 期間: {} から {}", equipmentId, startDate, endDate);
        try {
            // 日付のバリデーション

            // equipmentRentalServiceで予約情報を取得
            List<ReservationDTO> schedules = equipmentRentalService.getReservations(
                    Long.parseLong(equipmentId), startDate, endDate);

            return ResponseEntity.ok(schedules);
        } catch (Exception e) {
            logger.error("備品予約情報取得エラー", e);
            return ResponseEntity.badRequest().body(null);
        }
    }
    /**
     * 設備予約情報取得 API
     * @param facilityId 設備ID
     * @param startDate 開始日
     * @param endDate 終了日
     * @return 予約情報リスト
     */
    @GetMapping("/facility/{facilityId}/closures")
    @ResponseBody
    public ResponseEntity<List<ClosureScheduleDTO>> getFacilityClosures(
            @PathVariable String facilityId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {

        logger.info("設備予約窓口情報取得 API - facilityId: {}, 期間: {} から {}", facilityId, startDate, endDate);
        try {
            // TODO: 日付のバリデーション

            // TODO: equipmentRentalServiceで予約情報を取得する処理を実装
            List<ClosureScheduleDTO> schedules = new ArrayList<>();

            // 例として、サービスから取得したデータをDTOに変換する処理
            schedules.add(
                    ClosureScheduleDTO.builder()
                            .day(1)  // 月曜日
                            .date("2024-05-05")
                            .startTime("00:00")
                            .endTime("08:00")
                            .eventText("予約不可: " + facilityId)
                            .build()
            );

            return ResponseEntity.ok(schedules);
        } catch (Exception e) {
            logger.error("機器予約情報取得エラー", e);
            return ResponseEntity.badRequest().body(null);
        }
    }
    /**
     * 設備予約情報取得 API
     * @param facilityId 設備ID
     * @param startDate 開始日
     * @param endDate 終了日
     * @return 予約情報リスト
     */
    @GetMapping("/facility/{facilityId}/reservations")
    @ResponseBody
    public ResponseEntity<List<ReservationDTO>> getFacilityReservations(
            @PathVariable String facilityId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {

        logger.info("設備予約情報取得 API - facilityId: {}, 期間: {} から {}", facilityId, startDate, endDate);
        try {
            // 日付のバリデーション

            // facilityRentalServiceで予約情報を取得
            List<ReservationDTO> schedules = facilityRentalService.getReservations(
                    Long.parseLong(facilityId), startDate, endDate);

            return ResponseEntity.ok(schedules);
        } catch (Exception e) {
            logger.error("設備予約情報取得エラー", e);
            return ResponseEntity.badRequest().body(null);
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
                    .rentalList(facilityRentalService.checkin(counterId,userId))
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
                    .rentalList(facilityRentalService.checkin(counterId,userId))
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

    /**
     * 統合予約エンドポイント - 設備と備品の両方を予約
     */
    @PostMapping("/community/reservation/new")
    @ResponseBody
    public ResponseEntity<?> makeCombinedReservation(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody CombinedReservationRequest request) {
        logger.info("統合予約 API");
        try {
            String userId = authService.checkAuthHeader(authHeader);
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Authorization failed"));
            }

            reservationService.reserveCombined(request, userId);

            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            logger.error("統合予約エラー", e);
            return ResponseEntity.badRequest().body(Map.of("error", "Reservation failed: " + e.getMessage()));
        }
    }
}
