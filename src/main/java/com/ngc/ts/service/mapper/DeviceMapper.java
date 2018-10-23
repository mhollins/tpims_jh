package com.ngc.ts.service.mapper;

import com.ngc.ts.domain.*;
import com.ngc.ts.service.dto.DeviceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Device and its DTO DeviceDTO.
 */
@Mapper(componentModel = "spring", uses = {SiteMapper.class, LocationMapper.class})
public interface DeviceMapper extends EntityMapper<DeviceDTO, Device> {

    @Mapping(source = "site.id", target = "siteId")
    @Mapping(source = "site.siteName", target = "siteName")
    @Mapping(source = "location.id", target = "locationId")
    DeviceDTO toDto(Device device);

    @Mapping(source = "siteId", target = "site")
    @Mapping(source = "locationId", target = "location")
    Device toEntity(DeviceDTO deviceDTO);

    default Device fromId(Long id) {
        if (id == null) {
            return null;
        }
        Device device = new Device();
        device.setId(id);
        return device;
    }
}
