package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Operators;
import com.mycompany.myapp.repository.OperatorsRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Operators}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OperatorsResource {

    private final Logger log = LoggerFactory.getLogger(OperatorsResource.class);

    private static final String ENTITY_NAME = "operators";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OperatorsRepository operatorsRepository;

    public OperatorsResource(OperatorsRepository operatorsRepository) {
        this.operatorsRepository = operatorsRepository;
    }

    /**
     * {@code POST  /operators} : Create a new operators.
     *
     * @param operators the operators to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new operators, or with status {@code 400 (Bad Request)} if the operators has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/operators")
    public ResponseEntity<Operators> createOperators(@Valid @RequestBody Operators operators) throws URISyntaxException {
        log.debug("REST request to save Operators : {}", operators);
        if (operators.getId() != null) {
            throw new BadRequestAlertException("A new operators cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Operators result = operatorsRepository.save(operators);
        return ResponseEntity.created(new URI("/api/operators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /operators} : Updates an existing operators.
     *
     * @param operators the operators to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operators,
     * or with status {@code 400 (Bad Request)} if the operators is not valid,
     * or with status {@code 500 (Internal Server Error)} if the operators couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/operators")
    public ResponseEntity<Operators> updateOperators(@Valid @RequestBody Operators operators) throws URISyntaxException {
        log.debug("REST request to update Operators : {}", operators);
        if (operators.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Operators result = operatorsRepository.save(operators);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, operators.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /operators} : get all the operators.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of operators in body.
     */
    @GetMapping("/operators")
    public List<Operators> getAllOperators() {
        log.debug("REST request to get all Operators");
        return operatorsRepository.findAll();
    }

    /**
     * {@code GET  /operators/:id} : get the "id" operators.
     *
     * @param id the id of the operators to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the operators, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/operators/{id}")
    public ResponseEntity<Operators> getOperators(@PathVariable Long id) {
        log.debug("REST request to get Operators : {}", id);
        Optional<Operators> operators = operatorsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(operators);
    }

    /**
     * {@code DELETE  /operators/:id} : delete the "id" operators.
     *
     * @param id the id of the operators to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/operators/{id}")
    public ResponseEntity<Void> deleteOperators(@PathVariable Long id) {
        log.debug("REST request to delete Operators : {}", id);
        operatorsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
