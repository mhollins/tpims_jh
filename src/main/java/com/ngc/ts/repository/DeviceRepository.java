package com.ngc.ts.repository;

import com.ngc.ts.domain.Device;
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
}
