package com.ngc.ts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ngc.ts.domain.District;

import com.ngc.ts.repository.DistrictRepository;
import com.ngc.ts.repository.search.DistrictSearchRepository;
import com.ngc.ts.web.rest.errors.BadRequestAlertException;
import com.ngc.ts.web.rest.util.HeaderUtil;
import com.ngc.ts.web.rest.util.PaginationUtil;
import com.ngc.ts.service.dto.DistrictDTO;
import com.ngc.ts.service.mapper.DistrictMapper;
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
 * REST controller for managing District.
 */
@RestController
@RequestMapping("/api")
public class DistrictResource {

    private final Logger log = LoggerFactory.getLogger(DistrictResource.class);

    private static final String ENTITY_NAME = "district";

    private final DistrictRepository districtRepository;

    private final DistrictMapper districtMapper;

    private final DistrictSearchRepository districtSearchRepository;

    public DistrictResource(DistrictRepository districtRepository, DistrictMapper districtMapper, DistrictSearchRepository districtSearchRepository) {
        this.districtRepository = districtRepository;
        this.districtMapper = districtMapper;
        this.districtSearchRepository = districtSearchRepository;
    }

    /**
     * POST  /districts : Create a new district.
     *
     * @param districtDTO the districtDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new districtDTO, or with status 400 (Bad Request) if the district has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/districts")
    @Timed
    public ResponseEntity<DistrictDTO> createDistrict(@RequestBody DistrictDTO districtDTO) throws URISyntaxException {
        log.debug("REST request to save District : {}", districtDTO);
        if (districtDTO.getId() != null) {
            throw new BadRequestAlertException("A new district cannot already have an ID", ENTITY_NAME, "idexists");
        }
        District district = districtMapper.toEntity(districtDTO);
        district = districtRepository.save(district);
        DistrictDTO result = districtMapper.toDto(district);
        districtSearchRepository.save(district);
        return ResponseEntity.created(new URI("/api/districts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /districts : Updates an existing district.
     *
     * @param districtDTO the districtDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated districtDTO,
     * or with status 400 (Bad Request) if the districtDTO is not valid,
     * or with status 500 (Internal Server Error) if the districtDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/districts")
    @Timed
    public ResponseEntity<DistrictDTO> updateDistrict(@RequestBody DistrictDTO districtDTO) throws URISyntaxException {
        log.debug("REST request to update District : {}", districtDTO);
        if (districtDTO.getId() == null) {
            return createDistrict(districtDTO);
        }
        District district = districtMapper.toEntity(districtDTO);
        district = districtRepository.save(district);
        DistrictDTO result = districtMapper.toDto(district);
        districtSearchRepository.save(district);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, districtDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /districts : get all the districts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of districts in body
     */
    @GetMapping("/districts")
    @Timed
    public ResponseEntity<List<DistrictDTO>> getAllDistricts(Pageable pageable) {
        log.debug("REST request to get a page of Districts");
        Page<District> page = districtRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/districts");
        return new ResponseEntity<>(districtMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /districts/:id : get the "id" district.
     *
     * @param id the id of the districtDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the districtDTO, or with status 404 (Not Found)
     */
    @GetMapping("/districts/{id}")
    @Timed
    public ResponseEntity<DistrictDTO> getDistrict(@PathVariable Long id) {
        log.debug("REST request to get District : {}", id);
        District district = districtRepository.findOne(id);
        DistrictDTO districtDTO = districtMapper.toDto(district);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(districtDTO));
    }

    /**
     * DELETE  /districts/:id : delete the "id" district.
     *
     * @param id the id of the districtDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/districts/{id}")
    @Timed
    public ResponseEntity<Void> deleteDistrict(@PathVariable Long id) {
        log.debug("REST request to delete District : {}", id);
        districtRepository.delete(id);
        districtSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/districts?query=:query : search for the district corresponding
     * to the query.
     *
     * @param query the query of the district search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/districts")
    @Timed
    public ResponseEntity<List<DistrictDTO>> searchDistricts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Districts for query {}", query);
        Page<District> page = districtSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/districts");
        return new ResponseEntity<>(districtMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

}
