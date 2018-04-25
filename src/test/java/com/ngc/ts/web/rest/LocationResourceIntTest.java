package com.ngc.ts.web.rest;

import com.ngc.ts.TpimsApp;

import com.ngc.ts.domain.Location;
import com.ngc.ts.repository.LocationRepository;
import com.ngc.ts.repository.search.LocationSearchRepository;
import com.ngc.ts.service.dto.LocationDTO;
import com.ngc.ts.service.mapper.LocationMapper;
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
import java.math.BigDecimal;
import java.util.List;

import static com.ngc.ts.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LocationResource REST controller.
 *
 * @see LocationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TpimsApp.class)
public class LocationResourceIntTest {

    private static final String DEFAULT_LOCATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_OWNER = "BBBBBBBBBB";

    private static final String DEFAULT_RELEVANT_HIGHWAY = "AAAAAAAAAA";
    private static final String UPDATED_RELEVANT_HIGHWAY = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE_POST = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_POST = "BBBBBBBBBB";

    private static final String DEFAULT_EXIT_ID = "AAAAAAAAAA";
    private static final String UPDATED_EXIT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECTION_OF_TRAVEL = "AAAAAAAAAA";
    private static final String UPDATED_DIRECTION_OF_TRAVEL = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_ADR = "AAAAAAAAAA";
    private static final String UPDATED_STREET_ADR = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TIME_ZONE = "AAAAAAAAAA";
    private static final String UPDATED_TIME_ZONE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_LATITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LATITUDE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LONGITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LONGITUDE = new BigDecimal(2);

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private LocationSearchRepository locationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLocationMockMvc;

