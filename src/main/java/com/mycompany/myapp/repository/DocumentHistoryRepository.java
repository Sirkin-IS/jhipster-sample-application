package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DocumentHistory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DocumentHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentHistoryRepository extends JpaRepository<DocumentHistory, Long> {

}
