package com.ngc.ts.repository.search;

import com.ngc.ts.domain.DeviceData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DeviceData entity.
 */
public interface DeviceDataSearchRepository extends ElasticsearchRepository<DeviceData, Long> {
}