    private Location location;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocationResource locationResource = new LocationResource(locationRepository, locationMapper, locationSearchRepository);
        this.restLocationMockMvc = MockMvcBuilders.standaloneSetup(locationResource)
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
    public static Location createEntity(EntityManager em) {
        Location location = new Location()
            .locationType(DEFAULT_LOCATION_TYPE)
            .locationOwner(DEFAULT_LOCATION_OWNER)
            .relevantHighway(DEFAULT_RELEVANT_HIGHWAY)
            .referencePost(DEFAULT_REFERENCE_POST)
            .exitId(DEFAULT_EXIT_ID)
            .directionOfTravel(DEFAULT_DIRECTION_OF_TRAVEL)
            .streetAdr(DEFAULT_STREET_ADR)
            .city(DEFAULT_CITY)
            .postalCode(DEFAULT_POSTAL_CODE)
            .timeZone(DEFAULT_TIME_ZONE)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE);
        return location;
    }

    @Before
    public void initTest() {
        locationSearchRepository.deleteAll();
        location = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocation() throws Exception {
        int databaseSizeBeforeCreate = locationRepository.findAll().size();

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);
        restLocationMockMvc.perform(post("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationDTO)))
            .andExpect(status().isCreated());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeCreate + 1);
        Location testLocation = locationList.get(locationList.size() - 1);
        assertThat(testLocation.getLocationType()).isEqualTo(DEFAULT_LOCATION_TYPE);
        assertThat(testLocation.getLocationOwner()).isEqualTo(DEFAULT_LOCATION_OWNER);
        assertThat(testLocation.getRelevantHighway()).isEqualTo(DEFAULT_RELEVANT_HIGHWAY);
        assertThat(testLocation.getReferencePost()).isEqualTo(DEFAULT_REFERENCE_POST);
        assertThat(testLocation.getExitId()).isEqualTo(DEFAULT_EXIT_ID);
        assertThat(testLocation.getDirectionOfTravel()).isEqualTo(DEFAULT_DIRECTION_OF_TRAVEL);
        assertThat(testLocation.getStreetAdr()).isEqualTo(DEFAULT_STREET_ADR);
        assertThat(testLocation.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testLocation.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testLocation.getTimeZone()).isEqualTo(DEFAULT_TIME_ZONE);
        assertThat(testLocation.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testLocation.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);

        // Validate the Location in Elasticsearch
        Location locationEs = locationSearchRepository.findOne(testLocation.getId());
        assertThat(locationEs).isEqualToIgnoringGivenFields(testLocation);
    }

    @Test
    @Transactional
    public void createLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = locationRepository.findAll().size();

        // Create the Location with an existing ID
        location.setId(1L);
        LocationDTO locationDTO = locationMapper.toDto(location);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationMockMvc.perform(post("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLocations() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList
        restLocationMockMvc.perform(get("/api/locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(location.getId().intValue())))
            .andExpect(jsonPath("$.[*].locationType").value(hasItem(DEFAULT_LOCATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].locationOwner").value(hasItem(DEFAULT_LOCATION_OWNER.toString())))
            .andExpect(jsonPath("$.[*].relevantHighway").value(hasItem(DEFAULT_RELEVANT_HIGHWAY.toString())))
            .andExpect(jsonPath("$.[*].referencePost").value(hasItem(DEFAULT_REFERENCE_POST.toString())))
            .andExpect(jsonPath("$.[*].exitId").value(hasItem(DEFAULT_EXIT_ID.toString())))
            .andExpect(jsonPath("$.[*].directionOfTravel").value(hasItem(DEFAULT_DIRECTION_OF_TRAVEL.toString())))
            .andExpect(jsonPath("$.[*].streetAdr").value(hasItem(DEFAULT_STREET_ADR.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].timeZone").value(hasItem(DEFAULT_TIME_ZONE.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.intValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.intValue())));
    }

    @Test
    @Transactional
    public void getLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get the location
        restLocationMockMvc.perform(get("/api/locations/{id}", location.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(location.getId().intValue()))
            .andExpect(jsonPath("$.locationType").value(DEFAULT_LOCATION_TYPE.toString()))
            .andExpect(jsonPath("$.locationOwner").value(DEFAULT_LOCATION_OWNER.toString()))
            .andExpect(jsonPath("$.relevantHighway").value(DEFAULT_RELEVANT_HIGHWAY.toString()))
            .andExpect(jsonPath("$.referencePost").value(DEFAULT_REFERENCE_POST.toString()))
            .andExpect(jsonPath("$.exitId").value(DEFAULT_EXIT_ID.toString()))
            .andExpect(jsonPath("$.directionOfTravel").value(DEFAULT_DIRECTION_OF_TRAVEL.toString()))
            .andExpect(jsonPath("$.streetAdr").value(DEFAULT_STREET_ADR.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.timeZone").value(DEFAULT_TIME_ZONE.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.intValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLocation() throws Exception {
        // Get the location
        restLocationMockMvc.perform(get("/api/locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);
        locationSearchRepository.save(location);
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();

        // Update the location
        Location updatedLocation = locationRepository.findOne(location.getId());
        // Disconnect from session so that the updates on updatedLocation are not directly saved in db
        em.detach(updatedLocation);
        updatedLocation
            .locationType(UPDATED_LOCATION_TYPE)
            .locationOwner(UPDATED_LOCATION_OWNER)
            .relevantHighway(UPDATED_RELEVANT_HIGHWAY)
            .referencePost(UPDATED_REFERENCE_POST)
            .exitId(UPDATED_EXIT_ID)
            .directionOfTravel(UPDATED_DIRECTION_OF_TRAVEL)
            .streetAdr(UPDATED_STREET_ADR)
            .city(UPDATED_CITY)
            .postalCode(UPDATED_POSTAL_CODE)
            .timeZone(UPDATED_TIME_ZONE)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
        LocationDTO locationDTO = locationMapper.toDto(updatedLocation);

        restLocationMockMvc.perform(put("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationDTO)))
            .andExpect(status().isOk());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
        Location testLocation = locationList.get(locationList.size() - 1);
        assertThat(testLocation.getLocationType()).isEqualTo(UPDATED_LOCATION_TYPE);
        assertThat(testLocation.getLocationOwner()).isEqualTo(UPDATED_LOCATION_OWNER);
        assertThat(testLocation.getRelevantHighway()).isEqualTo(UPDATED_RELEVANT_HIGHWAY);
        assertThat(testLocation.getReferencePost()).isEqualTo(UPDATED_REFERENCE_POST);
        assertThat(testLocation.getExitId()).isEqualTo(UPDATED_EXIT_ID);
        assertThat(testLocation.getDirectionOfTravel()).isEqualTo(UPDATED_DIRECTION_OF_TRAVEL);
        assertThat(testLocation.getStreetAdr()).isEqualTo(UPDATED_STREET_ADR);
        assertThat(testLocation.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testLocation.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testLocation.getTimeZone()).isEqualTo(UPDATED_TIME_ZONE);
        assertThat(testLocation.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testLocation.getLongitude()).isEqualTo(UPDATED_LONGITUDE);

        // Validate the Location in Elasticsearch
        Location locationEs = locationSearchRepository.findOne(testLocation.getId());
        assertThat(locationEs).isEqualToIgnoringGivenFields(testLocation);
    }

    @Test
    @Transactional
    public void updateNonExistingLocation() throws Exception {
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLocationMockMvc.perform(put("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationDTO)))
            .andExpect(status().isCreated());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);
        locationSearchRepository.save(location);
        int databaseSizeBeforeDelete = locationRepository.findAll().size();

        // Get the location
        restLocationMockMvc.perform(delete("/api/locations/{id}", location.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean locationExistsInEs = locationSearchRepository.exists(location.getId());
        assertThat(locationExistsInEs).isFalse();

        // Validate the database is empty
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);
        locationSearchRepository.save(location);

        // Search the location
        restLocationMockMvc.perform(get("/api/_search/locations?query=id:" + location.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(location.getId().intValue())))
            .andExpect(jsonPath("$.[*].locationType").value(hasItem(DEFAULT_LOCATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].locationOwner").value(hasItem(DEFAULT_LOCATION_OWNER.toString())))
            .andExpect(jsonPath("$.[*].relevantHighway").value(hasItem(DEFAULT_RELEVANT_HIGHWAY.toString())))
            .andExpect(jsonPath("$.[*].referencePost").value(hasItem(DEFAULT_REFERENCE_POST.toString())))
            .andExpect(jsonPath("$.[*].exitId").value(hasItem(DEFAULT_EXIT_ID.toString())))
            .andExpect(jsonPath("$.[*].directionOfTravel").value(hasItem(DEFAULT_DIRECTION_OF_TRAVEL.toString())))
            .andExpect(jsonPath("$.[*].streetAdr").value(hasItem(DEFAULT_STREET_ADR.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].timeZone").value(hasItem(DEFAULT_TIME_ZONE.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.intValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Location.class);
        Location location1 = new Location();
        location1.setId(1L);
        Location location2 = new Location();
        location2.setId(location1.getId());
        assertThat(location1).isEqualTo(location2);
        location2.setId(2L);
        assertThat(location1).isNotEqualTo(location2);
        location1.setId(null);
        assertThat(location1).isNotEqualTo(location2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationDTO.class);
        LocationDTO locationDTO1 = new LocationDTO();
        locationDTO1.setId(1L);
        LocationDTO locationDTO2 = new LocationDTO();
        assertThat(locationDTO1).isNotEqualTo(locationDTO2);
        locationDTO2.setId(locationDTO1.getId());
        assertThat(locationDTO1).isEqualTo(locationDTO2);
        locationDTO2.setId(2L);
        assertThat(locationDTO1).isNotEqualTo(locationDTO2);
        locationDTO1.setId(null);
        assertThat(locationDTO1).isNotEqualTo(locationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(locationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(locationMapper.fromId(null)).isNull();
    }
}
