package com.ngc.ts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ngc.ts.domain.HistoricSiteData;

import com.ngc.ts.repository.HistoricSiteDataRepository;
import com.ngc.ts.repository.search.HistoricSiteDataSearchRepository;
import com.ngc.ts.web.rest.errors.BadRequestAlertException;
import com.ngc.ts.web.rest.util.HeaderUtil;
import com.ngc.ts.web.rest.util.PaginationUtil;
import com.ngc.ts.service.dto.HistoricSiteDataDTO;
import com.ngc.ts.service.mapper.HistoricSiteDataMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing HistoricSiteData.
 */
@RestController
@RequestMapping("/api")
public class HistoricSiteDataResource {

    private final Logger log = LoggerFactory.getLogger(HistoricSiteDataResource.class);

    private static final String ENTITY_NAME = "historicSiteData";

    private final HistoricSiteDataRepository historicSiteDataRepository;

    private final HistoricSiteDataMapper historicSiteDataMapper;

    private final HistoricSiteDataSearchRepository historicSiteDataSearchRepository;

    public HistoricSiteDataResource(HistoricSiteDataRepository historicSiteDataRepository, HistoricSiteDataMapper historicSiteDataMapper, HistoricSiteDataSearchRepository historicSiteDataSearchRepository) {
        this.historicSiteDataRepository = historicSiteDataRepository;
        this.historicSiteDataMapper = historicSiteDataMapper;
        this.historicSiteDataSearchRepository = historicSiteDataSearchRepository;
    }

    /**
     * POST  /historic-site-data : Create a new historicSiteData.
     *
     * @param historicSiteDataDTO the historicSiteDataDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new historicSiteDataDTO, or with status 400 (Bad Request) if the historicSiteData has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/historic-site-data")
    @Timed
    public ResponseEntity<HistoricSiteDataDTO> createHistoricSiteData(@RequestBody HistoricSiteDataDTO historicSiteDataDTO) throws URISyntaxException {
        log.debug("REST request to save HistoricSiteData : {}", historicSiteDataDTO);
        if (historicSiteDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new historicSiteData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HistoricSiteData historicSiteData = historicSiteDataMapper.toEntity(historicSiteDataDTO);
        historicSiteData = historicSiteDataRepository.save(historicSiteData);
        HistoricSiteDataDTO result = historicSiteDataMapper.toDto(historicSiteData);
        historicSiteDataSearchRepository.save(historicSiteData);
        return ResponseEntity.created(new URI("/api/historic-site-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /historic-site-data : Updates an existing historicSiteData.
     *
     * @param historicSiteDataDTO the historicSiteDataDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated historicSiteDataDTO,
     * or with status 400 (Bad Request) if the historicSiteDataDTO is not valid,
     * or with status 500 (Internal Server Error) if the historicSiteDataDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/historic-site-data")
    @Timed
    public ResponseEntity<HistoricSiteDataDTO> updateHistoricSiteData(@RequestBody HistoricSiteDataDTO historicSiteDataDTO) throws URISyntaxException {
        log.debug("REST request to update HistoricSiteData : {}", historicSiteDataDTO);
        if (historicSiteDataDTO.getId() == null) {
            return createHistoricSiteData(historicSiteDataDTO);
        }
        HistoricSiteData historicSiteData = historicSiteDataMapper.toEntity(historicSiteDataDTO);
        historicSiteData = historicSiteDataRepository.save(historicSiteData);
        HistoricSiteDataDTO result = historicSiteDataMapper.toDto(historicSiteData);
        historicSiteDataSearchRepository.save(historicSiteData);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, historicSiteDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /historic-site-data : get all the historicSiteData.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of historicSiteData in body
     */
    @GetMapping("/historic-site-data")
    @Timed
    public ResponseEntity<List<HistoricSiteDataDTO>> getAllHistoricSiteData(Pageable pageable) {
        log.debug("REST request to get a page of HistoricSiteData");
        Page<HistoricSiteData> page = historicSiteDataRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/historic-site-data");
        return new ResponseEntity<>(historicSiteDataMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /historic-site-data/:id : get the "id" historicSiteData.
     *
     * @param id the id of the historicSiteDataDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the historicSiteDataDTO, or with status 404 (Not Found)
     */
    @GetMapping("/historic-site-data/{id}")
    @Timed
    public ResponseEntity<HistoricSiteDataDTO> getHistoricSiteData(@PathVariable Long id) {
        log.debug("REST request to get HistoricSiteData : {}", id);
        HistoricSiteData historicSiteData = historicSiteDataRepository.findOne(id);
        HistoricSiteDataDTO historicSiteDataDTO = historicSiteDataMapper.toDto(historicSiteData);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(historicSiteDataDTO));
    }

    /**
     * DELETE  /historic-site-data/:id : delete the "id" historicSiteData.
     *
     * @param id the id of the historicSiteDataDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/historic-site-data/{id}")
    @Timed
    public ResponseEntity<Void> deleteHistoricSiteData(@PathVariable Long id) {
        log.debug("REST request to delete HistoricSiteData : {}", id);
        historicSiteDataRepository.delete(id);
        historicSiteDataSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/historic-site-data?query=:query : search for the historicSiteData corresponding
     * to the query.
     *
     * @param query the query of the historicSiteData search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/historic-site-data")
    @Timed
    public ResponseEntity<List<HistoricSiteDataDTO>> searchHistoricSiteData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of HistoricSiteData for query {}", query);
        Page<HistoricSiteData> page = historicSiteDataSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/historic-site-data");
        return new ResponseEntity<>(historicSiteDataMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

}
