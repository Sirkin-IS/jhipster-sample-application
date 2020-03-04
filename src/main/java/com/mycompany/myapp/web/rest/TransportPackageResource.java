package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.TransportPackage;
import com.mycompany.myapp.repository.TransportPackageRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.TransportPackage}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TransportPackageResource {

    private final Logger log = LoggerFactory.getLogger(TransportPackageResource.class);

    private static final String ENTITY_NAME = "transportPackage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransportPackageRepository transportPackageRepository;

    public TransportPackageResource(TransportPackageRepository transportPackageRepository) {
        this.transportPackageRepository = transportPackageRepository;
    }

    /**
     * {@code POST  /transport-packages} : Create a new transportPackage.
     *
     * @param transportPackage the transportPackage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transportPackage, or with status {@code 400 (Bad Request)} if the transportPackage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transport-packages")
    public ResponseEntity<TransportPackage> createTransportPackage(@Valid @RequestBody TransportPackage transportPackage) throws URISyntaxException {
        log.debug("REST request to save TransportPackage : {}", transportPackage);
        if (transportPackage.getId() != null) {
            throw new BadRequestAlertException("A new transportPackage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransportPackage result = transportPackageRepository.save(transportPackage);
        return ResponseEntity.created(new URI("/api/transport-packages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transport-packages} : Updates an existing transportPackage.
     *
     * @param transportPackage the transportPackage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transportPackage,
     * or with status {@code 400 (Bad Request)} if the transportPackage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transportPackage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transport-packages")
    public ResponseEntity<TransportPackage> updateTransportPackage(@Valid @RequestBody TransportPackage transportPackage) throws URISyntaxException {
        log.debug("REST request to update TransportPackage : {}", transportPackage);
        if (transportPackage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransportPackage result = transportPackageRepository.save(transportPackage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transportPackage.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transport-packages} : get all the transportPackages.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transportPackages in body.
     */
    @GetMapping("/transport-packages")
    public List<TransportPackage> getAllTransportPackages(@RequestParam(required = false) String filter) {
        if ("transportpackageid-is-null".equals(filter)) {
            log.debug("REST request to get all TransportPackages where transportPackageId is null");
            return StreamSupport
                .stream(transportPackageRepository.findAll().spliterator(), false)
                .filter(transportPackage -> transportPackage.getTransportPackageId() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all TransportPackages");
        return transportPackageRepository.findAll();
    }

    /**
     * {@code GET  /transport-packages/:id} : get the "id" transportPackage.
     *
     * @param id the id of the transportPackage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transportPackage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transport-packages/{id}")
    public ResponseEntity<TransportPackage> getTransportPackage(@PathVariable Long id) {
        log.debug("REST request to get TransportPackage : {}", id);
        Optional<TransportPackage> transportPackage = transportPackageRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(transportPackage);
    }

    /**
     * {@code DELETE  /transport-packages/:id} : delete the "id" transportPackage.
     *
     * @param id the id of the transportPackage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transport-packages/{id}")
    public ResponseEntity<Void> deleteTransportPackage(@PathVariable Long id) {
        log.debug("REST request to delete TransportPackage : {}", id);
        transportPackageRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
