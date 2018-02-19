package com.ngc.ts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ngc.ts.domain.Logos;

import com.ngc.ts.repository.LogosRepository;
import com.ngc.ts.repository.search.LogosSearchRepository;
import com.ngc.ts.web.rest.errors.BadRequestAlertException;
import com.ngc.ts.web.rest.util.HeaderUtil;
import com.ngc.ts.web.rest.util.PaginationUtil;
import com.ngc.ts.service.dto.LogosDTO;
import com.ngc.ts.service.mapper.LogosMapper;
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
 * REST controller for managing Logos.
 */
@RestController
@RequestMapping("/api")
public class LogosResource {

    private final Logger log = LoggerFactory.getLogger(LogosResource.class);

    private static final String ENTITY_NAME = "logos";

    private final LogosRepository logosRepository;

    private final LogosMapper logosMapper;

    private final LogosSearchRepository logosSearchRepository;

    public LogosResource(LogosRepository logosRepository, LogosMapper logosMapper, LogosSearchRepository logosSearchRepository) {
        this.logosRepository = logosRepository;
        this.logosMapper = logosMapper;
        this.logosSearchRepository = logosSearchRepository;
    }

    /**
     * POST  /logos : Create a new logos.
     *
     * @param logosDTO the logosDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new logosDTO, or with status 400 (Bad Request) if the logos has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/logos")
    @Timed
    public ResponseEntity<LogosDTO> createLogos(@RequestBody LogosDTO logosDTO) throws URISyntaxException {
        log.debug("REST request to save Logos : {}", logosDTO);
        if (logosDTO.getId() != null) {
            throw new BadRequestAlertException("A new logos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Logos logos = logosMapper.toEntity(logosDTO);
        logos = logosRepository.save(logos);
        LogosDTO result = logosMapper.toDto(logos);
        logosSearchRepository.save(logos);
        return ResponseEntity.created(new URI("/api/logos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /logos : Updates an existing logos.
     *
     * @param logosDTO the logosDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated logosDTO,
     * or with status 400 (Bad Request) if the logosDTO is not valid,
     * or with status 500 (Internal Server Error) if the logosDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/logos")
    @Timed
    public ResponseEntity<LogosDTO> updateLogos(@RequestBody LogosDTO logosDTO) throws URISyntaxException {
        log.debug("REST request to update Logos : {}", logosDTO);
        if (logosDTO.getId() == null) {
            return createLogos(logosDTO);
        }
        Logos logos = logosMapper.toEntity(logosDTO);
        logos = logosRepository.save(logos);
        LogosDTO result = logosMapper.toDto(logos);
        logosSearchRepository.save(logos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, logosDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /logos : get all the logos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of logos in body
     */
    @GetMapping("/logos")
    @Timed
    public ResponseEntity<List<LogosDTO>> getAllLogos(Pageable pageable) {
        log.debug("REST request to get a page of Logos");
        Page<Logos> page = logosRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/logos");
        return new ResponseEntity<>(logosMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /logos/:id : get the "id" logos.
     *
     * @param id the id of the logosDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the logosDTO, or with status 404 (Not Found)
     */
    @GetMapping("/logos/{id}")
    @Timed
    public ResponseEntity<LogosDTO> getLogos(@PathVariable Long id) {
        log.debug("REST request to get Logos : {}", id);
        Logos logos = logosRepository.findOne(id);
        LogosDTO logosDTO = logosMapper.toDto(logos);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(logosDTO));
    }

    /**
     * DELETE  /logos/:id : delete the "id" logos.
     *
     * @param id the id of the logosDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/logos/{id}")
    @Timed
    public ResponseEntity<Void> deleteLogos(@PathVariable Long id) {
        log.debug("REST request to delete Logos : {}", id);
        logosRepository.delete(id);
        logosSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/logos?query=:query : search for the logos corresponding
     * to the query.
     *
     * @param query the query of the logos search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/logos")
    @Timed
    public ResponseEntity<List<LogosDTO>> searchLogos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Logos for query {}", query);
        Page<Logos> page = logosSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/logos");
        return new ResponseEntity<>(logosMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

}
