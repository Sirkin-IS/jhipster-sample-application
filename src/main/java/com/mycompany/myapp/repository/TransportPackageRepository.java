package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TransportPackage;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TransportPackage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransportPackageRepository extends JpaRepository<TransportPackage, Long> {

}
