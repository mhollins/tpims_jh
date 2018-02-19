package com.ngc.ts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ngc.ts.domain.Amenities;

import com.ngc.ts.repository.AmenitiesRepository;
import com.ngc.ts.repository.search.AmenitiesSearchRepository;
import com.ngc.ts.web.rest.errors.BadRequestAlertException;
import com.ngc.ts.web.rest.util.HeaderUtil;
import com.ngc.ts.web.rest.util.PaginationUtil;
import com.ngc.ts.service.dto.AmenitiesDTO;
import com.ngc.ts.service.mapper.AmenitiesMapper;
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
 * REST controller for managing Amenities.
 */
@RestController
@RequestMapping("/api")
public class AmenitiesResource {

    private final Logger log = LoggerFactory.getLogger(AmenitiesResource.class);

    private static final String ENTITY_NAME = "amenities";

    private final AmenitiesRepository amenitiesRepository;

    private final AmenitiesMapper amenitiesMapper;

    private final AmenitiesSearchRepository amenitiesSearchRepository;

    public AmenitiesResource(AmenitiesRepository amenitiesRepository, AmenitiesMapper amenitiesMapper, AmenitiesSearchRepository amenitiesSearchRepository) {
        this.amenitiesRepository = amenitiesRepository;
        this.amenitiesMapper = amenitiesMapper;
        this.amenitiesSearchRepository = amenitiesSearchRepository;
    }

    /**
     * POST  /amenities : Create a new amenities.
     *
     * @param amenitiesDTO the amenitiesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new amenitiesDTO, or with status 400 (Bad Request) if the amenities has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/amenities")
    @Timed
    public ResponseEntity<AmenitiesDTO> createAmenities(@RequestBody AmenitiesDTO amenitiesDTO) throws URISyntaxException {
        log.debug("REST request to save Amenities : {}", amenitiesDTO);
        if (amenitiesDTO.getId() != null) {
            throw new BadRequestAlertException("A new amenities cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Amenities amenities = amenitiesMapper.toEntity(amenitiesDTO);
        amenities = amenitiesRepository.save(amenities);
        AmenitiesDTO result = amenitiesMapper.toDto(amenities);
        amenitiesSearchRepository.save(amenities);
        return ResponseEntity.created(new URI("/api/amenities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /amenities : Updates an existing amenities.
     *
     * @param amenitiesDTO the amenitiesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated amenitiesDTO,
     * or with status 400 (Bad Request) if the amenitiesDTO is not valid,
     * or with status 500 (Internal Server Error) if the amenitiesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/amenities")
    @Timed
    public ResponseEntity<AmenitiesDTO> updateAmenities(@RequestBody AmenitiesDTO amenitiesDTO) throws URISyntaxException {
        log.debug("REST request to update Amenities : {}", amenitiesDTO);
        if (amenitiesDTO.getId() == null) {
            return createAmenities(amenitiesDTO);
        }
        Amenities amenities = amenitiesMapper.toEntity(amenitiesDTO);
        amenities = amenitiesRepository.save(amenities);
        AmenitiesDTO result = amenitiesMapper.toDto(amenities);
        amenitiesSearchRepository.save(amenities);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, amenitiesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /amenities : get all the amenities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of amenities in body
     */
    @GetMapping("/amenities")
    @Timed
    public ResponseEntity<List<AmenitiesDTO>> getAllAmenities(Pageable pageable) {
        log.debug("REST request to get a page of Amenities");
        Page<Amenities> page = amenitiesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/amenities");
        return new ResponseEntity<>(amenitiesMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /amenities/:id : get the "id" amenities.
     *
     * @param id the id of the amenitiesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the amenitiesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/amenities/{id}")
    @Timed
    public ResponseEntity<AmenitiesDTO> getAmenities(@PathVariable Long id) {
        log.debug("REST request to get Amenities : {}", id);
        Amenities amenities = amenitiesRepository.findOne(id);
        AmenitiesDTO amenitiesDTO = amenitiesMapper.toDto(amenities);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(amenitiesDTO));
    }

    /**
     * DELETE  /amenities/:id : delete the "id" amenities.
     *
     * @param id the id of the amenitiesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/amenities/{id}")
    @Timed
    public ResponseEntity<Void> deleteAmenities(@PathVariable Long id) {
        log.debug("REST request to delete Amenities : {}", id);
        amenitiesRepository.delete(id);
        amenitiesSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/amenities?query=:query : search for the amenities corresponding
     * to the query.
     *
     * @param query the query of the amenities search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/amenities")
    @Timed
    public ResponseEntity<List<AmenitiesDTO>> searchAmenities(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Amenities for query {}", query);
        Page<Amenities> page = amenitiesSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/amenities");
        return new ResponseEntity<>(amenitiesMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

}
