package com.dxjunkyard.community.domain.request;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacilityReservationRequest {
    private Integer facilityId;  // 借りる設備id
    private Integer eventId;  // 利用目的のイベントid
    private String renterId; // 借り主の user id
    private String startDate; // 貸出開始
    private String endDate; //　貸出終了
    private String comment; // コメント
}
