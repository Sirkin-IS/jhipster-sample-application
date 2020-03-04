package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.DocumentStatus;
import com.mycompany.myapp.repository.DocumentStatusRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DocumentStatusResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class DocumentStatusResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DocumentStatusRepository documentStatusRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restDocumentStatusMockMvc;

    private DocumentStatus documentStatus;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentStatusResource documentStatusResource = new DocumentStatusResource(documentStatusRepository);
        this.restDocumentStatusMockMvc = MockMvcBuilders.standaloneSetup(documentStatusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentStatus createEntity(EntityManager em) {
        DocumentStatus documentStatus = new DocumentStatus()
            .name(DEFAULT_NAME);
        return documentStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentStatus createUpdatedEntity(EntityManager em) {
        DocumentStatus documentStatus = new DocumentStatus()
            .name(UPDATED_NAME);
        return documentStatus;
    }

    @BeforeEach
    public void initTest() {
        documentStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentStatus() throws Exception {
        int databaseSizeBeforeCreate = documentStatusRepository.findAll().size();

        // Create the DocumentStatus
        restDocumentStatusMockMvc.perform(post("/api/document-statuses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentStatus)))
            .andExpect(status().isCreated());

        // Validate the DocumentStatus in the database
        List<DocumentStatus> documentStatusList = documentStatusRepository.findAll();
        assertThat(documentStatusList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentStatus testDocumentStatus = documentStatusList.get(documentStatusList.size() - 1);
        assertThat(testDocumentStatus.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDocumentStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentStatusRepository.findAll().size();

        // Create the DocumentStatus with an existing ID
        documentStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentStatusMockMvc.perform(post("/api/document-statuses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentStatus)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentStatus in the database
        List<DocumentStatus> documentStatusList = documentStatusRepository.findAll();
        assertThat(documentStatusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentStatusRepository.findAll().size();
        // set the field null
        documentStatus.setName(null);

        // Create the DocumentStatus, which fails.

        restDocumentStatusMockMvc.perform(post("/api/document-statuses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentStatus)))
            .andExpect(status().isBadRequest());

        List<DocumentStatus> documentStatusList = documentStatusRepository.findAll();
        assertThat(documentStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDocumentStatuses() throws Exception {
        // Initialize the database
        documentStatusRepository.saveAndFlush(documentStatus);

        // Get all the documentStatusList
        restDocumentStatusMockMvc.perform(get("/api/document-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getDocumentStatus() throws Exception {
        // Initialize the database
        documentStatusRepository.saveAndFlush(documentStatus);

        // Get the documentStatus
        restDocumentStatusMockMvc.perform(get("/api/document-statuses/{id}", documentStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingDocumentStatus() throws Exception {
        // Get the documentStatus
        restDocumentStatusMockMvc.perform(get("/api/document-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentStatus() throws Exception {
        // Initialize the database
        documentStatusRepository.saveAndFlush(documentStatus);

        int databaseSizeBeforeUpdate = documentStatusRepository.findAll().size();

        // Update the documentStatus
        DocumentStatus updatedDocumentStatus = documentStatusRepository.findById(documentStatus.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentStatus are not directly saved in db
        em.detach(updatedDocumentStatus);
        updatedDocumentStatus
            .name(UPDATED_NAME);

        restDocumentStatusMockMvc.perform(put("/api/document-statuses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocumentStatus)))
            .andExpect(status().isOk());

        // Validate the DocumentStatus in the database
        List<DocumentStatus> documentStatusList = documentStatusRepository.findAll();
        assertThat(documentStatusList).hasSize(databaseSizeBeforeUpdate);
        DocumentStatus testDocumentStatus = documentStatusList.get(documentStatusList.size() - 1);
        assertThat(testDocumentStatus.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentStatus() throws Exception {
        int databaseSizeBeforeUpdate = documentStatusRepository.findAll().size();

        // Create the DocumentStatus

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentStatusMockMvc.perform(put("/api/document-statuses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentStatus)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentStatus in the database
        List<DocumentStatus> documentStatusList = documentStatusRepository.findAll();
        assertThat(documentStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocumentStatus() throws Exception {
        // Initialize the database
        documentStatusRepository.saveAndFlush(documentStatus);

        int databaseSizeBeforeDelete = documentStatusRepository.findAll().size();

        // Delete the documentStatus
        restDocumentStatusMockMvc.perform(delete("/api/document-statuses/{id}", documentStatus.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocumentStatus> documentStatusList = documentStatusRepository.findAll();
        assertThat(documentStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
