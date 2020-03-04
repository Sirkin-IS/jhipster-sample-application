package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.CMS;
import com.mycompany.myapp.repository.CMSRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CMS}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CMSResource {

    private final Logger log = LoggerFactory.getLogger(CMSResource.class);

    private static final String ENTITY_NAME = "cMS";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CMSRepository cMSRepository;

    public CMSResource(CMSRepository cMSRepository) {
        this.cMSRepository = cMSRepository;
    }

    /**
     * {@code POST  /cms} : Create a new cMS.
     *
     * @param cMS the cMS to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cMS, or with status {@code 400 (Bad Request)} if the cMS has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cms")
    public ResponseEntity<CMS> createCMS(@Valid @RequestBody CMS cMS) throws URISyntaxException {
        log.debug("REST request to save CMS : {}", cMS);
        if (cMS.getId() != null) {
            throw new BadRequestAlertException("A new cMS cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CMS result = cMSRepository.save(cMS);
        return ResponseEntity.created(new URI("/api/cms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cms} : Updates an existing cMS.
     *
     * @param cMS the cMS to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cMS,
     * or with status {@code 400 (Bad Request)} if the cMS is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cMS couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cms")
    public ResponseEntity<CMS> updateCMS(@Valid @RequestBody CMS cMS) throws URISyntaxException {
        log.debug("REST request to update CMS : {}", cMS);
        if (cMS.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CMS result = cMSRepository.save(cMS);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cMS.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cms} : get all the cMS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cMS in body.
     */
    @GetMapping("/cms")
    public List<CMS> getAllCMS() {
        log.debug("REST request to get all CMS");
        return cMSRepository.findAll();
    }

    /**
     * {@code GET  /cms/:id} : get the "id" cMS.
     *
     * @param id the id of the cMS to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cMS, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cms/{id}")
    public ResponseEntity<CMS> getCMS(@PathVariable Long id) {
        log.debug("REST request to get CMS : {}", id);
        Optional<CMS> cMS = cMSRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cMS);
    }

    /**
     * {@code DELETE  /cms/:id} : delete the "id" cMS.
     *
     * @param id the id of the cMS to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cms/{id}")
    public ResponseEntity<Void> deleteCMS(@PathVariable Long id) {
        log.debug("REST request to delete CMS : {}", id);
        cMSRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
