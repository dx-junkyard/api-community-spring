package com.dxjunkyard.community.repository.dao.mapper;

import com.dxjunkyard.community.domain.EquipmentRental;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface EquipmentMapper {
    void reserve(EquipmentRental rental);
    
    /**
     * 指定された設備IDと日付範囲に基づいて予約情報を取得する
     * @param equipmentId 設備ID
     * @param startDate 開始日時
     * @param endDate 終了日時
     * @return 予約情報のリスト
     */
    List<EquipmentRental> findReservationsByEquipmentIdAndDateRange(Long equipmentId, LocalDateTime startDate, LocalDateTime endDate);
}
