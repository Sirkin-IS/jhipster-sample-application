package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.DocumentStatus;
import com.mycompany.myapp.repository.DocumentStatusRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.DocumentStatus}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DocumentStatusResource {

    private final Logger log = LoggerFactory.getLogger(DocumentStatusResource.class);

    private static final String ENTITY_NAME = "documentStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentStatusRepository documentStatusRepository;

    public DocumentStatusResource(DocumentStatusRepository documentStatusRepository) {
        this.documentStatusRepository = documentStatusRepository;
    }

    /**
     * {@code POST  /document-statuses} : Create a new documentStatus.
     *
     * @param documentStatus the documentStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentStatus, or with status {@code 400 (Bad Request)} if the documentStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/document-statuses")
    public ResponseEntity<DocumentStatus> createDocumentStatus(@Valid @RequestBody DocumentStatus documentStatus) throws URISyntaxException {
        log.debug("REST request to save DocumentStatus : {}", documentStatus);
        if (documentStatus.getId() != null) {
            throw new BadRequestAlertException("A new documentStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentStatus result = documentStatusRepository.save(documentStatus);
        return ResponseEntity.created(new URI("/api/document-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /document-statuses} : Updates an existing documentStatus.
     *
     * @param documentStatus the documentStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentStatus,
     * or with status {@code 400 (Bad Request)} if the documentStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/document-statuses")
    public ResponseEntity<DocumentStatus> updateDocumentStatus(@Valid @RequestBody DocumentStatus documentStatus) throws URISyntaxException {
        log.debug("REST request to update DocumentStatus : {}", documentStatus);
        if (documentStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentStatus result = documentStatusRepository.save(documentStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, documentStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /document-statuses} : get all the documentStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentStatuses in body.
     */
    @GetMapping("/document-statuses")
    public List<DocumentStatus> getAllDocumentStatuses() {
        log.debug("REST request to get all DocumentStatuses");
        return documentStatusRepository.findAll();
    }

    /**
     * {@code GET  /document-statuses/:id} : get the "id" documentStatus.
     *
     * @param id the id of the documentStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/document-statuses/{id}")
    public ResponseEntity<DocumentStatus> getDocumentStatus(@PathVariable Long id) {
        log.debug("REST request to get DocumentStatus : {}", id);
        Optional<DocumentStatus> documentStatus = documentStatusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(documentStatus);
    }

    /**
     * {@code DELETE  /document-statuses/:id} : delete the "id" documentStatus.
     *
     * @param id the id of the documentStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/document-statuses/{id}")
    public ResponseEntity<Void> deleteDocumentStatus(@PathVariable Long id) {
        log.debug("REST request to delete DocumentStatus : {}", id);
        documentStatusRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
