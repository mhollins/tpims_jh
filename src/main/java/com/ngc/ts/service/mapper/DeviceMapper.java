package com.ngc.ts.service.mapper;

import com.ngc.ts.domain.*;
import com.ngc.ts.service.dto.DeviceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Device and its DTO DeviceDTO.
 */
@Mapper(componentModel = "spring", uses = {SiteMapper.class})
public interface DeviceMapper extends EntityMapper<DeviceDTO, Device> {

    @Mapping(source = "site.id", target = "siteId")
    DeviceDTO toDto(Device device);

    @Mapping(source = "siteId", target = "site")
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
