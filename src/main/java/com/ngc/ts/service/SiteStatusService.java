package com.ngc.ts.service;

import com.ngc.ts.service.dto.SiteStatusDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing SiteStatus.
 */
public interface SiteStatusService {

    /**
     * Save a siteStatus.
     *
     * @param siteStatusDTO the entity to save
     * @return the persisted entity
     */
    SiteStatusDTO save(SiteStatusDTO siteStatusDTO);

    /**
     * Get all the siteStatuses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SiteStatusDTO> findAll(Pageable pageable);

    /**
     * Get the "id" siteStatus.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SiteStatusDTO findOne(Long id);

    /**
     * Delete the "id" siteStatus.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the siteStatus corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SiteStatusDTO> search(String query, Pageable pageable);
}
