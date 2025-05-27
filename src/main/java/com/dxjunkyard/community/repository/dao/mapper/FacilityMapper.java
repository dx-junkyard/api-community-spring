package com.dxjunkyard.community.repository.dao.mapper;

import com.dxjunkyard.community.domain.FacilityRental;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FacilityMapper {
    void reserve(FacilityRental rental);
}
