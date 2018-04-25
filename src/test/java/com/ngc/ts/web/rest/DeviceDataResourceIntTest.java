package com.ngc.ts.web.rest;

import com.ngc.ts.TpimsApp;

import com.ngc.ts.domain.DeviceData;
import com.ngc.ts.repository.DeviceDataRepository;
import com.ngc.ts.repository.search.DeviceDataSearchRepository;
import com.ngc.ts.service.dto.DeviceDataDTO;
import com.ngc.ts.service.mapper.DeviceDataMapper;
import com.ngc.ts.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.ngc.ts.web.rest.TestUtil.sameInstant;
import static com.ngc.ts.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DeviceDataResource REST controller.
 *
 * @see DeviceDataResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TpimsApp.class)
public class DeviceDataResourceIntTest {

    private static final ZonedDateTime DEFAULT_TIME_STAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME_STAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DEVICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ZONE = 1;
    private static final Integer UPDATED_ZONE = 2;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Integer DEFAULT_SPEED = 1;
    private static final Integer UPDATED_SPEED = 2;

    private static final Integer DEFAULT_VOLUME = 1;
    private static final Integer UPDATED_VOLUME = 2;

    private static final Integer DEFAULT_OCCUPANCY = 1;
    private static final Integer UPDATED_OCCUPANCY = 2;

    @Autowired
    private DeviceDataRepository deviceDataRepository;

    @Autowired
    private DeviceDataMapper deviceDataMapper;

    @Autowired
    private DeviceDataSearchRepository deviceDataSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDeviceDataMockMvc;

