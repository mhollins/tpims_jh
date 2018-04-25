package com.ngc.ts.repository.search;

import com.ngc.ts.domain.County;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the County entity.
 */
public interface CountySearchRepository extends ElasticsearchRepository<County, Long> {
}
