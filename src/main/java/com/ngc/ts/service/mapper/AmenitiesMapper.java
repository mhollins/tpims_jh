package com.ngc.ts.service.mapper;

import com.ngc.ts.domain.*;
import com.ngc.ts.service.dto.AmenitiesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Amenities and its DTO AmenitiesDTO.
 */
@Mapper(componentModel = "spring", uses = {SiteMapper.class})
public interface AmenitiesMapper extends EntityMapper<AmenitiesDTO, Amenities> {

    @Mapping(source = "site.id", target = "siteId")
    AmenitiesDTO toDto(Amenities amenities);

    @Mapping(source = "siteId", target = "site")
    Amenities toEntity(AmenitiesDTO amenitiesDTO);

    default Amenities fromId(Long id) {
        if (id == null) {
            return null;
        }
        Amenities amenities = new Amenities();
        amenities.setId(id);
        return amenities;
    }
}
