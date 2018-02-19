package com.ngc.ts.service.mapper;

import com.ngc.ts.domain.*;
import com.ngc.ts.service.dto.ImagesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Images and its DTO ImagesDTO.
 */
@Mapper(componentModel = "spring", uses = {SiteMapper.class})
public interface ImagesMapper extends EntityMapper<ImagesDTO, Images> {

    @Mapping(source = "site.id", target = "siteId")
    ImagesDTO toDto(Images images);

    @Mapping(source = "siteId", target = "site")
    Images toEntity(ImagesDTO imagesDTO);

    default Images fromId(Long id) {
        if (id == null) {
            return null;
        }
        Images images = new Images();
        images.setId(id);
        return images;
    }
}
