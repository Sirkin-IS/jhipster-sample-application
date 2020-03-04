package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.LogicalMessage;
import com.mycompany.myapp.repository.LogicalMessageRepository;
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
 * Integration tests for the {@link LogicalMessageResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class LogicalMessageResourceIT {

    private static final Integer DEFAULT_OWNER_ID = 1;
    private static final Integer UPDATED_OWNER_ID = 2;

    private static final String DEFAULT_DOCUMENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_DIRECTION_ID = 1;
    private static final Integer UPDATED_DIRECTION_ID = 2;

    private static final String DEFAULT_CMS_ID = "AAAAAAAAAA";
    private static final String UPDATED_CMS_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CMS_DIRECTORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CMS_DIRECTORY_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_TRANSPORT_PACKAGE_ID = 1;
    private static final Integer UPDATED_TRANSPORT_PACKAGE_ID = 2;

    private static final String DEFAULT_RECEIPT_ID = "AAAAAAAAAA";
    private static final String UPDATED_RECEIPT_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_MESSAGE_TYPE_ID = 1;
    private static final Integer UPDATED_MESSAGE_TYPE_ID = 2;

    private static final Integer DEFAULT_RESPONSE_CODE = 1;
    private static final Integer UPDATED_RESPONSE_CODE = 2;

    private static final Integer DEFAULT_ATTEMPS = 1;
    private static final Integer UPDATED_ATTEMPS = 2;

    private static final LocalDate DEFAULT_LAST_TIME_OF_ATTEMPS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_TIME_OF_ATTEMPS = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private LogicalMessageRepository logicalMessageRepository;

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

    private MockMvc restLogicalMessageMockMvc;

    private LogicalMessage logicalMessage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LogicalMessageResource logicalMessageResource = new LogicalMessageResource(logicalMessageRepository);
        this.restLogicalMessageMockMvc = MockMvcBuilders.standaloneSetup(logicalMessageResource)
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
    public static LogicalMessage createEntity(EntityManager em) {
        LogicalMessage logicalMessage = new LogicalMessage()
            .ownerId(DEFAULT_OWNER_ID)
            .documentId(DEFAULT_DOCUMENT_ID)
            .eventId(DEFAULT_EVENT_ID)
            .directionId(DEFAULT_DIRECTION_ID)
            .cmsId(DEFAULT_CMS_ID)
            .cmsDirectoryName(DEFAULT_CMS_DIRECTORY_NAME)
            .transportPackageId(DEFAULT_TRANSPORT_PACKAGE_ID)
            .receiptId(DEFAULT_RECEIPT_ID)
            .messageTypeId(DEFAULT_MESSAGE_TYPE_ID)
            .responseCode(DEFAULT_RESPONSE_CODE)
            .attemps(DEFAULT_ATTEMPS)
            .lastTimeOfAttemps(DEFAULT_LAST_TIME_OF_ATTEMPS)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return logicalMessage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LogicalMessage createUpdatedEntity(EntityManager em) {
        LogicalMessage logicalMessage = new LogicalMessage()
            .ownerId(UPDATED_OWNER_ID)
            .documentId(UPDATED_DOCUMENT_ID)
            .eventId(UPDATED_EVENT_ID)
            .directionId(UPDATED_DIRECTION_ID)
            .cmsId(UPDATED_CMS_ID)
            .cmsDirectoryName(UPDATED_CMS_DIRECTORY_NAME)
            .transportPackageId(UPDATED_TRANSPORT_PACKAGE_ID)
            .receiptId(UPDATED_RECEIPT_ID)
            .messageTypeId(UPDATED_MESSAGE_TYPE_ID)
            .responseCode(UPDATED_RESPONSE_CODE)
            .attemps(UPDATED_ATTEMPS)
            .lastTimeOfAttemps(UPDATED_LAST_TIME_OF_ATTEMPS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return logicalMessage;
    }

    @BeforeEach
    public void initTest() {
        logicalMessage = createEntity(em);
    }

    @Test
    @Transactional
    public void createLogicalMessage() throws Exception {
        int databaseSizeBeforeCreate = logicalMessageRepository.findAll().size();

        // Create the LogicalMessage
        restLogicalMessageMockMvc.perform(post("/api/logical-messages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logicalMessage)))
            .andExpect(status().isCreated());

        // Validate the LogicalMessage in the database
        List<LogicalMessage> logicalMessageList = logicalMessageRepository.findAll();
        assertThat(logicalMessageList).hasSize(databaseSizeBeforeCreate + 1);
        LogicalMessage testLogicalMessage = logicalMessageList.get(logicalMessageList.size() - 1);
        assertThat(testLogicalMessage.getOwnerId()).isEqualTo(DEFAULT_OWNER_ID);
        assertThat(testLogicalMessage.getDocumentId()).isEqualTo(DEFAULT_DOCUMENT_ID);
        assertThat(testLogicalMessage.getEventId()).isEqualTo(DEFAULT_EVENT_ID);
        assertThat(testLogicalMessage.getDirectionId()).isEqualTo(DEFAULT_DIRECTION_ID);
        assertThat(testLogicalMessage.getCmsId()).isEqualTo(DEFAULT_CMS_ID);
        assertThat(testLogicalMessage.getCmsDirectoryName()).isEqualTo(DEFAULT_CMS_DIRECTORY_NAME);
        assertThat(testLogicalMessage.getTransportPackageId()).isEqualTo(DEFAULT_TRANSPORT_PACKAGE_ID);
        assertThat(testLogicalMessage.getReceiptId()).isEqualTo(DEFAULT_RECEIPT_ID);
        assertThat(testLogicalMessage.getMessageTypeId()).isEqualTo(DEFAULT_MESSAGE_TYPE_ID);
        assertThat(testLogicalMessage.getResponseCode()).isEqualTo(DEFAULT_RESPONSE_CODE);
        assertThat(testLogicalMessage.getAttemps()).isEqualTo(DEFAULT_ATTEMPS);
        assertThat(testLogicalMessage.getLastTimeOfAttemps()).isEqualTo(DEFAULT_LAST_TIME_OF_ATTEMPS);
        assertThat(testLogicalMessage.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLogicalMessage.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createLogicalMessageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = logicalMessageRepository.findAll().size();

        // Create the LogicalMessage with an existing ID
        logicalMessage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogicalMessageMockMvc.perform(post("/api/logical-messages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logicalMessage)))
            .andExpect(status().isBadRequest());

        // Validate the LogicalMessage in the database
        List<LogicalMessage> logicalMessageList = logicalMessageRepository.findAll();
        assertThat(logicalMessageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkOwnerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = logicalMessageRepository.findAll().size();
        // set the field null
        logicalMessage.setOwnerId(null);

        // Create the LogicalMessage, which fails.

        restLogicalMessageMockMvc.perform(post("/api/logical-messages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logicalMessage)))
            .andExpect(status().isBadRequest());

        List<LogicalMessage> logicalMessageList = logicalMessageRepository.findAll();
        assertThat(logicalMessageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = logicalMessageRepository.findAll().size();
        // set the field null
        logicalMessage.setDocumentId(null);

        // Create the LogicalMessage, which fails.

        restLogicalMessageMockMvc.perform(post("/api/logical-messages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logicalMessage)))
            .andExpect(status().isBadRequest());

        List<LogicalMessage> logicalMessageList = logicalMessageRepository.findAll();
        assertThat(logicalMessageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDirectionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = logicalMessageRepository.findAll().size();
        // set the field null
        logicalMessage.setDirectionId(null);

        // Create the LogicalMessage, which fails.

        restLogicalMessageMockMvc.perform(post("/api/logical-messages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logicalMessage)))
            .andExpect(status().isBadRequest());

        List<LogicalMessage> logicalMessageList = logicalMessageRepository.findAll();
        assertThat(logicalMessageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCmsIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = logicalMessageRepository.findAll().size();
        // set the field null
        logicalMessage.setCmsId(null);

        // Create the LogicalMessage, which fails.

        restLogicalMessageMockMvc.perform(post("/api/logical-messages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logicalMessage)))
            .andExpect(status().isBadRequest());

        List<LogicalMessage> logicalMessageList = logicalMessageRepository.findAll();
        assertThat(logicalMessageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCmsDirectoryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = logicalMessageRepository.findAll().size();
        // set the field null
        logicalMessage.setCmsDirectoryName(null);

        // Create the LogicalMessage, which fails.

        restLogicalMessageMockMvc.perform(post("/api/logical-messages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logicalMessage)))
            .andExpect(status().isBadRequest());

        List<LogicalMessage> logicalMessageList = logicalMessageRepository.findAll();
        assertThat(logicalMessageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = logicalMessageRepository.findAll().size();
        // set the field null
        logicalMessage.setCreatedAt(null);

        // Create the LogicalMessage, which fails.

        restLogicalMessageMockMvc.perform(post("/api/logical-messages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logicalMessage)))
            .andExpect(status().isBadRequest());

        List<LogicalMessage> logicalMessageList = logicalMessageRepository.findAll();
        assertThat(logicalMessageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = logicalMessageRepository.findAll().size();
        // set the field null
        logicalMessage.setUpdatedAt(null);

        // Create the LogicalMessage, which fails.

        restLogicalMessageMockMvc.perform(post("/api/logical-messages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logicalMessage)))
            .andExpect(status().isBadRequest());

        List<LogicalMessage> logicalMessageList = logicalMessageRepository.findAll();
        assertThat(logicalMessageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLogicalMessages() throws Exception {
        // Initialize the database
        logicalMessageRepository.saveAndFlush(logicalMessage);

        // Get all the logicalMessageList
        restLogicalMessageMockMvc.perform(get("/api/logical-messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logicalMessage.getId().intValue())))
            .andExpect(jsonPath("$.[*].ownerId").value(hasItem(DEFAULT_OWNER_ID)))
            .andExpect(jsonPath("$.[*].documentId").value(hasItem(DEFAULT_DOCUMENT_ID)))
            .andExpect(jsonPath("$.[*].eventId").value(hasItem(DEFAULT_EVENT_ID)))
            .andExpect(jsonPath("$.[*].directionId").value(hasItem(DEFAULT_DIRECTION_ID)))
            .andExpect(jsonPath("$.[*].cmsId").value(hasItem(DEFAULT_CMS_ID)))
            .andExpect(jsonPath("$.[*].cmsDirectoryName").value(hasItem(DEFAULT_CMS_DIRECTORY_NAME)))
            .andExpect(jsonPath("$.[*].transportPackageId").value(hasItem(DEFAULT_TRANSPORT_PACKAGE_ID)))
            .andExpect(jsonPath("$.[*].receiptId").value(hasItem(DEFAULT_RECEIPT_ID)))
            .andExpect(jsonPath("$.[*].messageTypeId").value(hasItem(DEFAULT_MESSAGE_TYPE_ID)))
            .andExpect(jsonPath("$.[*].responseCode").value(hasItem(DEFAULT_RESPONSE_CODE)))
            .andExpect(jsonPath("$.[*].attemps").value(hasItem(DEFAULT_ATTEMPS)))
            .andExpect(jsonPath("$.[*].lastTimeOfAttemps").value(hasItem(DEFAULT_LAST_TIME_OF_ATTEMPS.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getLogicalMessage() throws Exception {
        // Initialize the database
        logicalMessageRepository.saveAndFlush(logicalMessage);

        // Get the logicalMessage
        restLogicalMessageMockMvc.perform(get("/api/logical-messages/{id}", logicalMessage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(logicalMessage.getId().intValue()))
            .andExpect(jsonPath("$.ownerId").value(DEFAULT_OWNER_ID))
            .andExpect(jsonPath("$.documentId").value(DEFAULT_DOCUMENT_ID))
            .andExpect(jsonPath("$.eventId").value(DEFAULT_EVENT_ID))
            .andExpect(jsonPath("$.directionId").value(DEFAULT_DIRECTION_ID))
            .andExpect(jsonPath("$.cmsId").value(DEFAULT_CMS_ID))
            .andExpect(jsonPath("$.cmsDirectoryName").value(DEFAULT_CMS_DIRECTORY_NAME))
            .andExpect(jsonPath("$.transportPackageId").value(DEFAULT_TRANSPORT_PACKAGE_ID))
            .andExpect(jsonPath("$.receiptId").value(DEFAULT_RECEIPT_ID))
            .andExpect(jsonPath("$.messageTypeId").value(DEFAULT_MESSAGE_TYPE_ID))
            .andExpect(jsonPath("$.responseCode").value(DEFAULT_RESPONSE_CODE))
            .andExpect(jsonPath("$.attemps").value(DEFAULT_ATTEMPS))
            .andExpect(jsonPath("$.lastTimeOfAttemps").value(DEFAULT_LAST_TIME_OF_ATTEMPS.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLogicalMessage() throws Exception {
        // Get the logicalMessage
        restLogicalMessageMockMvc.perform(get("/api/logical-messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLogicalMessage() throws Exception {
        // Initialize the database
        logicalMessageRepository.saveAndFlush(logicalMessage);

        int databaseSizeBeforeUpdate = logicalMessageRepository.findAll().size();

        // Update the logicalMessage
        LogicalMessage updatedLogicalMessage = logicalMessageRepository.findById(logicalMessage.getId()).get();
        // Disconnect from session so that the updates on updatedLogicalMessage are not directly saved in db
        em.detach(updatedLogicalMessage);
        updatedLogicalMessage
            .ownerId(UPDATED_OWNER_ID)
            .documentId(UPDATED_DOCUMENT_ID)
            .eventId(UPDATED_EVENT_ID)
            .directionId(UPDATED_DIRECTION_ID)
            .cmsId(UPDATED_CMS_ID)
            .cmsDirectoryName(UPDATED_CMS_DIRECTORY_NAME)
            .transportPackageId(UPDATED_TRANSPORT_PACKAGE_ID)
            .receiptId(UPDATED_RECEIPT_ID)
            .messageTypeId(UPDATED_MESSAGE_TYPE_ID)
            .responseCode(UPDATED_RESPONSE_CODE)
            .attemps(UPDATED_ATTEMPS)
            .lastTimeOfAttemps(UPDATED_LAST_TIME_OF_ATTEMPS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restLogicalMessageMockMvc.perform(put("/api/logical-messages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLogicalMessage)))
            .andExpect(status().isOk());

        // Validate the LogicalMessage in the database
        List<LogicalMessage> logicalMessageList = logicalMessageRepository.findAll();
        assertThat(logicalMessageList).hasSize(databaseSizeBeforeUpdate);
        LogicalMessage testLogicalMessage = logicalMessageList.get(logicalMessageList.size() - 1);
        assertThat(testLogicalMessage.getOwnerId()).isEqualTo(UPDATED_OWNER_ID);
        assertThat(testLogicalMessage.getDocumentId()).isEqualTo(UPDATED_DOCUMENT_ID);
        assertThat(testLogicalMessage.getEventId()).isEqualTo(UPDATED_EVENT_ID);
        assertThat(testLogicalMessage.getDirectionId()).isEqualTo(UPDATED_DIRECTION_ID);
        assertThat(testLogicalMessage.getCmsId()).isEqualTo(UPDATED_CMS_ID);
        assertThat(testLogicalMessage.getCmsDirectoryName()).isEqualTo(UPDATED_CMS_DIRECTORY_NAME);
        assertThat(testLogicalMessage.getTransportPackageId()).isEqualTo(UPDATED_TRANSPORT_PACKAGE_ID);
        assertThat(testLogicalMessage.getReceiptId()).isEqualTo(UPDATED_RECEIPT_ID);
        assertThat(testLogicalMessage.getMessageTypeId()).isEqualTo(UPDATED_MESSAGE_TYPE_ID);
        assertThat(testLogicalMessage.getResponseCode()).isEqualTo(UPDATED_RESPONSE_CODE);
        assertThat(testLogicalMessage.getAttemps()).isEqualTo(UPDATED_ATTEMPS);
        assertThat(testLogicalMessage.getLastTimeOfAttemps()).isEqualTo(UPDATED_LAST_TIME_OF_ATTEMPS);
        assertThat(testLogicalMessage.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLogicalMessage.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingLogicalMessage() throws Exception {
        int databaseSizeBeforeUpdate = logicalMessageRepository.findAll().size();

        // Create the LogicalMessage

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLogicalMessageMockMvc.perform(put("/api/logical-messages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logicalMessage)))
            .andExpect(status().isBadRequest());

        // Validate the LogicalMessage in the database
        List<LogicalMessage> logicalMessageList = logicalMessageRepository.findAll();
        assertThat(logicalMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLogicalMessage() throws Exception {
        // Initialize the database
        logicalMessageRepository.saveAndFlush(logicalMessage);

        int databaseSizeBeforeDelete = logicalMessageRepository.findAll().size();

        // Delete the logicalMessage
        restLogicalMessageMockMvc.perform(delete("/api/logical-messages/{id}", logicalMessage.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LogicalMessage> logicalMessageList = logicalMessageRepository.findAll();
        assertThat(logicalMessageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
