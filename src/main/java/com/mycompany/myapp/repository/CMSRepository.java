package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CMS;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CMS entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CMSRepository extends JpaRepository<CMS, Long> {

}
