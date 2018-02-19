package com.ngc.ts.repository;

import com.ngc.ts.domain.Logos;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Logos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogosRepository extends JpaRepository<Logos, Long> {

}
