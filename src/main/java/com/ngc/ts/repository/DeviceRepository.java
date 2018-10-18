package com.ngc.ts.repository;

import com.ngc.ts.domain.Device;
import com.ngc.ts.domain.enumeration.LocationFunctions;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Device entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device findByDeviceName(String deviceName);
    List<Device> findBySiteId(Long id);
    List<Device> findByLocationfunction(LocationFunctions func);
}
