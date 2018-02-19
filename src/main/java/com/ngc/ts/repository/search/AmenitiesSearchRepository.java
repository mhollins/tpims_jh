package com.ngc.ts.repository.search;

import com.ngc.ts.domain.Amenities;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Amenities entity.
 */
public interface AmenitiesSearchRepository extends ElasticsearchRepository<Amenities, Long> {
}
