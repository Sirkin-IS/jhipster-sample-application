package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TransportPackageRepeat;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TransportPackageRepeat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransportPackageRepeatRepository extends JpaRepository<TransportPackageRepeat, Long> {

}