    private DeviceData deviceData;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeviceDataResource deviceDataResource = new DeviceDataResource(deviceDataRepository, deviceDataMapper, deviceDataSearchRepository);
        this.restDeviceDataMockMvc = MockMvcBuilders.standaloneSetup(deviceDataResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceData createEntity(EntityManager em) {
        DeviceData deviceData = new DeviceData()
            .timeStamp(DEFAULT_TIME_STAMP)
            .deviceName(DEFAULT_DEVICE_NAME)
            .zone(DEFAULT_ZONE)
            .status(DEFAULT_STATUS)
            .speed(DEFAULT_SPEED)
            .volume(DEFAULT_VOLUME)
            .occupancy(DEFAULT_OCCUPANCY);
        return deviceData;
    }

    @Before
    public void initTest() {
        deviceDataSearchRepository.deleteAll();
        deviceData = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeviceData() throws Exception {
        int databaseSizeBeforeCreate = deviceDataRepository.findAll().size();

        // Create the DeviceData
        DeviceDataDTO deviceDataDTO = deviceDataMapper.toDto(deviceData);
        restDeviceDataMockMvc.perform(post("/api/device-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDataDTO)))
            .andExpect(status().isCreated());

        // Validate the DeviceData in the database
        List<DeviceData> deviceDataList = deviceDataRepository.findAll();
        assertThat(deviceDataList).hasSize(databaseSizeBeforeCreate + 1);
        DeviceData testDeviceData = deviceDataList.get(deviceDataList.size() - 1);
        assertThat(testDeviceData.getTimeStamp()).isEqualTo(DEFAULT_TIME_STAMP);
        assertThat(testDeviceData.getDeviceName()).isEqualTo(DEFAULT_DEVICE_NAME);
        assertThat(testDeviceData.getZone()).isEqualTo(DEFAULT_ZONE);
        assertThat(testDeviceData.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDeviceData.getSpeed()).isEqualTo(DEFAULT_SPEED);
        assertThat(testDeviceData.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testDeviceData.getOccupancy()).isEqualTo(DEFAULT_OCCUPANCY);

        // Validate the DeviceData in Elasticsearch
        DeviceData deviceDataEs = deviceDataSearchRepository.findOne(testDeviceData.getId());
        assertThat(testDeviceData.getTimeStamp()).isEqualTo(testDeviceData.getTimeStamp());
        assertThat(deviceDataEs).isEqualToIgnoringGivenFields(testDeviceData, "timeStamp");
    }

    @Test
    @Transactional
    public void createDeviceDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deviceDataRepository.findAll().size();

        // Create the DeviceData with an existing ID
        deviceData.setId(1L);
        DeviceDataDTO deviceDataDTO = deviceDataMapper.toDto(deviceData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceDataMockMvc.perform(post("/api/device-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceData in the database
        List<DeviceData> deviceDataList = deviceDataRepository.findAll();
        assertThat(deviceDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDeviceData() throws Exception {
        // Initialize the database
        deviceDataRepository.saveAndFlush(deviceData);

        // Get all the deviceDataList
        restDeviceDataMockMvc.perform(get("/api/device-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceData.getId().intValue())))
            .andExpect(jsonPath("$.[*].timeStamp").value(hasItem(sameInstant(DEFAULT_TIME_STAMP))))
            .andExpect(jsonPath("$.[*].deviceName").value(hasItem(DEFAULT_DEVICE_NAME.toString())))
            .andExpect(jsonPath("$.[*].zone").value(hasItem(DEFAULT_ZONE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].speed").value(hasItem(DEFAULT_SPEED)))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME)))
            .andExpect(jsonPath("$.[*].occupancy").value(hasItem(DEFAULT_OCCUPANCY)));
    }

    @Test
    @Transactional
    public void getDeviceData() throws Exception {
        // Initialize the database
        deviceDataRepository.saveAndFlush(deviceData);

        // Get the deviceData
        restDeviceDataMockMvc.perform(get("/api/device-data/{id}", deviceData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deviceData.getId().intValue()))
            .andExpect(jsonPath("$.timeStamp").value(sameInstant(DEFAULT_TIME_STAMP)))
            .andExpect(jsonPath("$.deviceName").value(DEFAULT_DEVICE_NAME.toString()))
            .andExpect(jsonPath("$.zone").value(DEFAULT_ZONE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.speed").value(DEFAULT_SPEED))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME))
            .andExpect(jsonPath("$.occupancy").value(DEFAULT_OCCUPANCY));
    }

    @Test
    @Transactional
    public void getNonExistingDeviceData() throws Exception {
        // Get the deviceData
        restDeviceDataMockMvc.perform(get("/api/device-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeviceData() throws Exception {
        // Initialize the database
        deviceDataRepository.saveAndFlush(deviceData);
        deviceDataSearchRepository.save(deviceData);
        int databaseSizeBeforeUpdate = deviceDataRepository.findAll().size();

        // Update the deviceData
        DeviceData updatedDeviceData = deviceDataRepository.findOne(deviceData.getId());
        // Disconnect from session so that the updates on updatedDeviceData are not directly saved in db
        em.detach(updatedDeviceData);
        updatedDeviceData
            .timeStamp(UPDATED_TIME_STAMP)
            .deviceName(UPDATED_DEVICE_NAME)
            .zone(UPDATED_ZONE)
            .status(UPDATED_STATUS)
            .speed(UPDATED_SPEED)
            .volume(UPDATED_VOLUME)
            .occupancy(UPDATED_OCCUPANCY);
        DeviceDataDTO deviceDataDTO = deviceDataMapper.toDto(updatedDeviceData);

        restDeviceDataMockMvc.perform(put("/api/device-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDataDTO)))
            .andExpect(status().isOk());

        // Validate the DeviceData in the database
        List<DeviceData> deviceDataList = deviceDataRepository.findAll();
        assertThat(deviceDataList).hasSize(databaseSizeBeforeUpdate);
        DeviceData testDeviceData = deviceDataList.get(deviceDataList.size() - 1);
        assertThat(testDeviceData.getTimeStamp()).isEqualTo(UPDATED_TIME_STAMP);
        assertThat(testDeviceData.getDeviceName()).isEqualTo(UPDATED_DEVICE_NAME);
        assertThat(testDeviceData.getZone()).isEqualTo(UPDATED_ZONE);
        assertThat(testDeviceData.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDeviceData.getSpeed()).isEqualTo(UPDATED_SPEED);
        assertThat(testDeviceData.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testDeviceData.getOccupancy()).isEqualTo(UPDATED_OCCUPANCY);

        // Validate the DeviceData in Elasticsearch
        DeviceData deviceDataEs = deviceDataSearchRepository.findOne(testDeviceData.getId());
        assertThat(testDeviceData.getTimeStamp()).isEqualTo(testDeviceData.getTimeStamp());
        assertThat(deviceDataEs).isEqualToIgnoringGivenFields(testDeviceData, "timeStamp");
    }

    @Test
    @Transactional
    public void updateNonExistingDeviceData() throws Exception {
        int databaseSizeBeforeUpdate = deviceDataRepository.findAll().size();

        // Create the DeviceData
        DeviceDataDTO deviceDataDTO = deviceDataMapper.toDto(deviceData);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDeviceDataMockMvc.perform(put("/api/device-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDataDTO)))
            .andExpect(status().isCreated());

        // Validate the DeviceData in the database
        List<DeviceData> deviceDataList = deviceDataRepository.findAll();
        assertThat(deviceDataList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDeviceData() throws Exception {
        // Initialize the database
        deviceDataRepository.saveAndFlush(deviceData);
        deviceDataSearchRepository.save(deviceData);
        int databaseSizeBeforeDelete = deviceDataRepository.findAll().size();

        // Get the deviceData
        restDeviceDataMockMvc.perform(delete("/api/device-data/{id}", deviceData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean deviceDataExistsInEs = deviceDataSearchRepository.exists(deviceData.getId());
        assertThat(deviceDataExistsInEs).isFalse();

        // Validate the database is empty
        List<DeviceData> deviceDataList = deviceDataRepository.findAll();
        assertThat(deviceDataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDeviceData() throws Exception {
        // Initialize the database
        deviceDataRepository.saveAndFlush(deviceData);
        deviceDataSearchRepository.save(deviceData);

        // Search the deviceData
        restDeviceDataMockMvc.perform(get("/api/_search/device-data?query=id:" + deviceData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceData.getId().intValue())))
            .andExpect(jsonPath("$.[*].timeStamp").value(hasItem(sameInstant(DEFAULT_TIME_STAMP))))
            .andExpect(jsonPath("$.[*].deviceName").value(hasItem(DEFAULT_DEVICE_NAME.toString())))
            .andExpect(jsonPath("$.[*].zone").value(hasItem(DEFAULT_ZONE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].speed").value(hasItem(DEFAULT_SPEED)))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME)))
            .andExpect(jsonPath("$.[*].occupancy").value(hasItem(DEFAULT_OCCUPANCY)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceData.class);
        DeviceData deviceData1 = new DeviceData();
        deviceData1.setId(1L);
        DeviceData deviceData2 = new DeviceData();
        deviceData2.setId(deviceData1.getId());
        assertThat(deviceData1).isEqualTo(deviceData2);
        deviceData2.setId(2L);
        assertThat(deviceData1).isNotEqualTo(deviceData2);
        deviceData1.setId(null);
        assertThat(deviceData1).isNotEqualTo(deviceData2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceDataDTO.class);
        DeviceDataDTO deviceDataDTO1 = new DeviceDataDTO();
        deviceDataDTO1.setId(1L);
        DeviceDataDTO deviceDataDTO2 = new DeviceDataDTO();
        assertThat(deviceDataDTO1).isNotEqualTo(deviceDataDTO2);
        deviceDataDTO2.setId(deviceDataDTO1.getId());
        assertThat(deviceDataDTO1).isEqualTo(deviceDataDTO2);
        deviceDataDTO2.setId(2L);
        assertThat(deviceDataDTO1).isNotEqualTo(deviceDataDTO2);
        deviceDataDTO1.setId(null);
        assertThat(deviceDataDTO1).isNotEqualTo(deviceDataDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(deviceDataMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(deviceDataMapper.fromId(null)).isNull();
    }
}
