package com.dxjunkyard.community.repository.dao.mapper;

import com.dxjunkyard.community.domain.FacilityRental;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface FacilityMapper {
    void reserve(FacilityRental rental);
    
    /**
     * 指定された施設IDと日付範囲に基づいて予約情報を取得する
     * @param facilityId 施設ID
     * @param startDate 開始日時
     * @param endDate 終了日時
     * @return 予約情報のリスト
     */
    List<FacilityRental> findReservationsByFacilityIdAndDateRange(Long facilityId, LocalDateTime startDate, LocalDateTime endDate);
}
