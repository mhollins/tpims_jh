package com.ngc.ts.service.mapper;

import com.ngc.ts.domain.*;
import com.ngc.ts.service.dto.CountyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity County and its DTO CountyDTO.
 */
@Mapper(componentModel = "spring", uses = {DistrictMapper.class})
public interface CountyMapper extends EntityMapper<CountyDTO, County> {

    @Mapping(source = "district.id", target = "districtId")
    CountyDTO toDto(County county);

    @Mapping(source = "districtId", target = "district")
    County toEntity(CountyDTO countyDTO);

    default County fromId(Long id) {
        if (id == null) {
            return null;
        }
        County county = new County();
        county.setId(id);
        return county;
    }
}
