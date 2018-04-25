package com.ngc.ts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ngc.ts.domain.DeviceData;

import com.ngc.ts.repository.DeviceDataRepository;
import com.ngc.ts.repository.search.DeviceDataSearchRepository;
import com.ngc.ts.web.rest.errors.BadRequestAlertException;
import com.ngc.ts.web.rest.util.HeaderUtil;
import com.ngc.ts.web.rest.util.PaginationUtil;
import com.ngc.ts.service.dto.DeviceDataDTO;
import com.ngc.ts.service.mapper.DeviceDataMapper;
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
 * REST controller for managing DeviceData.
 */
@RestController
@RequestMapping("/api")
public class DeviceDataResource {

    private final Logger log = LoggerFactory.getLogger(DeviceDataResource.class);

    private static final String ENTITY_NAME = "deviceData";

    private final DeviceDataRepository deviceDataRepository;

    private final DeviceDataMapper deviceDataMapper;

    private final DeviceDataSearchRepository deviceDataSearchRepository;

    public DeviceDataResource(DeviceDataRepository deviceDataRepository, DeviceDataMapper deviceDataMapper, DeviceDataSearchRepository deviceDataSearchRepository) {
        this.deviceDataRepository = deviceDataRepository;
        this.deviceDataMapper = deviceDataMapper;
        this.deviceDataSearchRepository = deviceDataSearchRepository;
    }

    /**
     * POST  /device-data : Create a new deviceData.
     *
     * @param deviceDataDTO the deviceDataDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deviceDataDTO, or with status 400 (Bad Request) if the deviceData has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/device-data")
    @Timed
    public ResponseEntity<DeviceDataDTO> createDeviceData(@RequestBody DeviceDataDTO deviceDataDTO) throws URISyntaxException {
        log.debug("REST request to save DeviceData : {}", deviceDataDTO);
        if (deviceDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new deviceData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeviceData deviceData = deviceDataMapper.toEntity(deviceDataDTO);
        deviceData = deviceDataRepository.save(deviceData);
        DeviceDataDTO result = deviceDataMapper.toDto(deviceData);
        deviceDataSearchRepository.save(deviceData);
        return ResponseEntity.created(new URI("/api/device-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /device-data : Updates an existing deviceData.
     *
     * @param deviceDataDTO the deviceDataDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deviceDataDTO,
     * or with status 400 (Bad Request) if the deviceDataDTO is not valid,
     * or with status 500 (Internal Server Error) if the deviceDataDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/device-data")
    @Timed
    public ResponseEntity<DeviceDataDTO> updateDeviceData(@RequestBody DeviceDataDTO deviceDataDTO) throws URISyntaxException {
        log.debug("REST request to update DeviceData : {}", deviceDataDTO);
        if (deviceDataDTO.getId() == null) {
            return createDeviceData(deviceDataDTO);
        }
        DeviceData deviceData = deviceDataMapper.toEntity(deviceDataDTO);
        deviceData = deviceDataRepository.save(deviceData);
        DeviceDataDTO result = deviceDataMapper.toDto(deviceData);
        deviceDataSearchRepository.save(deviceData);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, deviceDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /device-data : get all the deviceData.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of deviceData in body
     */
    @GetMapping("/device-data")
    @Timed
    public ResponseEntity<List<DeviceDataDTO>> getAllDeviceData(Pageable pageable) {
        log.debug("REST request to get a page of DeviceData");
        Page<DeviceData> page = deviceDataRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/device-data");
        return new ResponseEntity<>(deviceDataMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /device-data/:id : get the "id" deviceData.
     *
     * @param id the id of the deviceDataDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deviceDataDTO, or with status 404 (Not Found)
     */
    @GetMapping("/device-data/{id}")
    @Timed
    public ResponseEntity<DeviceDataDTO> getDeviceData(@PathVariable Long id) {
        log.debug("REST request to get DeviceData : {}", id);
        DeviceData deviceData = deviceDataRepository.findOne(id);
        DeviceDataDTO deviceDataDTO = deviceDataMapper.toDto(deviceData);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(deviceDataDTO));
    }

    /**
     * DELETE  /device-data/:id : delete the "id" deviceData.
     *
     * @param id the id of the deviceDataDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/device-data/{id}")
    @Timed
    public ResponseEntity<Void> deleteDeviceData(@PathVariable Long id) {
        log.debug("REST request to delete DeviceData : {}", id);
        deviceDataRepository.delete(id);
        deviceDataSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/device-data?query=:query : search for the deviceData corresponding
     * to the query.
     *
     * @param query the query of the deviceData search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/device-data")
    @Timed
    public ResponseEntity<List<DeviceDataDTO>> searchDeviceData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DeviceData for query {}", query);
        Page<DeviceData> page = deviceDataSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/device-data");
        return new ResponseEntity<>(deviceDataMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

}
