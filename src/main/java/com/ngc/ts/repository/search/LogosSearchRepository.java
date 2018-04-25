package com.ngc.ts.repository.search;

import com.ngc.ts.domain.Logos;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Logos entity.
 */
public interface LogosSearchRepository extends ElasticsearchRepository<Logos, Long> {
}
