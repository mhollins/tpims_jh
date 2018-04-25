package com.ngc.ts.service.mapper;

import com.ngc.ts.domain.*;
import com.ngc.ts.service.dto.LocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Location and its DTO LocationDTO.
 */
@Mapper(componentModel = "spring", uses = {CountyMapper.class})
public interface LocationMapper extends EntityMapper<LocationDTO, Location> {

    @Mapping(source = "county.id", target = "countyId")
    LocationDTO toDto(Location location);

    @Mapping(source = "countyId", target = "county")
    Location toEntity(LocationDTO locationDTO);

    default Location fromId(Long id) {
        if (id == null) {
            return null;
        }
        Location location = new Location();
        location.setId(id);
        return location;
    }
}
