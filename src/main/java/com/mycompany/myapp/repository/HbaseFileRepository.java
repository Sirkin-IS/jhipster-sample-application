package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.HbaseFile;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the HbaseFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HbaseFileRepository extends JpaRepository<HbaseFile, Long> {

}
