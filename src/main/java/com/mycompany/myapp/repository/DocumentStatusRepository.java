package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DocumentStatus;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DocumentStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentStatusRepository extends JpaRepository<DocumentStatus, Long> {

}
