package com.ngc.ts.repository.search;

import com.ngc.ts.domain.HistoricSiteData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the HistoricSiteData entity.
 */
public interface HistoricSiteDataSearchRepository extends ElasticsearchRepository<HistoricSiteData, Long> {
}
