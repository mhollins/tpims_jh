package com.ngc.ts.service.impl;

import com.ngc.ts.domain.Site;
import com.ngc.ts.repository.SiteRepository;
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


import java.time.Instant;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SiteStatus.
 */
@Service
@Transactional
public class SiteStatusServiceImpl implements SiteStatusService {

    private final Logger log = LoggerFactory.getLogger(SiteStatusServiceImpl.class);

    private final SiteStatusRepository siteStatusRepository;

    private final SiteRepository siteRepository;

    private final SiteStatusMapper siteStatusMapper;

    private final SiteStatusSearchRepository siteStatusSearchRepository;

    public SiteStatusServiceImpl(SiteStatusRepository siteStatusRepository, SiteRepository siteRepository, SiteStatusMapper siteStatusMapper, SiteStatusSearchRepository siteStatusSearchRepository) {
        this.siteStatusRepository = siteStatusRepository;
        this.siteRepository = siteRepository;
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

        Site theSite = siteRepository.findOne(siteStatusDTO.getSiteId());

        if (siteStatus.getReportedAvailable() < 0) {
            siteStatus.setReportedAvailable(0);
        }
        if (siteStatus.getReportedAvailable() > theSite.getTotalCapacity()) {
            siteStatus.setReportedAvailable(theSite.getTotalCapacity());
        }
        if (siteStatus.getVehiclesCounted() < 0) {
            siteStatus.setVehiclesCounted(0);
        }

        siteStatus = siteStatusRepository.save(siteStatus);
        SiteStatusDTO result = siteStatusMapper.toDto(siteStatus);
        siteStatusSearchRepository.save(siteStatus);
        return result;
    }

    /**
     * Save a siteStatus.
     *
     * @param siteStatusDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SiteStatusDTO update(SiteStatusDTO siteStatusDTO) {
        log.debug("Request to update SiteStatus : {}", siteStatusDTO);
        //SiteStatus siteStatus = siteStatusMapper.toEntity(siteStatusDTO);

        // set operator update time
        siteStatusDTO.setLastOperatorUpdate(Instant.now());
        //siteStatus.setLastOperatorUpdate(Instant.now());

        //** calculate verification amplitude
        // if the operator provided availability is greater than the total capacity, what do we do?
        // I don't want to add another database query to get capacity, should add capacity to status?

        SiteStatus previous = siteStatusRepository.findOne(siteStatusDTO.getId());
        if (previous != null) {
            siteStatusDTO.setVerificationCheckAmplitude(siteStatusDTO.getReportedAvailable() - previous.getReportedAvailable());
        }

//        siteStatus = siteStatusRepository.save(siteStatus);
//        siteStatusSearchRepository.save(siteStatus);
//
//        SiteStatusDTO result = siteStatusMapper.toDto(siteStatus);
        return this.save(siteStatusDTO);
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
     * Get one siteStatus by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SiteStatusDTO findOneBySiteId(Long id) {
        log.debug("Request to get SiteStatus by SiteId : {}", id);
        SiteStatus siteStatus = siteStatusRepository.findBySiteId(id);
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
