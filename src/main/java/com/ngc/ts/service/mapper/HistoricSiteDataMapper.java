package com.ngc.ts.service.mapper;

import com.ngc.ts.domain.*;
import com.ngc.ts.service.dto.HistoricSiteDataDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity HistoricSiteData and its DTO HistoricSiteDataDTO.
 */
@Mapper(componentModel = "spring", uses = {SiteMapper.class})
public interface HistoricSiteDataMapper extends EntityMapper<HistoricSiteDataDTO, HistoricSiteData> {

    @Mapping(source = "site.id", target = "siteId")
    HistoricSiteDataDTO toDto(HistoricSiteData historicSiteData);

    @Mapping(source = "siteId", target = "site")
    HistoricSiteData toEntity(HistoricSiteDataDTO historicSiteDataDTO);

    default HistoricSiteData fromId(Long id) {
        if (id == null) {
            return null;
        }
        HistoricSiteData historicSiteData = new HistoricSiteData();
        historicSiteData.setId(id);
        return historicSiteData;
    }
}
