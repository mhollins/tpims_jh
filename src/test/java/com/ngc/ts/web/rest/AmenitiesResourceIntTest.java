package com.ngc.ts.web.rest;

import com.ngc.ts.TpimsApp;

import com.ngc.ts.domain.Amenities;
import com.ngc.ts.repository.AmenitiesRepository;
import com.ngc.ts.repository.search.AmenitiesSearchRepository;
import com.ngc.ts.service.dto.AmenitiesDTO;
import com.ngc.ts.service.mapper.AmenitiesMapper;
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
 * Test class for the AmenitiesResource REST controller.
 *
 * @see AmenitiesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TpimsApp.class)
public class AmenitiesResourceIntTest {

    private static final String DEFAULT_AMENITY = "AAAAAAAAAA";
    private static final String UPDATED_AMENITY = "BBBBBBBBBB";

    @Autowired
    private AmenitiesRepository amenitiesRepository;

    @Autowired
    private AmenitiesMapper amenitiesMapper;

    @Autowired
    private AmenitiesSearchRepository amenitiesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAmenitiesMockMvc;

    private Amenities amenities;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AmenitiesResource amenitiesResource = new AmenitiesResource(amenitiesRepository, amenitiesMapper, amenitiesSearchRepository);
        this.restAmenitiesMockMvc = MockMvcBuilders.standaloneSetup(amenitiesResource)
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
    public static Amenities createEntity(EntityManager em) {
        Amenities amenities = new Amenities()
            .amenity(DEFAULT_AMENITY);
        return amenities;
    }

    @Before
    public void initTest() {
        amenitiesSearchRepository.deleteAll();
        amenities = createEntity(em);
    }

