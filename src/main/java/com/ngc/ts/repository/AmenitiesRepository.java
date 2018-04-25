package com.ngc.ts.repository;

import com.ngc.ts.domain.Amenities;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Amenities entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmenitiesRepository extends JpaRepository<Amenities, Long> {

}
