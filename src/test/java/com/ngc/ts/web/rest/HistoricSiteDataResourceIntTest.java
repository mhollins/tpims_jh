package com.ngc.ts.web.rest;

import com.ngc.ts.TpimsApp;

import com.ngc.ts.domain.HistoricSiteData;
import com.ngc.ts.repository.HistoricSiteDataRepository;
import com.ngc.ts.repository.search.HistoricSiteDataSearchRepository;
import com.ngc.ts.service.dto.HistoricSiteDataDTO;
import com.ngc.ts.service.mapper.HistoricSiteDataMapper;
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
 * Test class for the HistoricSiteDataResource REST controller.
 *
 * @see HistoricSiteDataResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TpimsApp.class)
public class HistoricSiteDataResourceIntTest {

    private static final Integer DEFAULT_TOTAL_CAPACITY = 1;
    private static final Integer UPDATED_TOTAL_CAPACITY = 2;

    private static final Integer DEFAULT_AVAILABILITY = 1;
    private static final Integer UPDATED_AVAILABILITY = 2;

    private static final TrendOptions DEFAULT_TREND = TrendOptions.CLEARING;
    private static final TrendOptions UPDATED_TREND = TrendOptions.STEADY;

    private static final Boolean DEFAULT_OPEN = false;
    private static final Boolean UPDATED_OPEN = true;

    private static final Boolean DEFAULT_TRUST_DATA = false;
    private static final Boolean UPDATED_TRUST_DATA = true;

    private static final Instant DEFAULT_TIME_STAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME_STAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_VERIFICATION_CHECK = false;
    private static final Boolean UPDATED_VERIFICATION_CHECK = true;

    private static final Integer DEFAULT_TRUE_AVAILABLE = 1;
    private static final Integer UPDATED_TRUE_AVAILABLE = 2;

    @Autowired
    private HistoricSiteDataRepository historicSiteDataRepository;

    @Autowired
    private HistoricSiteDataMapper historicSiteDataMapper;

    @Autowired
    private HistoricSiteDataSearchRepository historicSiteDataSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHistoricSiteDataMockMvc;

    private HistoricSiteData historicSiteData;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HistoricSiteDataResource historicSiteDataResource = new HistoricSiteDataResource(historicSiteDataRepository, historicSiteDataMapper, historicSiteDataSearchRepository);
        this.restHistoricSiteDataMockMvc = MockMvcBuilders.standaloneSetup(historicSiteDataResource)
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
    public static HistoricSiteData createEntity(EntityManager em) {
        HistoricSiteData historicSiteData = new HistoricSiteData()
            .totalCapacity(DEFAULT_TOTAL_CAPACITY)
            .availability(DEFAULT_AVAILABILITY)
            .trend(DEFAULT_TREND)
            .open(DEFAULT_OPEN)
            .trustData(DEFAULT_TRUST_DATA)
            .timeStamp(DEFAULT_TIME_STAMP)
            .verificationCheck(DEFAULT_VERIFICATION_CHECK)
            .trueAvailable(DEFAULT_TRUE_AVAILABLE);
        return historicSiteData;
    }

    @Before
    public void initTest() {
        historicSiteDataSearchRepository.deleteAll();
        historicSiteData = createEntity(em);
    }

