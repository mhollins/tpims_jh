package com.ngc.ts.repository.search;

import com.ngc.ts.domain.Device;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Device entity.
 */
public interface DeviceSearchRepository extends ElasticsearchRepository<Device, Long> {
}
