package com.ngc.ts.repository;

import com.ngc.ts.domain.Images;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Set;


/**
 * Spring Data JPA repository for the Images entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImagesRepository extends JpaRepository<Images, Long> {
    List<Images> findBySiteId(Long id);
}
