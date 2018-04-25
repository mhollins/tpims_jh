package com.ngc.ts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ngc.ts.domain.County;

import com.ngc.ts.repository.CountyRepository;
import com.ngc.ts.repository.search.CountySearchRepository;
import com.ngc.ts.web.rest.errors.BadRequestAlertException;
import com.ngc.ts.web.rest.util.HeaderUtil;
import com.ngc.ts.web.rest.util.PaginationUtil;
import com.ngc.ts.service.dto.CountyDTO;
import com.ngc.ts.service.mapper.CountyMapper;
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
 * REST controller for managing County.
 */
@RestController
@RequestMapping("/api")
public class CountyResource {

    private final Logger log = LoggerFactory.getLogger(CountyResource.class);

    private static final String ENTITY_NAME = "county";

    private final CountyRepository countyRepository;

    private final CountyMapper countyMapper;

    private final CountySearchRepository countySearchRepository;

    public CountyResource(CountyRepository countyRepository, CountyMapper countyMapper, CountySearchRepository countySearchRepository) {
        this.countyRepository = countyRepository;
        this.countyMapper = countyMapper;
        this.countySearchRepository = countySearchRepository;
    }

    /**
     * POST  /counties : Create a new county.
     *
     * @param countyDTO the countyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new countyDTO, or with status 400 (Bad Request) if the county has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/counties")
    @Timed
    public ResponseEntity<CountyDTO> createCounty(@RequestBody CountyDTO countyDTO) throws URISyntaxException {
        log.debug("REST request to save County : {}", countyDTO);
        if (countyDTO.getId() != null) {
            throw new BadRequestAlertException("A new county cannot already have an ID", ENTITY_NAME, "idexists");
        }
        County county = countyMapper.toEntity(countyDTO);
        county = countyRepository.save(county);
        CountyDTO result = countyMapper.toDto(county);
        countySearchRepository.save(county);
        return ResponseEntity.created(new URI("/api/counties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /counties : Updates an existing county.
     *
     * @param countyDTO the countyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated countyDTO,
     * or with status 400 (Bad Request) if the countyDTO is not valid,
     * or with status 500 (Internal Server Error) if the countyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/counties")
    @Timed
    public ResponseEntity<CountyDTO> updateCounty(@RequestBody CountyDTO countyDTO) throws URISyntaxException {
        log.debug("REST request to update County : {}", countyDTO);
        if (countyDTO.getId() == null) {
            return createCounty(countyDTO);
        }
        County county = countyMapper.toEntity(countyDTO);
        county = countyRepository.save(county);
        CountyDTO result = countyMapper.toDto(county);
        countySearchRepository.save(county);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, countyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /counties : get all the counties.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of counties in body
     */
    @GetMapping("/counties")
    @Timed
    public ResponseEntity<List<CountyDTO>> getAllCounties(Pageable pageable) {
        log.debug("REST request to get a page of Counties");
        Page<County> page = countyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/counties");
        return new ResponseEntity<>(countyMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /counties/:id : get the "id" county.
     *
     * @param id the id of the countyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the countyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/counties/{id}")
    @Timed
    public ResponseEntity<CountyDTO> getCounty(@PathVariable Long id) {
        log.debug("REST request to get County : {}", id);
        County county = countyRepository.findOne(id);
        CountyDTO countyDTO = countyMapper.toDto(county);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(countyDTO));
    }

    /**
     * DELETE  /counties/:id : delete the "id" county.
     *
     * @param id the id of the countyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/counties/{id}")
    @Timed
    public ResponseEntity<Void> deleteCounty(@PathVariable Long id) {
        log.debug("REST request to delete County : {}", id);
        countyRepository.delete(id);
        countySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/counties?query=:query : search for the county corresponding
     * to the query.
     *
     * @param query the query of the county search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/counties")
    @Timed
    public ResponseEntity<List<CountyDTO>> searchCounties(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Counties for query {}", query);
        Page<County> page = countySearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/counties");
        return new ResponseEntity<>(countyMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

}