    @Test
    @Transactional
    public void createAmenities() throws Exception {
        int databaseSizeBeforeCreate = amenitiesRepository.findAll().size();

        // Create the Amenities
        AmenitiesDTO amenitiesDTO = amenitiesMapper.toDto(amenities);
        restAmenitiesMockMvc.perform(post("/api/amenities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amenitiesDTO)))
            .andExpect(status().isCreated());

        // Validate the Amenities in the database
        List<Amenities> amenitiesList = amenitiesRepository.findAll();
        assertThat(amenitiesList).hasSize(databaseSizeBeforeCreate + 1);
        Amenities testAmenities = amenitiesList.get(amenitiesList.size() - 1);
        assertThat(testAmenities.getAmenity()).isEqualTo(DEFAULT_AMENITY);

        // Validate the Amenities in Elasticsearch
        Amenities amenitiesEs = amenitiesSearchRepository.findOne(testAmenities.getId());
        assertThat(amenitiesEs).isEqualToIgnoringGivenFields(testAmenities);
    }

    @Test
    @Transactional
    public void createAmenitiesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = amenitiesRepository.findAll().size();

        // Create the Amenities with an existing ID
        amenities.setId(1L);
        AmenitiesDTO amenitiesDTO = amenitiesMapper.toDto(amenities);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmenitiesMockMvc.perform(post("/api/amenities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amenitiesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Amenities in the database
        List<Amenities> amenitiesList = amenitiesRepository.findAll();
        assertThat(amenitiesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAmenities() throws Exception {
        // Initialize the database
        amenitiesRepository.saveAndFlush(amenities);

        // Get all the amenitiesList
        restAmenitiesMockMvc.perform(get("/api/amenities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amenities.getId().intValue())))
            .andExpect(jsonPath("$.[*].amenity").value(hasItem(DEFAULT_AMENITY.toString())));
    }

    @Test
    @Transactional
    public void getAmenities() throws Exception {
        // Initialize the database
        amenitiesRepository.saveAndFlush(amenities);

        // Get the amenities
        restAmenitiesMockMvc.perform(get("/api/amenities/{id}", amenities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(amenities.getId().intValue()))
            .andExpect(jsonPath("$.amenity").value(DEFAULT_AMENITY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAmenities() throws Exception {
        // Get the amenities
        restAmenitiesMockMvc.perform(get("/api/amenities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAmenities() throws Exception {
        // Initialize the database
        amenitiesRepository.saveAndFlush(amenities);
        amenitiesSearchRepository.save(amenities);
        int databaseSizeBeforeUpdate = amenitiesRepository.findAll().size();

        // Update the amenities
        Amenities updatedAmenities = amenitiesRepository.findOne(amenities.getId());
        // Disconnect from session so that the updates on updatedAmenities are not directly saved in db
        em.detach(updatedAmenities);
        updatedAmenities
            .amenity(UPDATED_AMENITY);
        AmenitiesDTO amenitiesDTO = amenitiesMapper.toDto(updatedAmenities);

        restAmenitiesMockMvc.perform(put("/api/amenities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amenitiesDTO)))
            .andExpect(status().isOk());

        // Validate the Amenities in the database
        List<Amenities> amenitiesList = amenitiesRepository.findAll();
        assertThat(amenitiesList).hasSize(databaseSizeBeforeUpdate);
        Amenities testAmenities = amenitiesList.get(amenitiesList.size() - 1);
        assertThat(testAmenities.getAmenity()).isEqualTo(UPDATED_AMENITY);

        // Validate the Amenities in Elasticsearch
        Amenities amenitiesEs = amenitiesSearchRepository.findOne(testAmenities.getId());
        assertThat(amenitiesEs).isEqualToIgnoringGivenFields(testAmenities);
    }

    @Test
    @Transactional
    public void updateNonExistingAmenities() throws Exception {
        int databaseSizeBeforeUpdate = amenitiesRepository.findAll().size();

        // Create the Amenities
        AmenitiesDTO amenitiesDTO = amenitiesMapper.toDto(amenities);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAmenitiesMockMvc.perform(put("/api/amenities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amenitiesDTO)))
            .andExpect(status().isCreated());

        // Validate the Amenities in the database
        List<Amenities> amenitiesList = amenitiesRepository.findAll();
        assertThat(amenitiesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAmenities() throws Exception {
        // Initialize the database
        amenitiesRepository.saveAndFlush(amenities);
        amenitiesSearchRepository.save(amenities);
        int databaseSizeBeforeDelete = amenitiesRepository.findAll().size();

        // Get the amenities
        restAmenitiesMockMvc.perform(delete("/api/amenities/{id}", amenities.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean amenitiesExistsInEs = amenitiesSearchRepository.exists(amenities.getId());
        assertThat(amenitiesExistsInEs).isFalse();

        // Validate the database is empty
        List<Amenities> amenitiesList = amenitiesRepository.findAll();
        assertThat(amenitiesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAmenities() throws Exception {
        // Initialize the database
        amenitiesRepository.saveAndFlush(amenities);
        amenitiesSearchRepository.save(amenities);

        // Search the amenities
        restAmenitiesMockMvc.perform(get("/api/_search/amenities?query=id:" + amenities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amenities.getId().intValue())))
            .andExpect(jsonPath("$.[*].amenity").value(hasItem(DEFAULT_AMENITY.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Amenities.class);
        Amenities amenities1 = new Amenities();
        amenities1.setId(1L);
        Amenities amenities2 = new Amenities();
        amenities2.setId(amenities1.getId());
        assertThat(amenities1).isEqualTo(amenities2);
        amenities2.setId(2L);
        assertThat(amenities1).isNotEqualTo(amenities2);
        amenities1.setId(null);
        assertThat(amenities1).isNotEqualTo(amenities2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmenitiesDTO.class);
        AmenitiesDTO amenitiesDTO1 = new AmenitiesDTO();
        amenitiesDTO1.setId(1L);
        AmenitiesDTO amenitiesDTO2 = new AmenitiesDTO();
        assertThat(amenitiesDTO1).isNotEqualTo(amenitiesDTO2);
        amenitiesDTO2.setId(amenitiesDTO1.getId());
        assertThat(amenitiesDTO1).isEqualTo(amenitiesDTO2);
        amenitiesDTO2.setId(2L);
        assertThat(amenitiesDTO1).isNotEqualTo(amenitiesDTO2);
        amenitiesDTO1.setId(null);
        assertThat(amenitiesDTO1).isNotEqualTo(amenitiesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(amenitiesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(amenitiesMapper.fromId(null)).isNull();
    }
}
