package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.DocumentHistory;
import com.mycompany.myapp.repository.DocumentHistoryRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.DocumentHistory}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DocumentHistoryResource {

    private final Logger log = LoggerFactory.getLogger(DocumentHistoryResource.class);

    private static final String ENTITY_NAME = "documentHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentHistoryRepository documentHistoryRepository;

    public DocumentHistoryResource(DocumentHistoryRepository documentHistoryRepository) {
        this.documentHistoryRepository = documentHistoryRepository;
    }

    /**
     * {@code POST  /document-histories} : Create a new documentHistory.
     *
     * @param documentHistory the documentHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentHistory, or with status {@code 400 (Bad Request)} if the documentHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/document-histories")
    public ResponseEntity<DocumentHistory> createDocumentHistory(@Valid @RequestBody DocumentHistory documentHistory) throws URISyntaxException {
        log.debug("REST request to save DocumentHistory : {}", documentHistory);
        if (documentHistory.getId() != null) {
            throw new BadRequestAlertException("A new documentHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentHistory result = documentHistoryRepository.save(documentHistory);
        return ResponseEntity.created(new URI("/api/document-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /document-histories} : Updates an existing documentHistory.
     *
     * @param documentHistory the documentHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentHistory,
     * or with status {@code 400 (Bad Request)} if the documentHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/document-histories")
    public ResponseEntity<DocumentHistory> updateDocumentHistory(@Valid @RequestBody DocumentHistory documentHistory) throws URISyntaxException {
        log.debug("REST request to update DocumentHistory : {}", documentHistory);
        if (documentHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentHistory result = documentHistoryRepository.save(documentHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, documentHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /document-histories} : get all the documentHistories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentHistories in body.
     */
    @GetMapping("/document-histories")
    public List<DocumentHistory> getAllDocumentHistories() {
        log.debug("REST request to get all DocumentHistories");
        return documentHistoryRepository.findAll();
    }

    /**
     * {@code GET  /document-histories/:id} : get the "id" documentHistory.
     *
     * @param id the id of the documentHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/document-histories/{id}")
    public ResponseEntity<DocumentHistory> getDocumentHistory(@PathVariable Long id) {
        log.debug("REST request to get DocumentHistory : {}", id);
        Optional<DocumentHistory> documentHistory = documentHistoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(documentHistory);
    }

    /**
     * {@code DELETE  /document-histories/:id} : delete the "id" documentHistory.
     *
     * @param id the id of the documentHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/document-histories/{id}")
    public ResponseEntity<Void> deleteDocumentHistory(@PathVariable Long id) {
        log.debug("REST request to delete DocumentHistory : {}", id);
        documentHistoryRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
