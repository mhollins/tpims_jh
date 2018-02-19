package com.ngc.ts.service.impl;

import com.ngc.ts.service.SiteStatusService;
import com.ngc.ts.domain.SiteStatus;
import com.ngc.ts.repository.SiteStatusRepository;
import com.ngc.ts.repository.search.SiteStatusSearchRepository;
import com.ngc.ts.service.dto.SiteStatusDTO;
import com.ngc.ts.service.mapper.SiteStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SiteStatus.
 */
@Service
@Transactional
public class SiteStatusServiceImpl implements SiteStatusService {

    private final Logger log = LoggerFactory.getLogger(SiteStatusServiceImpl.class);

    private final SiteStatusRepository siteStatusRepository;

    private final SiteStatusMapper siteStatusMapper;

    private final SiteStatusSearchRepository siteStatusSearchRepository;

    public SiteStatusServiceImpl(SiteStatusRepository siteStatusRepository, SiteStatusMapper siteStatusMapper, SiteStatusSearchRepository siteStatusSearchRepository) {
        this.siteStatusRepository = siteStatusRepository;
        this.siteStatusMapper = siteStatusMapper;
        this.siteStatusSearchRepository = siteStatusSearchRepository;
    }

    /**
     * Save a siteStatus.
     *
     * @param siteStatusDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SiteStatusDTO save(SiteStatusDTO siteStatusDTO) {
        log.debug("Request to save SiteStatus : {}", siteStatusDTO);
        SiteStatus siteStatus = siteStatusMapper.toEntity(siteStatusDTO);
        siteStatus = siteStatusRepository.save(siteStatus);
        SiteStatusDTO result = siteStatusMapper.toDto(siteStatus);
        siteStatusSearchRepository.save(siteStatus);
        return result;
    }

    /**
     * Get all the siteStatuses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SiteStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SiteStatuses");
        return siteStatusRepository.findAll(pageable)
            .map(siteStatusMapper::toDto);
    }

    /**
     * Get one siteStatus by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SiteStatusDTO findOne(Long id) {
        log.debug("Request to get SiteStatus : {}", id);
        SiteStatus siteStatus = siteStatusRepository.findOne(id);
        return siteStatusMapper.toDto(siteStatus);
    }

    /**
     * Delete the siteStatus by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SiteStatus : {}", id);
        siteStatusRepository.delete(id);
        siteStatusSearchRepository.delete(id);
    }

    /**
     * Search for the siteStatus corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SiteStatusDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SiteStatuses for query {}", query);
        Page<SiteStatus> result = siteStatusSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(siteStatusMapper::toDto);
    }
}
