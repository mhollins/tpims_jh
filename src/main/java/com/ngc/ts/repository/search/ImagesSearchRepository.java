package com.ngc.ts.repository.search;

import com.ngc.ts.domain.Images;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Images entity.
 */
public interface ImagesSearchRepository extends ElasticsearchRepository<Images, Long> {
}
