package com.ngc.ts.service.impl;

import com.ngc.ts.domain.HistoricSiteData;
import com.ngc.ts.domain.Site;
import com.ngc.ts.repository.HistoricSiteDataRepository;
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

    private final HistoricSiteDataRepository historicSiteDataRepository;

    public SiteStatusServiceImpl(SiteStatusRepository siteStatusRepository, SiteRepository siteRepository, SiteStatusMapper siteStatusMapper, SiteStatusSearchRepository siteStatusSearchRepository, HistoricSiteDataRepository historicSiteDataRepository) {
        this.siteStatusRepository = siteStatusRepository;
        this.siteRepository = siteRepository;
        this.siteStatusMapper = siteStatusMapper;
        this.siteStatusSearchRepository = siteStatusSearchRepository;
        this.historicSiteDataRepository = historicSiteDataRepository;
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

        SiteStatus siteStatus = checkValues(siteStatusDTO);

        return saveSiteStatus(siteStatus);
    }

    private SiteStatusDTO saveSiteStatus(SiteStatus siteStatus) {
        siteStatus = siteStatusRepository.save(siteStatus);
        SiteStatusDTO result = siteStatusMapper.toDto(siteStatus);
        siteStatusSearchRepository.save(siteStatus);
        return result;
    }

    private SiteStatus checkValues(SiteStatusDTO siteStatusDTO) {
        SiteStatus siteStatus = siteStatusMapper.toEntity(siteStatusDTO);

        if (0 > siteStatus.getReportedAvailable()) {
            siteStatus.setReportedAvailable(0);
        }

        if (siteStatusDTO.getSiteId() != null) {
            Site theSite = siteRepository.findOne(siteStatusDTO.getSiteId());
            siteStatus.setSite(theSite);
            if (siteStatus.getReportedAvailable() > theSite.getTotalCapacity()) {
                siteStatus.setReportedAvailable(theSite.getTotalCapacity());
            }

            // cap total vehicles to 150% of capacity
            if (theSite.getTotalCapacity() * 1.5 < siteStatus.getVehiclesCounted()) {
                siteStatus.setVehiclesCounted((theSite.getTotalCapacity() * 150) / 100);
            }
        }

        if (0 > siteStatus.getVehiclesCounted()) {
            siteStatus.setVehiclesCounted(0);
        }

        return siteStatus;
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

        SiteStatus siteStatus = checkValues(siteStatusDTO);

        // set operator update time
        siteStatus.setLastOperatorUpdate(Instant.now());

        //** calculate verification amplitude
        SiteStatus previous = siteStatusRepository.findOne(siteStatus.getId());
        if (previous != null) {
            siteStatus.setVerificationCheckAmplitude(siteStatus.getReportedAvailable() - previous.getReportedAvailable());
            log.debug("Verification amplitude: " + siteStatus.getVerificationCheckAmplitude());
        } else {
            siteStatus.setVerificationCheckAmplitude(siteStatus.getReportedAvailable());
        }

        HistoricSiteData historicSiteData = new HistoricSiteData()
            .totalCapacity(siteStatus.getSite().getTotalCapacity())
            .availability(siteStatus.getReportedAvailable())
            .trend(siteStatus.getTrend())
            .open(siteStatus.isOpen())
            .trustData(siteStatus.isTrustData())
            .timeStamp(siteStatus.getLastOperatorUpdate())
            .verificationCheck(true)
            .trueAvailable(siteStatusDTO.getReportedAvailable())
            .site(siteStatus.getSite());
        historicSiteDataRepository.save(historicSiteData);


        return this.saveSiteStatus(siteStatus);
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
