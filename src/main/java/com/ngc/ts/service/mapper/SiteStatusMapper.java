package com.ngc.ts.service.mapper;

import com.ngc.ts.domain.*;
import com.ngc.ts.service.dto.SiteStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SiteStatus and its DTO SiteStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {SiteMapper.class})
public interface SiteStatusMapper extends EntityMapper<SiteStatusDTO, SiteStatus> {

    @Mapping(source = "site.id", target = "siteId")
    SiteStatusDTO toDto(SiteStatus siteStatus);

    @Mapping(source = "siteId", target = "site")
    SiteStatus toEntity(SiteStatusDTO siteStatusDTO);

    default SiteStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        SiteStatus siteStatus = new SiteStatus();
        siteStatus.setId(id);
        return siteStatus;
    }
}
