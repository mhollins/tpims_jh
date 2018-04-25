package com.ngc.ts.repository.search;

import com.ngc.ts.domain.SiteStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SiteStatus entity.
 */
public interface SiteStatusSearchRepository extends ElasticsearchRepository<SiteStatus, Long> {
}
