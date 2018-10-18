package com.ngc.ts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ngc.ts.domain.Device;

import com.ngc.ts.domain.enumeration.LocationFunctions;
import com.ngc.ts.repository.DeviceRepository;
import com.ngc.ts.repository.search.DeviceSearchRepository;
import com.ngc.ts.web.rest.errors.BadRequestAlertException;
import com.ngc.ts.web.rest.util.HeaderUtil;
import com.ngc.ts.web.rest.util.PaginationUtil;
import com.ngc.ts.service.dto.DeviceDTO;
import com.ngc.ts.service.mapper.DeviceMapper;
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
 * REST controller for managing Device.
 */
@RestController
@RequestMapping("/api")
public class DeviceResource {

    private final Logger log = LoggerFactory.getLogger(DeviceResource.class);

    private static final String ENTITY_NAME = "device";

    private final DeviceRepository deviceRepository;

    private final DeviceMapper deviceMapper;

    private final DeviceSearchRepository deviceSearchRepository;

    public DeviceResource(DeviceRepository deviceRepository, DeviceMapper deviceMapper, DeviceSearchRepository deviceSearchRepository) {
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
        this.deviceSearchRepository = deviceSearchRepository;
    }

    /**
     * POST  /devices : Create a new device.
     *
     * @param deviceDTO the deviceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deviceDTO, or with status 400 (Bad Request) if the device has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/devices")
    @Timed
    public ResponseEntity<DeviceDTO> createDevice(@RequestBody DeviceDTO deviceDTO) throws URISyntaxException {
        log.debug("REST request to save Device : {}", deviceDTO);
        if (deviceDTO.getId() != null) {
            throw new BadRequestAlertException("A new device cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Device device = deviceMapper.toEntity(deviceDTO);
        device = deviceRepository.save(device);
        DeviceDTO result = deviceMapper.toDto(device);
        deviceSearchRepository.save(device);
        return ResponseEntity.created(new URI("/api/devices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /devices : Updates an existing device.
     *
     * @param deviceDTO the deviceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deviceDTO,
     * or with status 400 (Bad Request) if the deviceDTO is not valid,
     * or with status 500 (Internal Server Error) if the deviceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/devices")
    @Timed
    public ResponseEntity<DeviceDTO> updateDevice(@RequestBody DeviceDTO deviceDTO) throws URISyntaxException {
        log.debug("REST request to update Device : {}", deviceDTO);
        if (deviceDTO.getId() == null) {
            return createDevice(deviceDTO);
        }
        Device device = deviceMapper.toEntity(deviceDTO);
        device = deviceRepository.save(device);
        DeviceDTO result = deviceMapper.toDto(device);
        deviceSearchRepository.save(device);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, deviceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /devices : get all the devices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of devices in body
     */
    @GetMapping("/devices")
    @Timed
    public ResponseEntity<List<DeviceDTO>> getAllDevices(Pageable pageable) {
        log.debug("REST request to get a page of Devices");
        Page<Device> page = deviceRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/devices");
        return new ResponseEntity<>(deviceMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /devices/:id : get the "id" device.
     *
     * @param id the id of the deviceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deviceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/devices/{id}")
    @Timed
    public ResponseEntity<DeviceDTO> getDevice(@PathVariable Long id) {
        log.debug("REST request to get Device : {}", id);
        Device device = deviceRepository.findOne(id);
        DeviceDTO deviceDTO = deviceMapper.toDto(device);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(deviceDTO));
    }

    /**
     * GET  /devices/byDeviceName/:name : get the "name" device.
     *
     * @param name the deviceName of the deviceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deviceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/devices/byDeviceName/{name}")
    @Timed
    public ResponseEntity<DeviceDTO> getDeviceByDeviceName(@PathVariable String name) {
        log.debug("REST request to get Device by name: {}", name);
        Device device = deviceRepository.findByDeviceName(name);
        DeviceDTO deviceDTO = deviceMapper.toDto(device);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(deviceDTO));
    }

    /**
     * GET  /devices/bySiteId/:name : get the "name" device.
     *
     * @param id the siteId of the deviceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deviceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/devices/bySiteId/{id}")
    @Timed
    public ResponseEntity<List<DeviceDTO>> getDevicesBySiteId(@PathVariable Long id) {
        log.debug("REST request to get Devices by site.id: {}", id);
        List<Device> devices = deviceRepository.findBySiteId(id);
        List<DeviceDTO> deviceDTOs = deviceMapper.toDto(devices);
        return new ResponseEntity<>(deviceDTOs, HttpStatus.OK);
    }

    /**
     * GET  /devices/byLocationFunction/:func : get the "function" device.
     *
     * @param func the locationfunction of the deviceDTOs to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deviceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/devices/byLocationFunction/{func}")
    @Timed
    public ResponseEntity<List<DeviceDTO>> getDevicesByLocationFunction(@PathVariable String func) {
        log.debug("REST request to get Devices by locationfunction: {}", func);
        List<Device> devices = deviceRepository.findByLocationfunction(LocationFunctions.valueOf(func));
        List<DeviceDTO> deviceDTOs = deviceMapper.toDto(devices);
        return new ResponseEntity<>(deviceDTOs, HttpStatus.OK);
    }

    /**
     * DELETE  /devices/:id : delete the "id" device.
     *
     * @param id the id of the deviceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/devices/{id}")
    @Timed
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        log.debug("REST request to delete Device : {}", id);
        deviceRepository.delete(id);
        deviceSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/devices?query=:query : search for the device corresponding
     * to the query.
     *
     * @param query the query of the device search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/devices")
    @Timed
    public ResponseEntity<List<DeviceDTO>> searchDevices(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Devices for query {}", query);
        Page<Device> page = deviceSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/devices");
        return new ResponseEntity<>(deviceMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

}
