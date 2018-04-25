package com.ngc.ts.web.rest;

import com.ngc.ts.TpimsApp;

import com.ngc.ts.domain.Images;
import com.ngc.ts.repository.ImagesRepository;
import com.ngc.ts.repository.search.ImagesSearchRepository;
import com.ngc.ts.service.dto.ImagesDTO;
import com.ngc.ts.service.mapper.ImagesMapper;
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
 * Test class for the ImagesResource REST controller.
 *
 * @see ImagesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TpimsApp.class)
public class ImagesResourceIntTest {

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    @Autowired
    private ImagesRepository imagesRepository;

    @Autowired
    private ImagesMapper imagesMapper;

    @Autowired
    private ImagesSearchRepository imagesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restImagesMockMvc;

    private Images images;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImagesResource imagesResource = new ImagesResource(imagesRepository, imagesMapper, imagesSearchRepository);
        this.restImagesMockMvc = MockMvcBuilders.standaloneSetup(imagesResource)
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
    public static Images createEntity(EntityManager em) {
        Images images = new Images()
            .imageUrl(DEFAULT_IMAGE_URL);
        return images;
    }

    @Before
    public void initTest() {
        imagesSearchRepository.deleteAll();
        images = createEntity(em);
    }

    @Test
    @Transactional
    public void createImages() throws Exception {
        int databaseSizeBeforeCreate = imagesRepository.findAll().size();

        // Create the Images
        ImagesDTO imagesDTO = imagesMapper.toDto(images);
        restImagesMockMvc.perform(post("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagesDTO)))
            .andExpect(status().isCreated());

        // Validate the Images in the database
        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeCreate + 1);
        Images testImages = imagesList.get(imagesList.size() - 1);
        assertThat(testImages.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);

        // Validate the Images in Elasticsearch
        Images imagesEs = imagesSearchRepository.findOne(testImages.getId());
        assertThat(imagesEs).isEqualToIgnoringGivenFields(testImages);
    }

    @Test
    @Transactional
    public void createImagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imagesRepository.findAll().size();

        // Create the Images with an existing ID
        images.setId(1L);
        ImagesDTO imagesDTO = imagesMapper.toDto(images);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImagesMockMvc.perform(post("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Images in the database
        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllImages() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);

        // Get all the imagesList
        restImagesMockMvc.perform(get("/api/images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(images.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())));
    }

    @Test
    @Transactional
    public void getImages() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);

        // Get the images
        restImagesMockMvc.perform(get("/api/images/{id}", images.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(images.getId().intValue()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingImages() throws Exception {
        // Get the images
        restImagesMockMvc.perform(get("/api/images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImages() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);
        imagesSearchRepository.save(images);
        int databaseSizeBeforeUpdate = imagesRepository.findAll().size();

        // Update the images
        Images updatedImages = imagesRepository.findOne(images.getId());
        // Disconnect from session so that the updates on updatedImages are not directly saved in db
        em.detach(updatedImages);
        updatedImages
            .imageUrl(UPDATED_IMAGE_URL);
        ImagesDTO imagesDTO = imagesMapper.toDto(updatedImages);

        restImagesMockMvc.perform(put("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagesDTO)))
            .andExpect(status().isOk());

        // Validate the Images in the database
        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeUpdate);
        Images testImages = imagesList.get(imagesList.size() - 1);
        assertThat(testImages.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);

        // Validate the Images in Elasticsearch
        Images imagesEs = imagesSearchRepository.findOne(testImages.getId());
        assertThat(imagesEs).isEqualToIgnoringGivenFields(testImages);
    }

    @Test
    @Transactional
    public void updateNonExistingImages() throws Exception {
        int databaseSizeBeforeUpdate = imagesRepository.findAll().size();

        // Create the Images
        ImagesDTO imagesDTO = imagesMapper.toDto(images);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restImagesMockMvc.perform(put("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagesDTO)))
            .andExpect(status().isCreated());

        // Validate the Images in the database
        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteImages() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);
        imagesSearchRepository.save(images);
        int databaseSizeBeforeDelete = imagesRepository.findAll().size();

        // Get the images
        restImagesMockMvc.perform(delete("/api/images/{id}", images.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean imagesExistsInEs = imagesSearchRepository.exists(images.getId());
        assertThat(imagesExistsInEs).isFalse();

        // Validate the database is empty
        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchImages() throws Exception {
        // Initialize the database
        imagesRepository.saveAndFlush(images);
        imagesSearchRepository.save(images);

        // Search the images
        restImagesMockMvc.perform(get("/api/_search/images?query=id:" + images.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(images.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Images.class);
        Images images1 = new Images();
        images1.setId(1L);
        Images images2 = new Images();
        images2.setId(images1.getId());
        assertThat(images1).isEqualTo(images2);
        images2.setId(2L);
        assertThat(images1).isNotEqualTo(images2);
        images1.setId(null);
        assertThat(images1).isNotEqualTo(images2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImagesDTO.class);
        ImagesDTO imagesDTO1 = new ImagesDTO();
        imagesDTO1.setId(1L);
        ImagesDTO imagesDTO2 = new ImagesDTO();
        assertThat(imagesDTO1).isNotEqualTo(imagesDTO2);
        imagesDTO2.setId(imagesDTO1.getId());
        assertThat(imagesDTO1).isEqualTo(imagesDTO2);
        imagesDTO2.setId(2L);
        assertThat(imagesDTO1).isNotEqualTo(imagesDTO2);
        imagesDTO1.setId(null);
        assertThat(imagesDTO1).isNotEqualTo(imagesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(imagesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(imagesMapper.fromId(null)).isNull();
    }
}
