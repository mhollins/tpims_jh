package com.ngc.ts.service.mapper;

import com.ngc.ts.domain.*;
import com.ngc.ts.service.dto.LogosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Logos and its DTO LogosDTO.
 */
@Mapper(componentModel = "spring", uses = {SiteMapper.class})
public interface LogosMapper extends EntityMapper<LogosDTO, Logos> {

    @Mapping(source = "site.id", target = "siteId")
    LogosDTO toDto(Logos logos);

    @Mapping(source = "siteId", target = "site")
    Logos toEntity(LogosDTO logosDTO);

    default Logos fromId(Long id) {
        if (id == null) {
            return null;
        }
        Logos logos = new Logos();
        logos.setId(id);
        return logos;
    }
}
