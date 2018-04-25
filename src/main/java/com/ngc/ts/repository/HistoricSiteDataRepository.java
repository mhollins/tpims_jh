package com.ngc.ts.repository;

import com.ngc.ts.domain.HistoricSiteData;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the HistoricSiteData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistoricSiteDataRepository extends JpaRepository<HistoricSiteData, Long> {

}
