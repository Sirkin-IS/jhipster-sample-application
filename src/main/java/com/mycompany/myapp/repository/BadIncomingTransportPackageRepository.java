package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BadIncomingTransportPackage;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BadIncomingTransportPackage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BadIncomingTransportPackageRepository extends JpaRepository<BadIncomingTransportPackage, Long> {

}
