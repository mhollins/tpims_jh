package com.ngc.ts.web.rest;

import com.ngc.ts.TpimsApp;

import com.ngc.ts.domain.Site;
import com.ngc.ts.repository.SiteRepository;
import com.ngc.ts.repository.search.SiteSearchRepository;
import com.ngc.ts.service.dto.SiteDTO;
import com.ngc.ts.service.mapper.SiteMapper;
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

import com.ngc.ts.domain.enumeration.OwnerShipOptions;
/**
 * Test class for the SiteResource REST controller.
 *
 * @see SiteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TpimsApp.class)
public class SiteResourceIntTest {

    private static final String DEFAULT_MAASTO_SITE_ID = "AAAAAAAAAA";
    private static final String UPDATED_MAASTO_SITE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SITE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SITE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_TOTAL_CAPACITY = 1;
    private static final Integer UPDATED_TOTAL_CAPACITY = 2;

    private static final Integer DEFAULT_LOW_THRESHOLD = 1;
    private static final Integer UPDATED_LOW_THRESHOLD = 2;

    private static final Instant DEFAULT_STATIC_DATA_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STATIC_DATA_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final OwnerShipOptions DEFAULT_OWNERSHIP = OwnerShipOptions.PR;
    private static final OwnerShipOptions UPDATED_OWNERSHIP = OwnerShipOptions.PU;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private SiteMapper siteMapper;

    @Autowired
    private SiteSearchRepository siteSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSiteMockMvc;

    private Site site;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SiteResource siteResource = new SiteResource(siteRepository, siteMapper, siteSearchRepository);
        this.restSiteMockMvc = MockMvcBuilders.standaloneSetup(siteResource)
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
    public static Site createEntity(EntityManager em) {
        Site site = new Site()
            .maastoSiteId(DEFAULT_MAASTO_SITE_ID)
            .siteName(DEFAULT_SITE_NAME)
            .totalCapacity(DEFAULT_TOTAL_CAPACITY)
            .lowThreshold(DEFAULT_LOW_THRESHOLD)
            .staticDataUpdated(DEFAULT_STATIC_DATA_UPDATED)
            .ownership(DEFAULT_OWNERSHIP);
        return site;
    }

    @Before
    public void initTest() {
        siteSearchRepository.deleteAll();
        site = createEntity(em);
    }

    @Test
    @Transactional
    public void createSite() throws Exception {
        int databaseSizeBeforeCreate = siteRepository.findAll().size();

        // Create the Site
        SiteDTO siteDTO = siteMapper.toDto(site);
        restSiteMockMvc.perform(post("/api/sites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siteDTO)))
            .andExpect(status().isCreated());

        // Validate the Site in the database
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeCreate + 1);
        Site testSite = siteList.get(siteList.size() - 1);
        assertThat(testSite.getMaastoSiteId()).isEqualTo(DEFAULT_MAASTO_SITE_ID);
        assertThat(testSite.getSiteName()).isEqualTo(DEFAULT_SITE_NAME);
        assertThat(testSite.getTotalCapacity()).isEqualTo(DEFAULT_TOTAL_CAPACITY);
        assertThat(testSite.getLowThreshold()).isEqualTo(DEFAULT_LOW_THRESHOLD);
        assertThat(testSite.getStaticDataUpdated()).isEqualTo(DEFAULT_STATIC_DATA_UPDATED);
        assertThat(testSite.getOwnership()).isEqualTo(DEFAULT_OWNERSHIP);

        // Validate the Site in Elasticsearch
        Site siteEs = siteSearchRepository.findOne(testSite.getId());
        assertThat(siteEs).isEqualToIgnoringGivenFields(testSite);
    }

    @Test
    @Transactional
    public void createSiteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = siteRepository.findAll().size();

        // Create the Site with an existing ID
        site.setId(1L);
        SiteDTO siteDTO = siteMapper.toDto(site);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSiteMockMvc.perform(post("/api/sites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Site in the database
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMaastoSiteIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteRepository.findAll().size();
        // set the field null
        site.setMaastoSiteId(null);

        // Create the Site, which fails.
        SiteDTO siteDTO = siteMapper.toDto(site);

        restSiteMockMvc.perform(post("/api/sites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siteDTO)))
            .andExpect(status().isBadRequest());

        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSites() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList
        restSiteMockMvc.perform(get("/api/sites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(site.getId().intValue())))
            .andExpect(jsonPath("$.[*].maastoSiteId").value(hasItem(DEFAULT_MAASTO_SITE_ID.toString())))
            .andExpect(jsonPath("$.[*].siteName").value(hasItem(DEFAULT_SITE_NAME.toString())))
            .andExpect(jsonPath("$.[*].totalCapacity").value(hasItem(DEFAULT_TOTAL_CAPACITY)))
            .andExpect(jsonPath("$.[*].lowThreshold").value(hasItem(DEFAULT_LOW_THRESHOLD)))
            .andExpect(jsonPath("$.[*].staticDataUpdated").value(hasItem(DEFAULT_STATIC_DATA_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].ownership").value(hasItem(DEFAULT_OWNERSHIP.toString())));
    }

    @Test
    @Transactional
    public void getSite() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get the site
        restSiteMockMvc.perform(get("/api/sites/{id}", site.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(site.getId().intValue()))
            .andExpect(jsonPath("$.maastoSiteId").value(DEFAULT_MAASTO_SITE_ID.toString()))
            .andExpect(jsonPath("$.siteName").value(DEFAULT_SITE_NAME.toString()))
            .andExpect(jsonPath("$.totalCapacity").value(DEFAULT_TOTAL_CAPACITY))
            .andExpect(jsonPath("$.lowThreshold").value(DEFAULT_LOW_THRESHOLD))
            .andExpect(jsonPath("$.staticDataUpdated").value(DEFAULT_STATIC_DATA_UPDATED.toString()))
            .andExpect(jsonPath("$.ownership").value(DEFAULT_OWNERSHIP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSite() throws Exception {
        // Get the site
        restSiteMockMvc.perform(get("/api/sites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSite() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);
        siteSearchRepository.save(site);
        int databaseSizeBeforeUpdate = siteRepository.findAll().size();

        // Update the site
        Site updatedSite = siteRepository.findOne(site.getId());
        // Disconnect from session so that the updates on updatedSite are not directly saved in db
        em.detach(updatedSite);
        updatedSite
            .maastoSiteId(UPDATED_MAASTO_SITE_ID)
            .siteName(UPDATED_SITE_NAME)
            .totalCapacity(UPDATED_TOTAL_CAPACITY)
            .lowThreshold(UPDATED_LOW_THRESHOLD)
            .staticDataUpdated(UPDATED_STATIC_DATA_UPDATED)
            .ownership(UPDATED_OWNERSHIP);
        SiteDTO siteDTO = siteMapper.toDto(updatedSite);

        restSiteMockMvc.perform(put("/api/sites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siteDTO)))
            .andExpect(status().isOk());

        // Validate the Site in the database
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeUpdate);
        Site testSite = siteList.get(siteList.size() - 1);
        assertThat(testSite.getMaastoSiteId()).isEqualTo(UPDATED_MAASTO_SITE_ID);
        assertThat(testSite.getSiteName()).isEqualTo(UPDATED_SITE_NAME);
        assertThat(testSite.getTotalCapacity()).isEqualTo(UPDATED_TOTAL_CAPACITY);
        assertThat(testSite.getLowThreshold()).isEqualTo(UPDATED_LOW_THRESHOLD);
        assertThat(testSite.getStaticDataUpdated()).isEqualTo(UPDATED_STATIC_DATA_UPDATED);
        assertThat(testSite.getOwnership()).isEqualTo(UPDATED_OWNERSHIP);

        // Validate the Site in Elasticsearch
        Site siteEs = siteSearchRepository.findOne(testSite.getId());
        assertThat(siteEs).isEqualToIgnoringGivenFields(testSite);
    }

    @Test
    @Transactional
    public void updateNonExistingSite() throws Exception {
        int databaseSizeBeforeUpdate = siteRepository.findAll().size();

        // Create the Site
        SiteDTO siteDTO = siteMapper.toDto(site);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSiteMockMvc.perform(put("/api/sites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siteDTO)))
            .andExpect(status().isCreated());

        // Validate the Site in the database
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSite() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);
        siteSearchRepository.save(site);
        int databaseSizeBeforeDelete = siteRepository.findAll().size();

        // Get the site
        restSiteMockMvc.perform(delete("/api/sites/{id}", site.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean siteExistsInEs = siteSearchRepository.exists(site.getId());
        assertThat(siteExistsInEs).isFalse();

        // Validate the database is empty
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSite() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);
        siteSearchRepository.save(site);

        // Search the site
        restSiteMockMvc.perform(get("/api/_search/sites?query=id:" + site.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(site.getId().intValue())))
            .andExpect(jsonPath("$.[*].maastoSiteId").value(hasItem(DEFAULT_MAASTO_SITE_ID.toString())))
            .andExpect(jsonPath("$.[*].siteName").value(hasItem(DEFAULT_SITE_NAME.toString())))
            .andExpect(jsonPath("$.[*].totalCapacity").value(hasItem(DEFAULT_TOTAL_CAPACITY)))
            .andExpect(jsonPath("$.[*].lowThreshold").value(hasItem(DEFAULT_LOW_THRESHOLD)))
            .andExpect(jsonPath("$.[*].staticDataUpdated").value(hasItem(DEFAULT_STATIC_DATA_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].ownership").value(hasItem(DEFAULT_OWNERSHIP.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Site.class);
        Site site1 = new Site();
        site1.setId(1L);
        Site site2 = new Site();
        site2.setId(site1.getId());
        assertThat(site1).isEqualTo(site2);
        site2.setId(2L);
        assertThat(site1).isNotEqualTo(site2);
        site1.setId(null);
        assertThat(site1).isNotEqualTo(site2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiteDTO.class);
        SiteDTO siteDTO1 = new SiteDTO();
        siteDTO1.setId(1L);
        SiteDTO siteDTO2 = new SiteDTO();
        assertThat(siteDTO1).isNotEqualTo(siteDTO2);
        siteDTO2.setId(siteDTO1.getId());
        assertThat(siteDTO1).isEqualTo(siteDTO2);
        siteDTO2.setId(2L);
        assertThat(siteDTO1).isNotEqualTo(siteDTO2);
        siteDTO1.setId(null);
        assertThat(siteDTO1).isNotEqualTo(siteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(siteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(siteMapper.fromId(null)).isNull();
    }
}
