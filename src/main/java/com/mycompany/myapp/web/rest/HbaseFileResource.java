package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.HbaseFile;
import com.mycompany.myapp.repository.HbaseFileRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.HbaseFile}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class HbaseFileResource {

    private final Logger log = LoggerFactory.getLogger(HbaseFileResource.class);

    private static final String ENTITY_NAME = "hbaseFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HbaseFileRepository hbaseFileRepository;

    public HbaseFileResource(HbaseFileRepository hbaseFileRepository) {
        this.hbaseFileRepository = hbaseFileRepository;
    }

    /**
     * {@code POST  /hbase-files} : Create a new hbaseFile.
     *
     * @param hbaseFile the hbaseFile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hbaseFile, or with status {@code 400 (Bad Request)} if the hbaseFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hbase-files")
    public ResponseEntity<HbaseFile> createHbaseFile(@Valid @RequestBody HbaseFile hbaseFile) throws URISyntaxException {
        log.debug("REST request to save HbaseFile : {}", hbaseFile);
        if (hbaseFile.getId() != null) {
            throw new BadRequestAlertException("A new hbaseFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HbaseFile result = hbaseFileRepository.save(hbaseFile);
        return ResponseEntity.created(new URI("/api/hbase-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hbase-files} : Updates an existing hbaseFile.
     *
     * @param hbaseFile the hbaseFile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hbaseFile,
     * or with status {@code 400 (Bad Request)} if the hbaseFile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hbaseFile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hbase-files")
    public ResponseEntity<HbaseFile> updateHbaseFile(@Valid @RequestBody HbaseFile hbaseFile) throws URISyntaxException {
        log.debug("REST request to update HbaseFile : {}", hbaseFile);
        if (hbaseFile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HbaseFile result = hbaseFileRepository.save(hbaseFile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hbaseFile.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /hbase-files} : get all the hbaseFiles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hbaseFiles in body.
     */
    @GetMapping("/hbase-files")
    public List<HbaseFile> getAllHbaseFiles() {
        log.debug("REST request to get all HbaseFiles");
        return hbaseFileRepository.findAll();
    }

    /**
     * {@code GET  /hbase-files/:id} : get the "id" hbaseFile.
     *
     * @param id the id of the hbaseFile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hbaseFile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hbase-files/{id}")
    public ResponseEntity<HbaseFile> getHbaseFile(@PathVariable Long id) {
        log.debug("REST request to get HbaseFile : {}", id);
        Optional<HbaseFile> hbaseFile = hbaseFileRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(hbaseFile);
    }

    /**
     * {@code DELETE  /hbase-files/:id} : delete the "id" hbaseFile.
     *
     * @param id the id of the hbaseFile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hbase-files/{id}")
    public ResponseEntity<Void> deleteHbaseFile(@PathVariable Long id) {
        log.debug("REST request to delete HbaseFile : {}", id);
        hbaseFileRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
