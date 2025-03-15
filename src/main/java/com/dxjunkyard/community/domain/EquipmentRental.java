package com.dxjunkyard.community.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentRental {
    private Long reservationId;
    private Long equipmentId;
    private Long eventId;
    private String renterId;
    private Integer equipmentN;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime usageDate;
    private String comment;
    private Integer status;
}
