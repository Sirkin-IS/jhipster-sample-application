package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.DocumentHistory;
import com.mycompany.myapp.repository.DocumentHistoryRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DocumentHistoryResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class DocumentHistoryResourceIT {

    private static final Integer DEFAULT_OWNER_ID = 1;
    private static final Integer UPDATED_OWNER_ID = 2;

    private static final Integer DEFAULT_DIRECTION_ID = 1;
    private static final Integer UPDATED_DIRECTION_ID = 2;

    private static final Integer DEFAULT_DOCUMENT_ID = 1;
    private static final Integer UPDATED_DOCUMENT_ID = 2;

    private static final Integer DEFAULT_STATUS_ID = 1;
    private static final Integer UPDATED_STATUS_ID = 2;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DocumentHistoryRepository documentHistoryRepository;

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

    private MockMvc restDocumentHistoryMockMvc;

    private DocumentHistory documentHistory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentHistoryResource documentHistoryResource = new DocumentHistoryResource(documentHistoryRepository);
        this.restDocumentHistoryMockMvc = MockMvcBuilders.standaloneSetup(documentHistoryResource)
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
    public static DocumentHistory createEntity(EntityManager em) {
        DocumentHistory documentHistory = new DocumentHistory()
            .ownerId(DEFAULT_OWNER_ID)
            .directionId(DEFAULT_DIRECTION_ID)
            .documentId(DEFAULT_DOCUMENT_ID)
            .statusId(DEFAULT_STATUS_ID)
            .date(DEFAULT_DATE);
        return documentHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentHistory createUpdatedEntity(EntityManager em) {
        DocumentHistory documentHistory = new DocumentHistory()
            .ownerId(UPDATED_OWNER_ID)
            .directionId(UPDATED_DIRECTION_ID)
            .documentId(UPDATED_DOCUMENT_ID)
            .statusId(UPDATED_STATUS_ID)
            .date(UPDATED_DATE);
        return documentHistory;
    }

    @BeforeEach
    public void initTest() {
        documentHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentHistory() throws Exception {
        int databaseSizeBeforeCreate = documentHistoryRepository.findAll().size();

        // Create the DocumentHistory
        restDocumentHistoryMockMvc.perform(post("/api/document-histories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentHistory)))
            .andExpect(status().isCreated());

        // Validate the DocumentHistory in the database
        List<DocumentHistory> documentHistoryList = documentHistoryRepository.findAll();
        assertThat(documentHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentHistory testDocumentHistory = documentHistoryList.get(documentHistoryList.size() - 1);
        assertThat(testDocumentHistory.getOwnerId()).isEqualTo(DEFAULT_OWNER_ID);
        assertThat(testDocumentHistory.getDirectionId()).isEqualTo(DEFAULT_DIRECTION_ID);
        assertThat(testDocumentHistory.getDocumentId()).isEqualTo(DEFAULT_DOCUMENT_ID);
        assertThat(testDocumentHistory.getStatusId()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testDocumentHistory.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createDocumentHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentHistoryRepository.findAll().size();

        // Create the DocumentHistory with an existing ID
        documentHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentHistoryMockMvc.perform(post("/api/document-histories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentHistory)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentHistory in the database
        List<DocumentHistory> documentHistoryList = documentHistoryRepository.findAll();
        assertThat(documentHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkOwnerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentHistoryRepository.findAll().size();
        // set the field null
        documentHistory.setOwnerId(null);

        // Create the DocumentHistory, which fails.

        restDocumentHistoryMockMvc.perform(post("/api/document-histories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentHistory)))
            .andExpect(status().isBadRequest());

        List<DocumentHistory> documentHistoryList = documentHistoryRepository.findAll();
        assertThat(documentHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDirectionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentHistoryRepository.findAll().size();
        // set the field null
        documentHistory.setDirectionId(null);

        // Create the DocumentHistory, which fails.

        restDocumentHistoryMockMvc.perform(post("/api/document-histories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentHistory)))
            .andExpect(status().isBadRequest());

        List<DocumentHistory> documentHistoryList = documentHistoryRepository.findAll();
        assertThat(documentHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentHistoryRepository.findAll().size();
        // set the field null
        documentHistory.setDocumentId(null);

        // Create the DocumentHistory, which fails.

        restDocumentHistoryMockMvc.perform(post("/api/document-histories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentHistory)))
            .andExpect(status().isBadRequest());

        List<DocumentHistory> documentHistoryList = documentHistoryRepository.findAll();
        assertThat(documentHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentHistoryRepository.findAll().size();
        // set the field null
        documentHistory.setStatusId(null);

        // Create the DocumentHistory, which fails.

        restDocumentHistoryMockMvc.perform(post("/api/document-histories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentHistory)))
            .andExpect(status().isBadRequest());

        List<DocumentHistory> documentHistoryList = documentHistoryRepository.findAll();
        assertThat(documentHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentHistoryRepository.findAll().size();
        // set the field null
        documentHistory.setDate(null);

        // Create the DocumentHistory, which fails.

        restDocumentHistoryMockMvc.perform(post("/api/document-histories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentHistory)))
            .andExpect(status().isBadRequest());

        List<DocumentHistory> documentHistoryList = documentHistoryRepository.findAll();
        assertThat(documentHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDocumentHistories() throws Exception {
        // Initialize the database
        documentHistoryRepository.saveAndFlush(documentHistory);

        // Get all the documentHistoryList
        restDocumentHistoryMockMvc.perform(get("/api/document-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].ownerId").value(hasItem(DEFAULT_OWNER_ID)))
            .andExpect(jsonPath("$.[*].directionId").value(hasItem(DEFAULT_DIRECTION_ID)))
            .andExpect(jsonPath("$.[*].documentId").value(hasItem(DEFAULT_DOCUMENT_ID)))
            .andExpect(jsonPath("$.[*].statusId").value(hasItem(DEFAULT_STATUS_ID)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getDocumentHistory() throws Exception {
        // Initialize the database
        documentHistoryRepository.saveAndFlush(documentHistory);

        // Get the documentHistory
        restDocumentHistoryMockMvc.perform(get("/api/document-histories/{id}", documentHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentHistory.getId().intValue()))
            .andExpect(jsonPath("$.ownerId").value(DEFAULT_OWNER_ID))
            .andExpect(jsonPath("$.directionId").value(DEFAULT_DIRECTION_ID))
            .andExpect(jsonPath("$.documentId").value(DEFAULT_DOCUMENT_ID))
            .andExpect(jsonPath("$.statusId").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDocumentHistory() throws Exception {
        // Get the documentHistory
        restDocumentHistoryMockMvc.perform(get("/api/document-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentHistory() throws Exception {
        // Initialize the database
        documentHistoryRepository.saveAndFlush(documentHistory);

        int databaseSizeBeforeUpdate = documentHistoryRepository.findAll().size();

        // Update the documentHistory
        DocumentHistory updatedDocumentHistory = documentHistoryRepository.findById(documentHistory.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentHistory are not directly saved in db
        em.detach(updatedDocumentHistory);
        updatedDocumentHistory
            .ownerId(UPDATED_OWNER_ID)
            .directionId(UPDATED_DIRECTION_ID)
            .documentId(UPDATED_DOCUMENT_ID)
            .statusId(UPDATED_STATUS_ID)
            .date(UPDATED_DATE);

        restDocumentHistoryMockMvc.perform(put("/api/document-histories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocumentHistory)))
            .andExpect(status().isOk());

        // Validate the DocumentHistory in the database
        List<DocumentHistory> documentHistoryList = documentHistoryRepository.findAll();
        assertThat(documentHistoryList).hasSize(databaseSizeBeforeUpdate);
        DocumentHistory testDocumentHistory = documentHistoryList.get(documentHistoryList.size() - 1);
        assertThat(testDocumentHistory.getOwnerId()).isEqualTo(UPDATED_OWNER_ID);
        assertThat(testDocumentHistory.getDirectionId()).isEqualTo(UPDATED_DIRECTION_ID);
        assertThat(testDocumentHistory.getDocumentId()).isEqualTo(UPDATED_DOCUMENT_ID);
        assertThat(testDocumentHistory.getStatusId()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testDocumentHistory.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentHistory() throws Exception {
        int databaseSizeBeforeUpdate = documentHistoryRepository.findAll().size();

        // Create the DocumentHistory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentHistoryMockMvc.perform(put("/api/document-histories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentHistory)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentHistory in the database
        List<DocumentHistory> documentHistoryList = documentHistoryRepository.findAll();
        assertThat(documentHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocumentHistory() throws Exception {
        // Initialize the database
        documentHistoryRepository.saveAndFlush(documentHistory);

        int databaseSizeBeforeDelete = documentHistoryRepository.findAll().size();

        // Delete the documentHistory
        restDocumentHistoryMockMvc.perform(delete("/api/document-histories/{id}", documentHistory.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocumentHistory> documentHistoryList = documentHistoryRepository.findAll();
        assertThat(documentHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
