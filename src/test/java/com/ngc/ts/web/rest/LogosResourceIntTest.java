package com.ngc.ts.web.rest;

import com.ngc.ts.TpimsApp;

import com.ngc.ts.domain.Logos;
import com.ngc.ts.repository.LogosRepository;
import com.ngc.ts.repository.search.LogosSearchRepository;
import com.ngc.ts.service.dto.LogosDTO;
import com.ngc.ts.service.mapper.LogosMapper;
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

/**
 * Test class for the LogosResource REST controller.
 *
 * @see LogosResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TpimsApp.class)
public class LogosResourceIntTest {

    private static final String DEFAULT_LOGO_URL = "AAAAAAAAAA";
    private static final String UPDATED_LOGO_URL = "BBBBBBBBBB";

    @Autowired
    private LogosRepository logosRepository;

    @Autowired
    private LogosMapper logosMapper;

    @Autowired
    private LogosSearchRepository logosSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLogosMockMvc;

    private Logos logos;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LogosResource logosResource = new LogosResource(logosRepository, logosMapper, logosSearchRepository);
        this.restLogosMockMvc = MockMvcBuilders.standaloneSetup(logosResource)
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
    public static Logos createEntity(EntityManager em) {
        Logos logos = new Logos()
            .logoUrl(DEFAULT_LOGO_URL);
        return logos;
    }

    @Before
    public void initTest() {
        logosSearchRepository.deleteAll();
        logos = createEntity(em);
    }

    @Test
    @Transactional
    public void createLogos() throws Exception {
        int databaseSizeBeforeCreate = logosRepository.findAll().size();

        // Create the Logos
        LogosDTO logosDTO = logosMapper.toDto(logos);
        restLogosMockMvc.perform(post("/api/logos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logosDTO)))
            .andExpect(status().isCreated());

        // Validate the Logos in the database
        List<Logos> logosList = logosRepository.findAll();
        assertThat(logosList).hasSize(databaseSizeBeforeCreate + 1);
        Logos testLogos = logosList.get(logosList.size() - 1);
        assertThat(testLogos.getLogoUrl()).isEqualTo(DEFAULT_LOGO_URL);

        // Validate the Logos in Elasticsearch
        Logos logosEs = logosSearchRepository.findOne(testLogos.getId());
        assertThat(logosEs).isEqualToIgnoringGivenFields(testLogos);
    }

    @Test
    @Transactional
    public void createLogosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = logosRepository.findAll().size();

        // Create the Logos with an existing ID
        logos.setId(1L);
        LogosDTO logosDTO = logosMapper.toDto(logos);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogosMockMvc.perform(post("/api/logos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Logos in the database
        List<Logos> logosList = logosRepository.findAll();
        assertThat(logosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLogos() throws Exception {
        // Initialize the database
        logosRepository.saveAndFlush(logos);

        // Get all the logosList
        restLogosMockMvc.perform(get("/api/logos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logos.getId().intValue())))
            .andExpect(jsonPath("$.[*].logoUrl").value(hasItem(DEFAULT_LOGO_URL.toString())));
    }

    @Test
    @Transactional
    public void getLogos() throws Exception {
        // Initialize the database
        logosRepository.saveAndFlush(logos);

        // Get the logos
        restLogosMockMvc.perform(get("/api/logos/{id}", logos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(logos.getId().intValue()))
            .andExpect(jsonPath("$.logoUrl").value(DEFAULT_LOGO_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLogos() throws Exception {
        // Get the logos
        restLogosMockMvc.perform(get("/api/logos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLogos() throws Exception {
        // Initialize the database
        logosRepository.saveAndFlush(logos);
        logosSearchRepository.save(logos);
        int databaseSizeBeforeUpdate = logosRepository.findAll().size();

        // Update the logos
        Logos updatedLogos = logosRepository.findOne(logos.getId());
        // Disconnect from session so that the updates on updatedLogos are not directly saved in db
        em.detach(updatedLogos);
        updatedLogos
            .logoUrl(UPDATED_LOGO_URL);
        LogosDTO logosDTO = logosMapper.toDto(updatedLogos);

        restLogosMockMvc.perform(put("/api/logos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logosDTO)))
            .andExpect(status().isOk());

        // Validate the Logos in the database
        List<Logos> logosList = logosRepository.findAll();
        assertThat(logosList).hasSize(databaseSizeBeforeUpdate);
        Logos testLogos = logosList.get(logosList.size() - 1);
        assertThat(testLogos.getLogoUrl()).isEqualTo(UPDATED_LOGO_URL);

        // Validate the Logos in Elasticsearch
        Logos logosEs = logosSearchRepository.findOne(testLogos.getId());
        assertThat(logosEs).isEqualToIgnoringGivenFields(testLogos);
    }

    @Test
    @Transactional
    public void updateNonExistingLogos() throws Exception {
        int databaseSizeBeforeUpdate = logosRepository.findAll().size();

        // Create the Logos
        LogosDTO logosDTO = logosMapper.toDto(logos);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLogosMockMvc.perform(put("/api/logos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logosDTO)))
            .andExpect(status().isCreated());

        // Validate the Logos in the database
        List<Logos> logosList = logosRepository.findAll();
        assertThat(logosList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLogos() throws Exception {
        // Initialize the database
        logosRepository.saveAndFlush(logos);
        logosSearchRepository.save(logos);
        int databaseSizeBeforeDelete = logosRepository.findAll().size();

        // Get the logos
        restLogosMockMvc.perform(delete("/api/logos/{id}", logos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean logosExistsInEs = logosSearchRepository.exists(logos.getId());
        assertThat(logosExistsInEs).isFalse();

        // Validate the database is empty
        List<Logos> logosList = logosRepository.findAll();
        assertThat(logosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchLogos() throws Exception {
        // Initialize the database
        logosRepository.saveAndFlush(logos);
        logosSearchRepository.save(logos);

        // Search the logos
        restLogosMockMvc.perform(get("/api/_search/logos?query=id:" + logos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logos.getId().intValue())))
            .andExpect(jsonPath("$.[*].logoUrl").value(hasItem(DEFAULT_LOGO_URL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Logos.class);
        Logos logos1 = new Logos();
        logos1.setId(1L);
        Logos logos2 = new Logos();
        logos2.setId(logos1.getId());
        assertThat(logos1).isEqualTo(logos2);
        logos2.setId(2L);
        assertThat(logos1).isNotEqualTo(logos2);
        logos1.setId(null);
        assertThat(logos1).isNotEqualTo(logos2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogosDTO.class);
        LogosDTO logosDTO1 = new LogosDTO();
        logosDTO1.setId(1L);
        LogosDTO logosDTO2 = new LogosDTO();
        assertThat(logosDTO1).isNotEqualTo(logosDTO2);
        logosDTO2.setId(logosDTO1.getId());
        assertThat(logosDTO1).isEqualTo(logosDTO2);
        logosDTO2.setId(2L);
        assertThat(logosDTO1).isNotEqualTo(logosDTO2);
        logosDTO1.setId(null);
        assertThat(logosDTO1).isNotEqualTo(logosDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(logosMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(logosMapper.fromId(null)).isNull();
    }
}
