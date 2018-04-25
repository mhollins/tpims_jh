package com.ngc.ts.web.rest;

import com.ngc.ts.TpimsApp;

import com.ngc.ts.domain.Device;
import com.ngc.ts.repository.DeviceRepository;
import com.ngc.ts.repository.search.DeviceSearchRepository;
import com.ngc.ts.service.dto.DeviceDTO;
import com.ngc.ts.service.mapper.DeviceMapper;
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
import java.util.List;

import static com.ngc.ts.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ngc.ts.domain.enumeration.LocationFunctions;
/**
 * Test class for the DeviceResource REST controller.
 *
 * @see DeviceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TpimsApp.class)
public class DeviceResourceIntTest {

    private static final String DEFAULT_DEVICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_IP_PORT = 1;
    private static final Integer UPDATED_IP_PORT = 2;

    private static final Integer DEFAULT_DEVICE_ADDRESS = 1;
    private static final Integer UPDATED_DEVICE_ADDRESS = 2;

    private static final Integer DEFAULT_POLLING_RATE = 1;
    private static final Integer UPDATED_POLLING_RATE = 2;

    private static final String DEFAULT_JMS_DOMAIN = "AAAAAAAAAA";
    private static final String UPDATED_JMS_DOMAIN = "BBBBBBBBBB";

    private static final Integer DEFAULT_TIMEOUT = 1;
    private static final Integer UPDATED_TIMEOUT = 2;

    private static final LocationFunctions DEFAULT_LOCATIONFUNCTION = LocationFunctions.ENTRANCE;
    private static final LocationFunctions UPDATED_LOCATIONFUNCTION = LocationFunctions.EXIT;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private DeviceSearchRepository deviceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDeviceMockMvc;

    private Device device;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeviceResource deviceResource = new DeviceResource(deviceRepository, deviceMapper, deviceSearchRepository);
        this.restDeviceMockMvc = MockMvcBuilders.standaloneSetup(deviceResource)
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
    public static Device createEntity(EntityManager em) {
        Device device = new Device()
            .deviceName(DEFAULT_DEVICE_NAME)
            .ipAddress(DEFAULT_IP_ADDRESS)
            .ipPort(DEFAULT_IP_PORT)
            .deviceAddress(DEFAULT_DEVICE_ADDRESS)
            .pollingRate(DEFAULT_POLLING_RATE)
            .jmsDomain(DEFAULT_JMS_DOMAIN)
            .timeout(DEFAULT_TIMEOUT)
            .locationfunction(DEFAULT_LOCATIONFUNCTION);
        return device;
    }

    @Before
    public void initTest() {
        deviceSearchRepository.deleteAll();
        device = createEntity(em);
    }

    @Test
    @Transactional
    public void createDevice() throws Exception {
        int databaseSizeBeforeCreate = deviceRepository.findAll().size();

        // Create the Device
        DeviceDTO deviceDTO = deviceMapper.toDto(device);
        restDeviceMockMvc.perform(post("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDTO)))
            .andExpect(status().isCreated());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeCreate + 1);
        Device testDevice = deviceList.get(deviceList.size() - 1);
        assertThat(testDevice.getDeviceName()).isEqualTo(DEFAULT_DEVICE_NAME);
        assertThat(testDevice.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testDevice.getIpPort()).isEqualTo(DEFAULT_IP_PORT);
        assertThat(testDevice.getDeviceAddress()).isEqualTo(DEFAULT_DEVICE_ADDRESS);
        assertThat(testDevice.getPollingRate()).isEqualTo(DEFAULT_POLLING_RATE);
        assertThat(testDevice.getJmsDomain()).isEqualTo(DEFAULT_JMS_DOMAIN);
        assertThat(testDevice.getTimeout()).isEqualTo(DEFAULT_TIMEOUT);
        assertThat(testDevice.getLocationfunction()).isEqualTo(DEFAULT_LOCATIONFUNCTION);

        // Validate the Device in Elasticsearch
        Device deviceEs = deviceSearchRepository.findOne(testDevice.getId());
        assertThat(deviceEs).isEqualToIgnoringGivenFields(testDevice);
    }

    @Test
    @Transactional
    public void createDeviceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deviceRepository.findAll().size();

        // Create the Device with an existing ID
        device.setId(1L);
        DeviceDTO deviceDTO = deviceMapper.toDto(device);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceMockMvc.perform(post("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDevices() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList
        restDeviceMockMvc.perform(get("/api/devices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(device.getId().intValue())))
            .andExpect(jsonPath("$.[*].deviceName").value(hasItem(DEFAULT_DEVICE_NAME.toString())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].ipPort").value(hasItem(DEFAULT_IP_PORT)))
            .andExpect(jsonPath("$.[*].deviceAddress").value(hasItem(DEFAULT_DEVICE_ADDRESS)))
            .andExpect(jsonPath("$.[*].pollingRate").value(hasItem(DEFAULT_POLLING_RATE)))
            .andExpect(jsonPath("$.[*].jmsDomain").value(hasItem(DEFAULT_JMS_DOMAIN.toString())))
            .andExpect(jsonPath("$.[*].timeout").value(hasItem(DEFAULT_TIMEOUT)))
            .andExpect(jsonPath("$.[*].locationfunction").value(hasItem(DEFAULT_LOCATIONFUNCTION.toString())));
    }

    @Test
    @Transactional
    public void getDevice() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get the device
        restDeviceMockMvc.perform(get("/api/devices/{id}", device.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(device.getId().intValue()))
            .andExpect(jsonPath("$.deviceName").value(DEFAULT_DEVICE_NAME.toString()))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS.toString()))
            .andExpect(jsonPath("$.ipPort").value(DEFAULT_IP_PORT))
            .andExpect(jsonPath("$.deviceAddress").value(DEFAULT_DEVICE_ADDRESS))
            .andExpect(jsonPath("$.pollingRate").value(DEFAULT_POLLING_RATE))
            .andExpect(jsonPath("$.jmsDomain").value(DEFAULT_JMS_DOMAIN.toString()))
            .andExpect(jsonPath("$.timeout").value(DEFAULT_TIMEOUT))
            .andExpect(jsonPath("$.locationfunction").value(DEFAULT_LOCATIONFUNCTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDevice() throws Exception {
        // Get the device
        restDeviceMockMvc.perform(get("/api/devices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDevice() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);
        deviceSearchRepository.save(device);
        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();

        // Update the device
        Device updatedDevice = deviceRepository.findOne(device.getId());
        // Disconnect from session so that the updates on updatedDevice are not directly saved in db
        em.detach(updatedDevice);
        updatedDevice
            .deviceName(UPDATED_DEVICE_NAME)
            .ipAddress(UPDATED_IP_ADDRESS)
            .ipPort(UPDATED_IP_PORT)
            .deviceAddress(UPDATED_DEVICE_ADDRESS)
            .pollingRate(UPDATED_POLLING_RATE)
            .jmsDomain(UPDATED_JMS_DOMAIN)
            .timeout(UPDATED_TIMEOUT)
            .locationfunction(UPDATED_LOCATIONFUNCTION);
        DeviceDTO deviceDTO = deviceMapper.toDto(updatedDevice);

        restDeviceMockMvc.perform(put("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDTO)))
            .andExpect(status().isOk());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);
        Device testDevice = deviceList.get(deviceList.size() - 1);
        assertThat(testDevice.getDeviceName()).isEqualTo(UPDATED_DEVICE_NAME);
        assertThat(testDevice.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testDevice.getIpPort()).isEqualTo(UPDATED_IP_PORT);
        assertThat(testDevice.getDeviceAddress()).isEqualTo(UPDATED_DEVICE_ADDRESS);
        assertThat(testDevice.getPollingRate()).isEqualTo(UPDATED_POLLING_RATE);
        assertThat(testDevice.getJmsDomain()).isEqualTo(UPDATED_JMS_DOMAIN);
        assertThat(testDevice.getTimeout()).isEqualTo(UPDATED_TIMEOUT);
        assertThat(testDevice.getLocationfunction()).isEqualTo(UPDATED_LOCATIONFUNCTION);

        // Validate the Device in Elasticsearch
        Device deviceEs = deviceSearchRepository.findOne(testDevice.getId());
        assertThat(deviceEs).isEqualToIgnoringGivenFields(testDevice);
    }

    @Test
    @Transactional
    public void updateNonExistingDevice() throws Exception {
        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();

        // Create the Device
        DeviceDTO deviceDTO = deviceMapper.toDto(device);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDeviceMockMvc.perform(put("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDTO)))
            .andExpect(status().isCreated());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDevice() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);
        deviceSearchRepository.save(device);
        int databaseSizeBeforeDelete = deviceRepository.findAll().size();

        // Get the device
        restDeviceMockMvc.perform(delete("/api/devices/{id}", device.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean deviceExistsInEs = deviceSearchRepository.exists(device.getId());
        assertThat(deviceExistsInEs).isFalse();

        // Validate the database is empty
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDevice() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);
        deviceSearchRepository.save(device);

        // Search the device
        restDeviceMockMvc.perform(get("/api/_search/devices?query=id:" + device.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(device.getId().intValue())))
            .andExpect(jsonPath("$.[*].deviceName").value(hasItem(DEFAULT_DEVICE_NAME.toString())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].ipPort").value(hasItem(DEFAULT_IP_PORT)))
            .andExpect(jsonPath("$.[*].deviceAddress").value(hasItem(DEFAULT_DEVICE_ADDRESS)))
            .andExpect(jsonPath("$.[*].pollingRate").value(hasItem(DEFAULT_POLLING_RATE)))
            .andExpect(jsonPath("$.[*].jmsDomain").value(hasItem(DEFAULT_JMS_DOMAIN.toString())))
            .andExpect(jsonPath("$.[*].timeout").value(hasItem(DEFAULT_TIMEOUT)))
            .andExpect(jsonPath("$.[*].locationfunction").value(hasItem(DEFAULT_LOCATIONFUNCTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Device.class);
        Device device1 = new Device();
        device1.setId(1L);
        Device device2 = new Device();
        device2.setId(device1.getId());
        assertThat(device1).isEqualTo(device2);
        device2.setId(2L);
        assertThat(device1).isNotEqualTo(device2);
        device1.setId(null);
        assertThat(device1).isNotEqualTo(device2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceDTO.class);
        DeviceDTO deviceDTO1 = new DeviceDTO();
        deviceDTO1.setId(1L);
        DeviceDTO deviceDTO2 = new DeviceDTO();
        assertThat(deviceDTO1).isNotEqualTo(deviceDTO2);
        deviceDTO2.setId(deviceDTO1.getId());
        assertThat(deviceDTO1).isEqualTo(deviceDTO2);
        deviceDTO2.setId(2L);
        assertThat(deviceDTO1).isNotEqualTo(deviceDTO2);
        deviceDTO1.setId(null);
        assertThat(deviceDTO1).isNotEqualTo(deviceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(deviceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(deviceMapper.fromId(null)).isNull();
    }
}
