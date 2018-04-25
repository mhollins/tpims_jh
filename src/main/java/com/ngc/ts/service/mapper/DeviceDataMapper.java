package com.ngc.ts.service.mapper;

import com.ngc.ts.domain.*;
import com.ngc.ts.service.dto.DeviceDataDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DeviceData and its DTO DeviceDataDTO.
 */
@Mapper(componentModel = "spring", uses = {DeviceMapper.class})
public interface DeviceDataMapper extends EntityMapper<DeviceDataDTO, DeviceData> {

    @Mapping(source = "device.id", target = "deviceId")
    DeviceDataDTO toDto(DeviceData deviceData);

    @Mapping(source = "deviceId", target = "device")
    DeviceData toEntity(DeviceDataDTO deviceDataDTO);

    default DeviceData fromId(Long id) {
        if (id == null) {
            return null;
        }
        DeviceData deviceData = new DeviceData();
        deviceData.setId(id);
        return deviceData;
    }
}
