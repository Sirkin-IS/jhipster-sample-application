package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.TransportPackageRepeat;
import com.mycompany.myapp.repository.TransportPackageRepeatRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.TransportPackageRepeat}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TransportPackageRepeatResource {

    private final Logger log = LoggerFactory.getLogger(TransportPackageRepeatResource.class);

    private static final String ENTITY_NAME = "transportPackageRepeat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransportPackageRepeatRepository transportPackageRepeatRepository;

    public TransportPackageRepeatResource(TransportPackageRepeatRepository transportPackageRepeatRepository) {
        this.transportPackageRepeatRepository = transportPackageRepeatRepository;
    }

    /**
     * {@code POST  /transport-package-repeats} : Create a new transportPackageRepeat.
     *
     * @param transportPackageRepeat the transportPackageRepeat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transportPackageRepeat, or with status {@code 400 (Bad Request)} if the transportPackageRepeat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transport-package-repeats")
    public ResponseEntity<TransportPackageRepeat> createTransportPackageRepeat(@Valid @RequestBody TransportPackageRepeat transportPackageRepeat) throws URISyntaxException {
        log.debug("REST request to save TransportPackageRepeat : {}", transportPackageRepeat);
        if (transportPackageRepeat.getId() != null) {
            throw new BadRequestAlertException("A new transportPackageRepeat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransportPackageRepeat result = transportPackageRepeatRepository.save(transportPackageRepeat);
        return ResponseEntity.created(new URI("/api/transport-package-repeats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transport-package-repeats} : Updates an existing transportPackageRepeat.
     *
     * @param transportPackageRepeat the transportPackageRepeat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transportPackageRepeat,
     * or with status {@code 400 (Bad Request)} if the transportPackageRepeat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transportPackageRepeat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transport-package-repeats")
    public ResponseEntity<TransportPackageRepeat> updateTransportPackageRepeat(@Valid @RequestBody TransportPackageRepeat transportPackageRepeat) throws URISyntaxException {
        log.debug("REST request to update TransportPackageRepeat : {}", transportPackageRepeat);
        if (transportPackageRepeat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransportPackageRepeat result = transportPackageRepeatRepository.save(transportPackageRepeat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transportPackageRepeat.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transport-package-repeats} : get all the transportPackageRepeats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transportPackageRepeats in body.
     */
    @GetMapping("/transport-package-repeats")
    public List<TransportPackageRepeat> getAllTransportPackageRepeats() {
        log.debug("REST request to get all TransportPackageRepeats");
        return transportPackageRepeatRepository.findAll();
    }

    /**
     * {@code GET  /transport-package-repeats/:id} : get the "id" transportPackageRepeat.
     *
     * @param id the id of the transportPackageRepeat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transportPackageRepeat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transport-package-repeats/{id}")
    public ResponseEntity<TransportPackageRepeat> getTransportPackageRepeat(@PathVariable Long id) {
        log.debug("REST request to get TransportPackageRepeat : {}", id);
        Optional<TransportPackageRepeat> transportPackageRepeat = transportPackageRepeatRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(transportPackageRepeat);
    }

    /**
     * {@code DELETE  /transport-package-repeats/:id} : delete the "id" transportPackageRepeat.
     *
     * @param id the id of the transportPackageRepeat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transport-package-repeats/{id}")
    public ResponseEntity<Void> deleteTransportPackageRepeat(@PathVariable Long id) {
        log.debug("REST request to delete TransportPackageRepeat : {}", id);
        transportPackageRepeatRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
