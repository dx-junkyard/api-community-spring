package com.dxjunkyard.community.repository.dao.mapper;

import com.dxjunkyard.community.domain.EquipmentRental;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EquipmentMapper {
    void reserve(EquipmentRental rental);
}
