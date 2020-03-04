package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.TransportPackage;
import com.mycompany.myapp.repository.TransportPackageRepository;
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
 * Integration tests for the {@link TransportPackageResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class TransportPackageResourceIT {

    private static final Integer DEFAULT_TRANSPORT_PACKAGE_ID = 1;
    private static final Integer UPDATED_TRANSPORT_PACKAGE_ID = 2;

    private static final Integer DEFAULT_DIRECTION_ID = 1;
    private static final Integer UPDATED_DIRECTION_ID = 2;

    private static final String DEFAULT_OPERATOR_ID = "AAAAAAAAAA";
    private static final String UPDATED_OPERATOR_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANSWER_CODE = 1;
    private static final Integer UPDATED_ANSWER_CODE = 2;

    private static final String DEFAULT_ANSWER_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER_CONTENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_ATTEMPS = 1;
    private static final Integer UPDATED_ATTEMPS = 2;

    private static final LocalDate DEFAULT_LAST_TIME_OF_ATTEMPS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_TIME_OF_ATTEMPS = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TransportPackageRepository transportPackageRepository;

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

    private MockMvc restTransportPackageMockMvc;

    private TransportPackage transportPackage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransportPackageResource transportPackageResource = new TransportPackageResource(transportPackageRepository);
        this.restTransportPackageMockMvc = MockMvcBuilders.standaloneSetup(transportPackageResource)
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
    public static TransportPackage createEntity(EntityManager em) {
        TransportPackage transportPackage = new TransportPackage()
            .transportPackageId(DEFAULT_TRANSPORT_PACKAGE_ID)
            .directionId(DEFAULT_DIRECTION_ID)
            .operatorId(DEFAULT_OPERATOR_ID)
            .answerCode(DEFAULT_ANSWER_CODE)
            .answerContent(DEFAULT_ANSWER_CONTENT)
            .attemps(DEFAULT_ATTEMPS)
            .lastTimeOfAttemps(DEFAULT_LAST_TIME_OF_ATTEMPS)
            .content(DEFAULT_CONTENT)
            .createdAt(DEFAULT_CREATED_AT);
        return transportPackage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransportPackage createUpdatedEntity(EntityManager em) {
        TransportPackage transportPackage = new TransportPackage()
            .transportPackageId(UPDATED_TRANSPORT_PACKAGE_ID)
            .directionId(UPDATED_DIRECTION_ID)
            .operatorId(UPDATED_OPERATOR_ID)
            .answerCode(UPDATED_ANSWER_CODE)
            .answerContent(UPDATED_ANSWER_CONTENT)
            .attemps(UPDATED_ATTEMPS)
            .lastTimeOfAttemps(UPDATED_LAST_TIME_OF_ATTEMPS)
            .content(UPDATED_CONTENT)
            .createdAt(UPDATED_CREATED_AT);
        return transportPackage;
    }

    @BeforeEach
    public void initTest() {
        transportPackage = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransportPackage() throws Exception {
        int databaseSizeBeforeCreate = transportPackageRepository.findAll().size();

        // Create the TransportPackage
        restTransportPackageMockMvc.perform(post("/api/transport-packages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transportPackage)))
            .andExpect(status().isCreated());

        // Validate the TransportPackage in the database
        List<TransportPackage> transportPackageList = transportPackageRepository.findAll();
        assertThat(transportPackageList).hasSize(databaseSizeBeforeCreate + 1);
        TransportPackage testTransportPackage = transportPackageList.get(transportPackageList.size() - 1);
        assertThat(testTransportPackage.getTransportPackageId()).isEqualTo(DEFAULT_TRANSPORT_PACKAGE_ID);
        assertThat(testTransportPackage.getDirectionId()).isEqualTo(DEFAULT_DIRECTION_ID);
        assertThat(testTransportPackage.getOperatorId()).isEqualTo(DEFAULT_OPERATOR_ID);
        assertThat(testTransportPackage.getAnswerCode()).isEqualTo(DEFAULT_ANSWER_CODE);
        assertThat(testTransportPackage.getAnswerContent()).isEqualTo(DEFAULT_ANSWER_CONTENT);
        assertThat(testTransportPackage.getAttemps()).isEqualTo(DEFAULT_ATTEMPS);
        assertThat(testTransportPackage.getLastTimeOfAttemps()).isEqualTo(DEFAULT_LAST_TIME_OF_ATTEMPS);
        assertThat(testTransportPackage.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testTransportPackage.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    public void createTransportPackageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transportPackageRepository.findAll().size();

        // Create the TransportPackage with an existing ID
        transportPackage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransportPackageMockMvc.perform(post("/api/transport-packages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transportPackage)))
            .andExpect(status().isBadRequest());

        // Validate the TransportPackage in the database
        List<TransportPackage> transportPackageList = transportPackageRepository.findAll();
        assertThat(transportPackageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTransportPackageIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = transportPackageRepository.findAll().size();
        // set the field null
        transportPackage.setTransportPackageId(null);

        // Create the TransportPackage, which fails.

        restTransportPackageMockMvc.perform(post("/api/transport-packages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transportPackage)))
            .andExpect(status().isBadRequest());

        List<TransportPackage> transportPackageList = transportPackageRepository.findAll();
        assertThat(transportPackageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDirectionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = transportPackageRepository.findAll().size();
        // set the field null
        transportPackage.setDirectionId(null);

        // Create the TransportPackage, which fails.

        restTransportPackageMockMvc.perform(post("/api/transport-packages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transportPackage)))
            .andExpect(status().isBadRequest());

        List<TransportPackage> transportPackageList = transportPackageRepository.findAll();
        assertThat(transportPackageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOperatorIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = transportPackageRepository.findAll().size();
        // set the field null
        transportPackage.setOperatorId(null);

        // Create the TransportPackage, which fails.

        restTransportPackageMockMvc.perform(post("/api/transport-packages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transportPackage)))
            .andExpect(status().isBadRequest());

        List<TransportPackage> transportPackageList = transportPackageRepository.findAll();
        assertThat(transportPackageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = transportPackageRepository.findAll().size();
        // set the field null
        transportPackage.setCreatedAt(null);

        // Create the TransportPackage, which fails.

        restTransportPackageMockMvc.perform(post("/api/transport-packages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transportPackage)))
            .andExpect(status().isBadRequest());

        List<TransportPackage> transportPackageList = transportPackageRepository.findAll();
        assertThat(transportPackageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransportPackages() throws Exception {
        // Initialize the database
        transportPackageRepository.saveAndFlush(transportPackage);

        // Get all the transportPackageList
        restTransportPackageMockMvc.perform(get("/api/transport-packages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transportPackage.getId().intValue())))
            .andExpect(jsonPath("$.[*].transportPackageId").value(hasItem(DEFAULT_TRANSPORT_PACKAGE_ID)))
            .andExpect(jsonPath("$.[*].directionId").value(hasItem(DEFAULT_DIRECTION_ID)))
            .andExpect(jsonPath("$.[*].operatorId").value(hasItem(DEFAULT_OPERATOR_ID)))
            .andExpect(jsonPath("$.[*].answerCode").value(hasItem(DEFAULT_ANSWER_CODE)))
            .andExpect(jsonPath("$.[*].answerContent").value(hasItem(DEFAULT_ANSWER_CONTENT)))
            .andExpect(jsonPath("$.[*].attemps").value(hasItem(DEFAULT_ATTEMPS)))
            .andExpect(jsonPath("$.[*].lastTimeOfAttemps").value(hasItem(DEFAULT_LAST_TIME_OF_ATTEMPS.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getTransportPackage() throws Exception {
        // Initialize the database
        transportPackageRepository.saveAndFlush(transportPackage);

        // Get the transportPackage
        restTransportPackageMockMvc.perform(get("/api/transport-packages/{id}", transportPackage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transportPackage.getId().intValue()))
            .andExpect(jsonPath("$.transportPackageId").value(DEFAULT_TRANSPORT_PACKAGE_ID))
            .andExpect(jsonPath("$.directionId").value(DEFAULT_DIRECTION_ID))
            .andExpect(jsonPath("$.operatorId").value(DEFAULT_OPERATOR_ID))
            .andExpect(jsonPath("$.answerCode").value(DEFAULT_ANSWER_CODE))
            .andExpect(jsonPath("$.answerContent").value(DEFAULT_ANSWER_CONTENT))
            .andExpect(jsonPath("$.attemps").value(DEFAULT_ATTEMPS))
            .andExpect(jsonPath("$.lastTimeOfAttemps").value(DEFAULT_LAST_TIME_OF_ATTEMPS.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTransportPackage() throws Exception {
        // Get the transportPackage
        restTransportPackageMockMvc.perform(get("/api/transport-packages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransportPackage() throws Exception {
        // Initialize the database
        transportPackageRepository.saveAndFlush(transportPackage);

        int databaseSizeBeforeUpdate = transportPackageRepository.findAll().size();

        // Update the transportPackage
        TransportPackage updatedTransportPackage = transportPackageRepository.findById(transportPackage.getId()).get();
        // Disconnect from session so that the updates on updatedTransportPackage are not directly saved in db
        em.detach(updatedTransportPackage);
        updatedTransportPackage
            .transportPackageId(UPDATED_TRANSPORT_PACKAGE_ID)
            .directionId(UPDATED_DIRECTION_ID)
            .operatorId(UPDATED_OPERATOR_ID)
            .answerCode(UPDATED_ANSWER_CODE)
            .answerContent(UPDATED_ANSWER_CONTENT)
            .attemps(UPDATED_ATTEMPS)
            .lastTimeOfAttemps(UPDATED_LAST_TIME_OF_ATTEMPS)
            .content(UPDATED_CONTENT)
            .createdAt(UPDATED_CREATED_AT);

        restTransportPackageMockMvc.perform(put("/api/transport-packages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransportPackage)))
            .andExpect(status().isOk());

        // Validate the TransportPackage in the database
        List<TransportPackage> transportPackageList = transportPackageRepository.findAll();
        assertThat(transportPackageList).hasSize(databaseSizeBeforeUpdate);
        TransportPackage testTransportPackage = transportPackageList.get(transportPackageList.size() - 1);
        assertThat(testTransportPackage.getTransportPackageId()).isEqualTo(UPDATED_TRANSPORT_PACKAGE_ID);
        assertThat(testTransportPackage.getDirectionId()).isEqualTo(UPDATED_DIRECTION_ID);
        assertThat(testTransportPackage.getOperatorId()).isEqualTo(UPDATED_OPERATOR_ID);
        assertThat(testTransportPackage.getAnswerCode()).isEqualTo(UPDATED_ANSWER_CODE);
        assertThat(testTransportPackage.getAnswerContent()).isEqualTo(UPDATED_ANSWER_CONTENT);
        assertThat(testTransportPackage.getAttemps()).isEqualTo(UPDATED_ATTEMPS);
        assertThat(testTransportPackage.getLastTimeOfAttemps()).isEqualTo(UPDATED_LAST_TIME_OF_ATTEMPS);
        assertThat(testTransportPackage.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testTransportPackage.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingTransportPackage() throws Exception {
        int databaseSizeBeforeUpdate = transportPackageRepository.findAll().size();

        // Create the TransportPackage

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransportPackageMockMvc.perform(put("/api/transport-packages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transportPackage)))
            .andExpect(status().isBadRequest());

        // Validate the TransportPackage in the database
        List<TransportPackage> transportPackageList = transportPackageRepository.findAll();
        assertThat(transportPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransportPackage() throws Exception {
        // Initialize the database
        transportPackageRepository.saveAndFlush(transportPackage);

        int databaseSizeBeforeDelete = transportPackageRepository.findAll().size();

        // Delete the transportPackage
        restTransportPackageMockMvc.perform(delete("/api/transport-packages/{id}", transportPackage.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransportPackage> transportPackageList = transportPackageRepository.findAll();
        assertThat(transportPackageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