    @Test
    @Transactional
    public void createHistoricSiteData() throws Exception {
        int databaseSizeBeforeCreate = historicSiteDataRepository.findAll().size();

        // Create the HistoricSiteData
        HistoricSiteDataDTO historicSiteDataDTO = historicSiteDataMapper.toDto(historicSiteData);
        restHistoricSiteDataMockMvc.perform(post("/api/historic-site-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historicSiteDataDTO)))
            .andExpect(status().isCreated());

        // Validate the HistoricSiteData in the database
        List<HistoricSiteData> historicSiteDataList = historicSiteDataRepository.findAll();
        assertThat(historicSiteDataList).hasSize(databaseSizeBeforeCreate + 1);
        HistoricSiteData testHistoricSiteData = historicSiteDataList.get(historicSiteDataList.size() - 1);
        assertThat(testHistoricSiteData.getTotalCapacity()).isEqualTo(DEFAULT_TOTAL_CAPACITY);
        assertThat(testHistoricSiteData.getAvailability()).isEqualTo(DEFAULT_AVAILABILITY);
        assertThat(testHistoricSiteData.getTrend()).isEqualTo(DEFAULT_TREND);
        assertThat(testHistoricSiteData.isOpen()).isEqualTo(DEFAULT_OPEN);
        assertThat(testHistoricSiteData.isTrustData()).isEqualTo(DEFAULT_TRUST_DATA);
        assertThat(testHistoricSiteData.getTimeStamp()).isEqualTo(DEFAULT_TIME_STAMP);
        assertThat(testHistoricSiteData.isVerificationCheck()).isEqualTo(DEFAULT_VERIFICATION_CHECK);
        assertThat(testHistoricSiteData.getTrueAvailable()).isEqualTo(DEFAULT_TRUE_AVAILABLE);

        // Validate the HistoricSiteData in Elasticsearch
        HistoricSiteData historicSiteDataEs = historicSiteDataSearchRepository.findOne(testHistoricSiteData.getId());
        assertThat(testHistoricSiteData.getTimeStamp()).isEqualTo(testHistoricSiteData.getTimeStamp());
        assertThat(historicSiteDataEs).isEqualToIgnoringGivenFields(testHistoricSiteData, "timeStamp");
    }

    @Test
    @Transactional
    public void createHistoricSiteDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = historicSiteDataRepository.findAll().size();

        // Create the HistoricSiteData with an existing ID
        historicSiteData.setId(1L);
        HistoricSiteDataDTO historicSiteDataDTO = historicSiteDataMapper.toDto(historicSiteData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistoricSiteDataMockMvc.perform(post("/api/historic-site-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historicSiteDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HistoricSiteData in the database
        List<HistoricSiteData> historicSiteDataList = historicSiteDataRepository.findAll();
        assertThat(historicSiteDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHistoricSiteData() throws Exception {
        // Initialize the database
        historicSiteDataRepository.saveAndFlush(historicSiteData);

        // Get all the historicSiteDataList
        restHistoricSiteDataMockMvc.perform(get("/api/historic-site-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historicSiteData.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalCapacity").value(hasItem(DEFAULT_TOTAL_CAPACITY)))
            .andExpect(jsonPath("$.[*].availability").value(hasItem(DEFAULT_AVAILABILITY)))
            .andExpect(jsonPath("$.[*].trend").value(hasItem(DEFAULT_TREND.toString())))
            .andExpect(jsonPath("$.[*].open").value(hasItem(DEFAULT_OPEN.booleanValue())))
            .andExpect(jsonPath("$.[*].trustData").value(hasItem(DEFAULT_TRUST_DATA.booleanValue())))
            .andExpect(jsonPath("$.[*].timeStamp").value(hasItem(DEFAULT_TIME_STAMP.toString())))
            .andExpect(jsonPath("$.[*].verificationCheck").value(hasItem(DEFAULT_VERIFICATION_CHECK.booleanValue())))
            .andExpect(jsonPath("$.[*].trueAvailable").value(hasItem(DEFAULT_TRUE_AVAILABLE)));
    }

    @Test
    @Transactional
    public void getHistoricSiteData() throws Exception {
        // Initialize the database
        historicSiteDataRepository.saveAndFlush(historicSiteData);

        // Get the historicSiteData
        restHistoricSiteDataMockMvc.perform(get("/api/historic-site-data/{id}", historicSiteData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(historicSiteData.getId().intValue()))
            .andExpect(jsonPath("$.totalCapacity").value(DEFAULT_TOTAL_CAPACITY))
            .andExpect(jsonPath("$.availability").value(DEFAULT_AVAILABILITY))
            .andExpect(jsonPath("$.trend").value(DEFAULT_TREND.toString()))
            .andExpect(jsonPath("$.open").value(DEFAULT_OPEN.booleanValue()))
            .andExpect(jsonPath("$.trustData").value(DEFAULT_TRUST_DATA.booleanValue()))
            .andExpect(jsonPath("$.timeStamp").value(DEFAULT_TIME_STAMP.toString()))
            .andExpect(jsonPath("$.verificationCheck").value(DEFAULT_VERIFICATION_CHECK.booleanValue()))
            .andExpect(jsonPath("$.trueAvailable").value(DEFAULT_TRUE_AVAILABLE));
    }

    @Test
    @Transactional
    public void getNonExistingHistoricSiteData() throws Exception {
        // Get the historicSiteData
        restHistoricSiteDataMockMvc.perform(get("/api/historic-site-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHistoricSiteData() throws Exception {
        // Initialize the database
        historicSiteDataRepository.saveAndFlush(historicSiteData);
        historicSiteDataSearchRepository.save(historicSiteData);
        int databaseSizeBeforeUpdate = historicSiteDataRepository.findAll().size();

        // Update the historicSiteData
        HistoricSiteData updatedHistoricSiteData = historicSiteDataRepository.findOne(historicSiteData.getId());
        // Disconnect from session so that the updates on updatedHistoricSiteData are not directly saved in db
        em.detach(updatedHistoricSiteData);
        updatedHistoricSiteData
            .totalCapacity(UPDATED_TOTAL_CAPACITY)
            .availability(UPDATED_AVAILABILITY)
            .trend(UPDATED_TREND)
            .open(UPDATED_OPEN)
            .trustData(UPDATED_TRUST_DATA)
            .timeStamp(UPDATED_TIME_STAMP)
            .verificationCheck(UPDATED_VERIFICATION_CHECK)
            .trueAvailable(UPDATED_TRUE_AVAILABLE);
        HistoricSiteDataDTO historicSiteDataDTO = historicSiteDataMapper.toDto(updatedHistoricSiteData);

        restHistoricSiteDataMockMvc.perform(put("/api/historic-site-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historicSiteDataDTO)))
            .andExpect(status().isOk());

        // Validate the HistoricSiteData in the database
        List<HistoricSiteData> historicSiteDataList = historicSiteDataRepository.findAll();
        assertThat(historicSiteDataList).hasSize(databaseSizeBeforeUpdate);
        HistoricSiteData testHistoricSiteData = historicSiteDataList.get(historicSiteDataList.size() - 1);
        assertThat(testHistoricSiteData.getTotalCapacity()).isEqualTo(UPDATED_TOTAL_CAPACITY);
        assertThat(testHistoricSiteData.getAvailability()).isEqualTo(UPDATED_AVAILABILITY);
        assertThat(testHistoricSiteData.getTrend()).isEqualTo(UPDATED_TREND);
        assertThat(testHistoricSiteData.isOpen()).isEqualTo(UPDATED_OPEN);
        assertThat(testHistoricSiteData.isTrustData()).isEqualTo(UPDATED_TRUST_DATA);
        assertThat(testHistoricSiteData.getTimeStamp()).isEqualTo(UPDATED_TIME_STAMP);
        assertThat(testHistoricSiteData.isVerificationCheck()).isEqualTo(UPDATED_VERIFICATION_CHECK);
        assertThat(testHistoricSiteData.getTrueAvailable()).isEqualTo(UPDATED_TRUE_AVAILABLE);

        // Validate the HistoricSiteData in Elasticsearch
        HistoricSiteData historicSiteDataEs = historicSiteDataSearchRepository.findOne(testHistoricSiteData.getId());
        assertThat(testHistoricSiteData.getTimeStamp()).isEqualTo(testHistoricSiteData.getTimeStamp());
        assertThat(historicSiteDataEs).isEqualToIgnoringGivenFields(testHistoricSiteData, "timeStamp");
    }

    @Test
    @Transactional
    public void updateNonExistingHistoricSiteData() throws Exception {
        int databaseSizeBeforeUpdate = historicSiteDataRepository.findAll().size();

        // Create the HistoricSiteData
        HistoricSiteDataDTO historicSiteDataDTO = historicSiteDataMapper.toDto(historicSiteData);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHistoricSiteDataMockMvc.perform(put("/api/historic-site-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historicSiteDataDTO)))
            .andExpect(status().isCreated());

        // Validate the HistoricSiteData in the database
        List<HistoricSiteData> historicSiteDataList = historicSiteDataRepository.findAll();
        assertThat(historicSiteDataList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHistoricSiteData() throws Exception {
        // Initialize the database
        historicSiteDataRepository.saveAndFlush(historicSiteData);
        historicSiteDataSearchRepository.save(historicSiteData);
        int databaseSizeBeforeDelete = historicSiteDataRepository.findAll().size();

        // Get the historicSiteData
        restHistoricSiteDataMockMvc.perform(delete("/api/historic-site-data/{id}", historicSiteData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean historicSiteDataExistsInEs = historicSiteDataSearchRepository.exists(historicSiteData.getId());
        assertThat(historicSiteDataExistsInEs).isFalse();

        // Validate the database is empty
        List<HistoricSiteData> historicSiteDataList = historicSiteDataRepository.findAll();
        assertThat(historicSiteDataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHistoricSiteData() throws Exception {
        // Initialize the database
        historicSiteDataRepository.saveAndFlush(historicSiteData);
        historicSiteDataSearchRepository.save(historicSiteData);

        // Search the historicSiteData
        restHistoricSiteDataMockMvc.perform(get("/api/_search/historic-site-data?query=id:" + historicSiteData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historicSiteData.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalCapacity").value(hasItem(DEFAULT_TOTAL_CAPACITY)))
            .andExpect(jsonPath("$.[*].availability").value(hasItem(DEFAULT_AVAILABILITY)))
            .andExpect(jsonPath("$.[*].trend").value(hasItem(DEFAULT_TREND.toString())))
            .andExpect(jsonPath("$.[*].open").value(hasItem(DEFAULT_OPEN.booleanValue())))
            .andExpect(jsonPath("$.[*].trustData").value(hasItem(DEFAULT_TRUST_DATA.booleanValue())))
            .andExpect(jsonPath("$.[*].timeStamp").value(hasItem(DEFAULT_TIME_STAMP.toString())))
            .andExpect(jsonPath("$.[*].verificationCheck").value(hasItem(DEFAULT_VERIFICATION_CHECK.booleanValue())))
            .andExpect(jsonPath("$.[*].trueAvailable").value(hasItem(DEFAULT_TRUE_AVAILABLE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistoricSiteData.class);
        HistoricSiteData historicSiteData1 = new HistoricSiteData();
        historicSiteData1.setId(1L);
        HistoricSiteData historicSiteData2 = new HistoricSiteData();
        historicSiteData2.setId(historicSiteData1.getId());
        assertThat(historicSiteData1).isEqualTo(historicSiteData2);
        historicSiteData2.setId(2L);
        assertThat(historicSiteData1).isNotEqualTo(historicSiteData2);
        historicSiteData1.setId(null);
        assertThat(historicSiteData1).isNotEqualTo(historicSiteData2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistoricSiteDataDTO.class);
        HistoricSiteDataDTO historicSiteDataDTO1 = new HistoricSiteDataDTO();
        historicSiteDataDTO1.setId(1L);
        HistoricSiteDataDTO historicSiteDataDTO2 = new HistoricSiteDataDTO();
        assertThat(historicSiteDataDTO1).isNotEqualTo(historicSiteDataDTO2);
        historicSiteDataDTO2.setId(historicSiteDataDTO1.getId());
        assertThat(historicSiteDataDTO1).isEqualTo(historicSiteDataDTO2);
        historicSiteDataDTO2.setId(2L);
        assertThat(historicSiteDataDTO1).isNotEqualTo(historicSiteDataDTO2);
        historicSiteDataDTO1.setId(null);
        assertThat(historicSiteDataDTO1).isNotEqualTo(historicSiteDataDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(historicSiteDataMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(historicSiteDataMapper.fromId(null)).isNull();
    }
}
