package com.ngc.ts.repository;

import com.ngc.ts.domain.DeviceData;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DeviceData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceDataRepository extends JpaRepository<DeviceData, Long> {

}
