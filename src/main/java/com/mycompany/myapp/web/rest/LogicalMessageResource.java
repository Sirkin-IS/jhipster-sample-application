package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.LogicalMessage;
import com.mycompany.myapp.repository.LogicalMessageRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.LogicalMessage}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LogicalMessageResource {

    private final Logger log = LoggerFactory.getLogger(LogicalMessageResource.class);

    private static final String ENTITY_NAME = "logicalMessage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LogicalMessageRepository logicalMessageRepository;

    public LogicalMessageResource(LogicalMessageRepository logicalMessageRepository) {
        this.logicalMessageRepository = logicalMessageRepository;
    }

    /**
     * {@code POST  /logical-messages} : Create a new logicalMessage.
     *
     * @param logicalMessage the logicalMessage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new logicalMessage, or with status {@code 400 (Bad Request)} if the logicalMessage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/logical-messages")
    public ResponseEntity<LogicalMessage> createLogicalMessage(@Valid @RequestBody LogicalMessage logicalMessage) throws URISyntaxException {
        log.debug("REST request to save LogicalMessage : {}", logicalMessage);
        if (logicalMessage.getId() != null) {
            throw new BadRequestAlertException("A new logicalMessage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LogicalMessage result = logicalMessageRepository.save(logicalMessage);
        return ResponseEntity.created(new URI("/api/logical-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /logical-messages} : Updates an existing logicalMessage.
     *
     * @param logicalMessage the logicalMessage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated logicalMessage,
     * or with status {@code 400 (Bad Request)} if the logicalMessage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the logicalMessage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/logical-messages")
    public ResponseEntity<LogicalMessage> updateLogicalMessage(@Valid @RequestBody LogicalMessage logicalMessage) throws URISyntaxException {
        log.debug("REST request to update LogicalMessage : {}", logicalMessage);
        if (logicalMessage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LogicalMessage result = logicalMessageRepository.save(logicalMessage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, logicalMessage.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /logical-messages} : get all the logicalMessages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of logicalMessages in body.
     */
    @GetMapping("/logical-messages")
    public List<LogicalMessage> getAllLogicalMessages() {
        log.debug("REST request to get all LogicalMessages");
        return logicalMessageRepository.findAll();
    }

    /**
     * {@code GET  /logical-messages/:id} : get the "id" logicalMessage.
     *
     * @param id the id of the logicalMessage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the logicalMessage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/logical-messages/{id}")
    public ResponseEntity<LogicalMessage> getLogicalMessage(@PathVariable Long id) {
        log.debug("REST request to get LogicalMessage : {}", id);
        Optional<LogicalMessage> logicalMessage = logicalMessageRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(logicalMessage);
    }

    /**
     * {@code DELETE  /logical-messages/:id} : delete the "id" logicalMessage.
     *
     * @param id the id of the logicalMessage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/logical-messages/{id}")
    public ResponseEntity<Void> deleteLogicalMessage(@PathVariable Long id) {
        log.debug("REST request to delete LogicalMessage : {}", id);
        logicalMessageRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
