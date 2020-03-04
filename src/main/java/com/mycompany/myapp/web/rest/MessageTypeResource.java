package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.MessageType;
import com.mycompany.myapp.repository.MessageTypeRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.MessageType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MessageTypeResource {

    private final Logger log = LoggerFactory.getLogger(MessageTypeResource.class);

    private static final String ENTITY_NAME = "messageType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MessageTypeRepository messageTypeRepository;

    public MessageTypeResource(MessageTypeRepository messageTypeRepository) {
        this.messageTypeRepository = messageTypeRepository;
    }

    /**
     * {@code POST  /message-types} : Create a new messageType.
     *
     * @param messageType the messageType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new messageType, or with status {@code 400 (Bad Request)} if the messageType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/message-types")
    public ResponseEntity<MessageType> createMessageType(@Valid @RequestBody MessageType messageType) throws URISyntaxException {
        log.debug("REST request to save MessageType : {}", messageType);
        if (messageType.getId() != null) {
            throw new BadRequestAlertException("A new messageType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MessageType result = messageTypeRepository.save(messageType);
        return ResponseEntity.created(new URI("/api/message-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /message-types} : Updates an existing messageType.
     *
     * @param messageType the messageType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated messageType,
     * or with status {@code 400 (Bad Request)} if the messageType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the messageType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/message-types")
    public ResponseEntity<MessageType> updateMessageType(@Valid @RequestBody MessageType messageType) throws URISyntaxException {
        log.debug("REST request to update MessageType : {}", messageType);
        if (messageType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MessageType result = messageTypeRepository.save(messageType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, messageType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /message-types} : get all the messageTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of messageTypes in body.
     */
    @GetMapping("/message-types")
    public List<MessageType> getAllMessageTypes() {
        log.debug("REST request to get all MessageTypes");
        return messageTypeRepository.findAll();
    }

    /**
     * {@code GET  /message-types/:id} : get the "id" messageType.
     *
     * @param id the id of the messageType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the messageType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/message-types/{id}")
    public ResponseEntity<MessageType> getMessageType(@PathVariable Long id) {
        log.debug("REST request to get MessageType : {}", id);
        Optional<MessageType> messageType = messageTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(messageType);
    }

    /**
     * {@code DELETE  /message-types/:id} : delete the "id" messageType.
     *
     * @param id the id of the messageType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/message-types/{id}")
    public ResponseEntity<Void> deleteMessageType(@PathVariable Long id) {
        log.debug("REST request to delete MessageType : {}", id);
        messageTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
