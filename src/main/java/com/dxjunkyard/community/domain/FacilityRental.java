package com.dxjunkyard.community.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacilityRental {
    private Integer reservationId; // 予約id
    private Integer facilityId; // 備品id
    private Integer eventId;  // 利用目的のイベントid
    private String renterId; // 借り主の user id
    private LocalDateTime startDate; // 貸出開始
    private LocalDateTime endDate; //　貸出終了
    private String comment; // コメント
}
