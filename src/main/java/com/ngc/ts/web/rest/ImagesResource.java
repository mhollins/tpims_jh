package com.ngc.ts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ngc.ts.domain.Images;

import com.ngc.ts.repository.ImagesRepository;
import com.ngc.ts.repository.search.ImagesSearchRepository;
import com.ngc.ts.web.rest.errors.BadRequestAlertException;
import com.ngc.ts.web.rest.util.HeaderUtil;
import com.ngc.ts.web.rest.util.PaginationUtil;
import com.ngc.ts.service.dto.ImagesDTO;
import com.ngc.ts.service.mapper.ImagesMapper;
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
 * REST controller for managing Images.
 */
@RestController
@RequestMapping("/api")
public class ImagesResource {

    private final Logger log = LoggerFactory.getLogger(ImagesResource.class);

    private static final String ENTITY_NAME = "images";

    private final ImagesRepository imagesRepository;

    private final ImagesMapper imagesMapper;

    private final ImagesSearchRepository imagesSearchRepository;

    public ImagesResource(ImagesRepository imagesRepository, ImagesMapper imagesMapper, ImagesSearchRepository imagesSearchRepository) {
        this.imagesRepository = imagesRepository;
        this.imagesMapper = imagesMapper;
        this.imagesSearchRepository = imagesSearchRepository;
    }

    /**
     * POST  /images : Create a new images.
     *
     * @param imagesDTO the imagesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new imagesDTO, or with status 400 (Bad Request) if the images has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/images")
    @Timed
    public ResponseEntity<ImagesDTO> createImages(@RequestBody ImagesDTO imagesDTO) throws URISyntaxException {
        log.debug("REST request to save Images : {}", imagesDTO);
        if (imagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new images cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Images images = imagesMapper.toEntity(imagesDTO);
        images = imagesRepository.save(images);
        ImagesDTO result = imagesMapper.toDto(images);
        imagesSearchRepository.save(images);
        return ResponseEntity.created(new URI("/api/images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /images : Updates an existing images.
     *
     * @param imagesDTO the imagesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated imagesDTO,
     * or with status 400 (Bad Request) if the imagesDTO is not valid,
     * or with status 500 (Internal Server Error) if the imagesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/images")
    @Timed
    public ResponseEntity<ImagesDTO> updateImages(@RequestBody ImagesDTO imagesDTO) throws URISyntaxException {
        log.debug("REST request to update Images : {}", imagesDTO);
        if (imagesDTO.getId() == null) {
            return createImages(imagesDTO);
        }
        Images images = imagesMapper.toEntity(imagesDTO);
        images = imagesRepository.save(images);
        ImagesDTO result = imagesMapper.toDto(images);
        imagesSearchRepository.save(images);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, imagesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /images : get all the images.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of images in body
     */
    @GetMapping("/images")
    @Timed
    public ResponseEntity<List<ImagesDTO>> getAllImages(Pageable pageable) {
        log.debug("REST request to get a page of Images");
        Page<Images> page = imagesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/images");
        return new ResponseEntity<>(imagesMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /images/:id : get the "id" images.
     *
     * @param id the id of the imagesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the imagesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/images/{id}")
    @Timed
    public ResponseEntity<ImagesDTO> getImages(@PathVariable Long id) {
        log.debug("REST request to get Images : {}", id);
        Images images = imagesRepository.findOne(id);
        ImagesDTO imagesDTO = imagesMapper.toDto(images);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(imagesDTO));
    }

    /**
     * DELETE  /images/:id : delete the "id" images.
     *
     * @param id the id of the imagesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/images/{id}")
    @Timed
    public ResponseEntity<Void> deleteImages(@PathVariable Long id) {
        log.debug("REST request to delete Images : {}", id);
        imagesRepository.delete(id);
        imagesSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/images?query=:query : search for the images corresponding
     * to the query.
     *
     * @param query the query of the images search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/images")
    @Timed
    public ResponseEntity<List<ImagesDTO>> searchImages(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Images for query {}", query);
        Page<Images> page = imagesSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/images");
        return new ResponseEntity<>(imagesMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

}
