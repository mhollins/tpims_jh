package com.ngc.ts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ngc.ts.service.SiteStatusService;
import com.ngc.ts.web.rest.errors.BadRequestAlertException;
import com.ngc.ts.web.rest.util.HeaderUtil;
import com.ngc.ts.web.rest.util.PaginationUtil;
import com.ngc.ts.service.dto.SiteStatusDTO;
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
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing SiteStatus.
 */
@RestController
@RequestMapping("/api")
public class SiteStatusResource {

    private final Logger log = LoggerFactory.getLogger(SiteStatusResource.class);

    private static final String ENTITY_NAME = "siteStatus";

    private final SiteStatusService siteStatusService;

    public SiteStatusResource(SiteStatusService siteStatusService) {
        this.siteStatusService = siteStatusService;
    }

    /**
     * POST  /site-statuses : Create a new siteStatus.
     *
     * @param siteStatusDTO the siteStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new siteStatusDTO, or with status 400 (Bad Request) if the siteStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/site-statuses")
    @Timed
    public ResponseEntity<SiteStatusDTO> createSiteStatus(@RequestBody SiteStatusDTO siteStatusDTO) throws URISyntaxException {
        log.debug("REST request to save SiteStatus : {}", siteStatusDTO);
        if (siteStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new siteStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SiteStatusDTO result = siteStatusService.save(siteStatusDTO);
        return ResponseEntity.created(new URI("/api/site-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /site-statuses : Updates an existing siteStatus.
     *
     * @param siteStatusDTO the siteStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated siteStatusDTO,
     * or with status 400 (Bad Request) if the siteStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the siteStatusDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/site-statuses")
    @Timed
    public ResponseEntity<SiteStatusDTO> updateSiteStatus(@RequestBody SiteStatusDTO siteStatusDTO) throws URISyntaxException {
        log.debug("REST request to update SiteStatus : {}", siteStatusDTO);
        if (siteStatusDTO.getId() == null) {
            return createSiteStatus(siteStatusDTO);
        }
        SiteStatusDTO result = siteStatusService.save(siteStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, siteStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /operator-update : Updates an existing siteStatus.
     *
     * @param siteStatusDTO the siteStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated siteStatusDTO,
     * or with status 400 (Bad Request) if the siteStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the siteStatusDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/operator-update")
    @Timed
    public ResponseEntity<SiteStatusDTO> operatorUpdateSiteStatus(@RequestBody SiteStatusDTO siteStatusDTO) throws URISyntaxException {
        log.debug("REST request to update SiteStatus : {}", siteStatusDTO);
        if (siteStatusDTO.getId() == null) {
            return createSiteStatus(siteStatusDTO);
        }
        SiteStatusDTO result = siteStatusService.update(siteStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, siteStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /site-statuses : get all the siteStatuses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of siteStatuses in body
     */
    @GetMapping("/site-statuses")
    @Timed
    public ResponseEntity<List<SiteStatusDTO>> getAllSiteStatuses(Pageable pageable) {
        log.debug("REST request to get a page of SiteStatuses");
        Page<SiteStatusDTO> page = siteStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/site-statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /site-statuses/:id : get the "id" siteStatus.
     *
     * @param id the id of the siteStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the siteStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/site-statuses/{id}")
    @Timed
    public ResponseEntity<SiteStatusDTO> getSiteStatus(@PathVariable Long id) {
        log.debug("REST request to get SiteStatus : {}", id);
        SiteStatusDTO siteStatusDTO = siteStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(siteStatusDTO));
    }

    /**
     * DELETE  /site-statuses/:id : delete the "id" siteStatus.
     *
     * @param id the id of the siteStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/site-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteSiteStatus(@PathVariable Long id) {
        log.debug("REST request to delete SiteStatus : {}", id);
        siteStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /site-statuses/:id : get the "id" siteStatus.
     *
     * @param id the id of the siteStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the siteStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/site-statuses/bySiteId/{id}")
    @Timed
    public ResponseEntity<SiteStatusDTO> getSiteStatusBySiteId(@PathVariable Long id) {
        log.debug("REST request to get SiteStatus by SiteId: {}", id);
        SiteStatusDTO siteStatusDTO = siteStatusService.findOneBySiteId(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(siteStatusDTO));
    }

    /**
     * SEARCH  /_search/site-statuses?query=:query : search for the siteStatus corresponding
     * to the query.
     *
     * @param query the query of the siteStatus search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/site-statuses")
    @Timed
    public ResponseEntity<List<SiteStatusDTO>> searchSiteStatuses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SiteStatuses for query {}", query);
        Page<SiteStatusDTO> page = siteStatusService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/site-statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
