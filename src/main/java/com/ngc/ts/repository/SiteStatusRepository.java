package com.ngc.ts.repository;

import com.ngc.ts.domain.SiteStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SiteStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SiteStatusRepository extends JpaRepository<SiteStatus, Long> {

}
