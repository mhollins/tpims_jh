package com.ngc.ts.web.rest;

import com.ngc.ts.TpimsApp;

import com.ngc.ts.domain.SiteStatus;
import com.ngc.ts.repository.SiteStatusRepository;
import com.ngc.ts.service.SiteStatusService;
import com.ngc.ts.repository.search.SiteStatusSearchRepository;
import com.ngc.ts.service.dto.SiteStatusDTO;
import com.ngc.ts.service.mapper.SiteStatusMapper;
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
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.ngc.ts.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ngc.ts.domain.enumeration.TrendOptions;
/**
 * Test class for the SiteStatusResource REST controller.
 *
 * @see SiteStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TpimsApp.class)
public class SiteStatusResourceIntTest {

    private static final Integer DEFAULT_REPORTED_AVAILABLE = 1;
    private static final Integer UPDATED_REPORTED_AVAILABLE = 2;

    private static final Integer DEFAULT_VEHICLES_COUNTED = 1;
    private static final Integer UPDATED_VEHICLES_COUNTED = 2;

    private static final TrendOptions DEFAULT_TREND = TrendOptions.EMPTYING;
    private static final TrendOptions UPDATED_TREND = TrendOptions.STEADY;

    private static final Boolean DEFAULT_OPEN = false;
    private static final Boolean UPDATED_OPEN = true;

    private static final Boolean DEFAULT_TRUST_DATA = false;
    private static final Boolean UPDATED_TRUST_DATA = true;

    private static final Instant DEFAULT_LAST_DEVICE_UPDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_DEVICE_UPDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_OPERATOR_UPDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_OPERATOR_UPDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VERIFICATION_CHECK_AMPLITUDE = 1;
    private static final Integer UPDATED_VERIFICATION_CHECK_AMPLITUDE = 2;

    @Autowired
    private SiteStatusRepository siteStatusRepository;

    @Autowired
    private SiteStatusMapper siteStatusMapper;

    @Autowired
    private SiteStatusService siteStatusService;

    @Autowired
    private SiteStatusSearchRepository siteStatusSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSiteStatusMockMvc;

    private SiteStatus siteStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SiteStatusResource siteStatusResource = new SiteStatusResource(siteStatusService);
        this.restSiteStatusMockMvc = MockMvcBuilders.standaloneSetup(siteStatusResource)
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
    public static SiteStatus createEntity(EntityManager em) {
        SiteStatus siteStatus = new SiteStatus()
            .reportedAvailable(DEFAULT_REPORTED_AVAILABLE)
            .vehiclesCounted(DEFAULT_VEHICLES_COUNTED)
            .trend(DEFAULT_TREND)
            .open(DEFAULT_OPEN)
            .trustData(DEFAULT_TRUST_DATA)
            .lastDeviceUpdate(DEFAULT_LAST_DEVICE_UPDATE)
            .lastOperatorUpdate(DEFAULT_LAST_OPERATOR_UPDATE)
            .verificationCheckAmplitude(DEFAULT_VERIFICATION_CHECK_AMPLITUDE);
        return siteStatus;
    }

    @Before
    public void initTest() {
        siteStatusSearchRepository.deleteAll();
        siteStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createSiteStatus() throws Exception {
        int databaseSizeBeforeCreate = siteStatusRepository.findAll().size();

        // Create the SiteStatus
        SiteStatusDTO siteStatusDTO = siteStatusMapper.toDto(siteStatus);
        restSiteStatusMockMvc.perform(post("/api/site-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siteStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the SiteStatus in the database
        List<SiteStatus> siteStatusList = siteStatusRepository.findAll();
        assertThat(siteStatusList).hasSize(databaseSizeBeforeCreate + 1);
        SiteStatus testSiteStatus = siteStatusList.get(siteStatusList.size() - 1);
        assertThat(testSiteStatus.getReportedAvailable()).isEqualTo(DEFAULT_REPORTED_AVAILABLE);
        assertThat(testSiteStatus.getVehiclesCounted()).isEqualTo(DEFAULT_VEHICLES_COUNTED);
        assertThat(testSiteStatus.getTrend()).isEqualTo(DEFAULT_TREND);
        assertThat(testSiteStatus.isOpen()).isEqualTo(DEFAULT_OPEN);
        assertThat(testSiteStatus.isTrustData()).isEqualTo(DEFAULT_TRUST_DATA);
        assertThat(testSiteStatus.getLastDeviceUpdate()).isEqualTo(DEFAULT_LAST_DEVICE_UPDATE);
        assertThat(testSiteStatus.getLastOperatorUpdate()).isEqualTo(DEFAULT_LAST_OPERATOR_UPDATE);
        assertThat(testSiteStatus.getVerificationCheckAmplitude()).isEqualTo(DEFAULT_VERIFICATION_CHECK_AMPLITUDE);

        // Validate the SiteStatus in Elasticsearch
        SiteStatus siteStatusEs = siteStatusSearchRepository.findOne(testSiteStatus.getId());
        assertThat(siteStatusEs).isEqualToIgnoringGivenFields(testSiteStatus);
    }

    @Test
    @Transactional
    public void createSiteStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = siteStatusRepository.findAll().size();

        // Create the SiteStatus with an existing ID
        siteStatus.setId(1L);
        SiteStatusDTO siteStatusDTO = siteStatusMapper.toDto(siteStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSiteStatusMockMvc.perform(post("/api/site-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siteStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SiteStatus in the database
        List<SiteStatus> siteStatusList = siteStatusRepository.findAll();
        assertThat(siteStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSiteStatuses() throws Exception {
        // Initialize the database
        siteStatusRepository.saveAndFlush(siteStatus);

        // Get all the siteStatusList
        restSiteStatusMockMvc.perform(get("/api/site-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(siteStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].reportedAvailable").value(hasItem(DEFAULT_REPORTED_AVAILABLE)))
            .andExpect(jsonPath("$.[*].vehiclesCounted").value(hasItem(DEFAULT_VEHICLES_COUNTED)))
            .andExpect(jsonPath("$.[*].trend").value(hasItem(DEFAULT_TREND.toString())))
            .andExpect(jsonPath("$.[*].open").value(hasItem(DEFAULT_OPEN.booleanValue())))
            .andExpect(jsonPath("$.[*].trustData").value(hasItem(DEFAULT_TRUST_DATA.booleanValue())))
            .andExpect(jsonPath("$.[*].lastDeviceUpdate").value(hasItem(DEFAULT_LAST_DEVICE_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].lastOperatorUpdate").value(hasItem(DEFAULT_LAST_OPERATOR_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].verificationCheckAmplitude").value(hasItem(DEFAULT_VERIFICATION_CHECK_AMPLITUDE)));
    }

    @Test
    @Transactional
    public void getSiteStatus() throws Exception {
        // Initialize the database
        siteStatusRepository.saveAndFlush(siteStatus);

        // Get the siteStatus
        restSiteStatusMockMvc.perform(get("/api/site-statuses/{id}", siteStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(siteStatus.getId().intValue()))
            .andExpect(jsonPath("$.reportedAvailable").value(DEFAULT_REPORTED_AVAILABLE))
            .andExpect(jsonPath("$.vehiclesCounted").value(DEFAULT_VEHICLES_COUNTED))
            .andExpect(jsonPath("$.trend").value(DEFAULT_TREND.toString()))
            .andExpect(jsonPath("$.open").value(DEFAULT_OPEN.booleanValue()))
            .andExpect(jsonPath("$.trustData").value(DEFAULT_TRUST_DATA.booleanValue()))
            .andExpect(jsonPath("$.lastDeviceUpdate").value(DEFAULT_LAST_DEVICE_UPDATE.toString()))
            .andExpect(jsonPath("$.lastOperatorUpdate").value(DEFAULT_LAST_OPERATOR_UPDATE.toString()))
            .andExpect(jsonPath("$.verificationCheckAmplitude").value(DEFAULT_VERIFICATION_CHECK_AMPLITUDE));
    }

    @Test
    @Transactional
    public void getNonExistingSiteStatus() throws Exception {
        // Get the siteStatus
        restSiteStatusMockMvc.perform(get("/api/site-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSiteStatus() throws Exception {
        // Initialize the database
        siteStatusRepository.saveAndFlush(siteStatus);
        siteStatusSearchRepository.save(siteStatus);
        int databaseSizeBeforeUpdate = siteStatusRepository.findAll().size();

        // Update the siteStatus
        SiteStatus updatedSiteStatus = siteStatusRepository.findOne(siteStatus.getId());
        // Disconnect from session so that the updates on updatedSiteStatus are not directly saved in db
        em.detach(updatedSiteStatus);
        updatedSiteStatus
            .reportedAvailable(UPDATED_REPORTED_AVAILABLE)
            .vehiclesCounted(UPDATED_VEHICLES_COUNTED)
            .trend(UPDATED_TREND)
            .open(UPDATED_OPEN)
            .trustData(UPDATED_TRUST_DATA)
            .lastDeviceUpdate(UPDATED_LAST_DEVICE_UPDATE)
            .lastOperatorUpdate(UPDATED_LAST_OPERATOR_UPDATE)
            .verificationCheckAmplitude(UPDATED_VERIFICATION_CHECK_AMPLITUDE);
        SiteStatusDTO siteStatusDTO = siteStatusMapper.toDto(updatedSiteStatus);

        restSiteStatusMockMvc.perform(put("/api/site-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siteStatusDTO)))
            .andExpect(status().isOk());

        // Validate the SiteStatus in the database
        List<SiteStatus> siteStatusList = siteStatusRepository.findAll();
        assertThat(siteStatusList).hasSize(databaseSizeBeforeUpdate);
        SiteStatus testSiteStatus = siteStatusList.get(siteStatusList.size() - 1);
        assertThat(testSiteStatus.getReportedAvailable()).isEqualTo(UPDATED_REPORTED_AVAILABLE);
        assertThat(testSiteStatus.getVehiclesCounted()).isEqualTo(UPDATED_VEHICLES_COUNTED);
        assertThat(testSiteStatus.getTrend()).isEqualTo(UPDATED_TREND);
        assertThat(testSiteStatus.isOpen()).isEqualTo(UPDATED_OPEN);
        assertThat(testSiteStatus.isTrustData()).isEqualTo(UPDATED_TRUST_DATA);
        assertThat(testSiteStatus.getLastDeviceUpdate()).isEqualTo(UPDATED_LAST_DEVICE_UPDATE);
        assertThat(testSiteStatus.getLastOperatorUpdate()).isEqualTo(UPDATED_LAST_OPERATOR_UPDATE);
        assertThat(testSiteStatus.getVerificationCheckAmplitude()).isEqualTo(UPDATED_VERIFICATION_CHECK_AMPLITUDE);

        // Validate the SiteStatus in Elasticsearch
        SiteStatus siteStatusEs = siteStatusSearchRepository.findOne(testSiteStatus.getId());
        assertThat(siteStatusEs).isEqualToIgnoringGivenFields(testSiteStatus);
    }

    @Test
    @Transactional
    public void updateNonExistingSiteStatus() throws Exception {
        int databaseSizeBeforeUpdate = siteStatusRepository.findAll().size();

        // Create the SiteStatus
        SiteStatusDTO siteStatusDTO = siteStatusMapper.toDto(siteStatus);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSiteStatusMockMvc.perform(put("/api/site-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siteStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the SiteStatus in the database
        List<SiteStatus> siteStatusList = siteStatusRepository.findAll();
        assertThat(siteStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSiteStatus() throws Exception {
        // Initialize the database
        siteStatusRepository.saveAndFlush(siteStatus);
        siteStatusSearchRepository.save(siteStatus);
        int databaseSizeBeforeDelete = siteStatusRepository.findAll().size();

        // Get the siteStatus
        restSiteStatusMockMvc.perform(delete("/api/site-statuses/{id}", siteStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean siteStatusExistsInEs = siteStatusSearchRepository.exists(siteStatus.getId());
        assertThat(siteStatusExistsInEs).isFalse();

        // Validate the database is empty
        List<SiteStatus> siteStatusList = siteStatusRepository.findAll();
        assertThat(siteStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSiteStatus() throws Exception {
        // Initialize the database
        siteStatusRepository.saveAndFlush(siteStatus);
        siteStatusSearchRepository.save(siteStatus);

        // Search the siteStatus
        restSiteStatusMockMvc.perform(get("/api/_search/site-statuses?query=id:" + siteStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(siteStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].reportedAvailable").value(hasItem(DEFAULT_REPORTED_AVAILABLE)))
            .andExpect(jsonPath("$.[*].vehiclesCounted").value(hasItem(DEFAULT_VEHICLES_COUNTED)))
            .andExpect(jsonPath("$.[*].trend").value(hasItem(DEFAULT_TREND.toString())))
            .andExpect(jsonPath("$.[*].open").value(hasItem(DEFAULT_OPEN.booleanValue())))
            .andExpect(jsonPath("$.[*].trustData").value(hasItem(DEFAULT_TRUST_DATA.booleanValue())))
            .andExpect(jsonPath("$.[*].lastDeviceUpdate").value(hasItem(DEFAULT_LAST_DEVICE_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].lastOperatorUpdate").value(hasItem(DEFAULT_LAST_OPERATOR_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].verificationCheckAmplitude").value(hasItem(DEFAULT_VERIFICATION_CHECK_AMPLITUDE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiteStatus.class);
        SiteStatus siteStatus1 = new SiteStatus();
        siteStatus1.setId(1L);
        SiteStatus siteStatus2 = new SiteStatus();
        siteStatus2.setId(siteStatus1.getId());
        assertThat(siteStatus1).isEqualTo(siteStatus2);
        siteStatus2.setId(2L);
        assertThat(siteStatus1).isNotEqualTo(siteStatus2);
        siteStatus1.setId(null);
        assertThat(siteStatus1).isNotEqualTo(siteStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiteStatusDTO.class);
        SiteStatusDTO siteStatusDTO1 = new SiteStatusDTO();
        siteStatusDTO1.setId(1L);
        SiteStatusDTO siteStatusDTO2 = new SiteStatusDTO();
        assertThat(siteStatusDTO1).isNotEqualTo(siteStatusDTO2);
        siteStatusDTO2.setId(siteStatusDTO1.getId());
        assertThat(siteStatusDTO1).isEqualTo(siteStatusDTO2);
        siteStatusDTO2.setId(2L);
        assertThat(siteStatusDTO1).isNotEqualTo(siteStatusDTO2);
        siteStatusDTO1.setId(null);
        assertThat(siteStatusDTO1).isNotEqualTo(siteStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(siteStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(siteStatusMapper.fromId(null)).isNull();
    }
}
