package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.TransportPackageRepeat;
import com.mycompany.myapp.repository.TransportPackageRepeatRepository;
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
 * Integration tests for the {@link TransportPackageRepeatResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class TransportPackageRepeatResourceIT {

    private static final Integer DEFAULT_TRANSPORT_PACKAGE_ID = 1;
    private static final Integer UPDATED_TRANSPORT_PACKAGE_ID = 2;

    private static final Integer DEFAULT_REPEAT_NUM = 1;
    private static final Integer UPDATED_REPEAT_NUM = 2;

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ANSWER_CODE = 1;
    private static final Integer UPDATED_ANSWER_CODE = 2;

    private static final String DEFAULT_ANSWER_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER_CONTENT = "BBBBBBBBBB";

    @Autowired
    private TransportPackageRepeatRepository transportPackageRepeatRepository;

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

    private MockMvc restTransportPackageRepeatMockMvc;

    private TransportPackageRepeat transportPackageRepeat;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransportPackageRepeatResource transportPackageRepeatResource = new TransportPackageRepeatResource(transportPackageRepeatRepository);
        this.restTransportPackageRepeatMockMvc = MockMvcBuilders.standaloneSetup(transportPackageRepeatResource)
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
    public static TransportPackageRepeat createEntity(EntityManager em) {
        TransportPackageRepeat transportPackageRepeat = new TransportPackageRepeat()
            .transportPackageId(DEFAULT_TRANSPORT_PACKAGE_ID)
            .repeatNum(DEFAULT_REPEAT_NUM)
            .createdAt(DEFAULT_CREATED_AT)
            .answerCode(DEFAULT_ANSWER_CODE)
            .answerContent(DEFAULT_ANSWER_CONTENT);
        return transportPackageRepeat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransportPackageRepeat createUpdatedEntity(EntityManager em) {
        TransportPackageRepeat transportPackageRepeat = new TransportPackageRepeat()
            .transportPackageId(UPDATED_TRANSPORT_PACKAGE_ID)
            .repeatNum(UPDATED_REPEAT_NUM)
            .createdAt(UPDATED_CREATED_AT)
            .answerCode(UPDATED_ANSWER_CODE)
            .answerContent(UPDATED_ANSWER_CONTENT);
        return transportPackageRepeat;
    }

    @BeforeEach
    public void initTest() {
        transportPackageRepeat = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransportPackageRepeat() throws Exception {
        int databaseSizeBeforeCreate = transportPackageRepeatRepository.findAll().size();

        // Create the TransportPackageRepeat
        restTransportPackageRepeatMockMvc.perform(post("/api/transport-package-repeats")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transportPackageRepeat)))
            .andExpect(status().isCreated());

        // Validate the TransportPackageRepeat in the database
        List<TransportPackageRepeat> transportPackageRepeatList = transportPackageRepeatRepository.findAll();
        assertThat(transportPackageRepeatList).hasSize(databaseSizeBeforeCreate + 1);
        TransportPackageRepeat testTransportPackageRepeat = transportPackageRepeatList.get(transportPackageRepeatList.size() - 1);
        assertThat(testTransportPackageRepeat.getTransportPackageId()).isEqualTo(DEFAULT_TRANSPORT_PACKAGE_ID);
        assertThat(testTransportPackageRepeat.getRepeatNum()).isEqualTo(DEFAULT_REPEAT_NUM);
        assertThat(testTransportPackageRepeat.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testTransportPackageRepeat.getAnswerCode()).isEqualTo(DEFAULT_ANSWER_CODE);
        assertThat(testTransportPackageRepeat.getAnswerContent()).isEqualTo(DEFAULT_ANSWER_CONTENT);
    }

    @Test
    @Transactional
    public void createTransportPackageRepeatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transportPackageRepeatRepository.findAll().size();

        // Create the TransportPackageRepeat with an existing ID
        transportPackageRepeat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransportPackageRepeatMockMvc.perform(post("/api/transport-package-repeats")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transportPackageRepeat)))
            .andExpect(status().isBadRequest());

        // Validate the TransportPackageRepeat in the database
        List<TransportPackageRepeat> transportPackageRepeatList = transportPackageRepeatRepository.findAll();
        assertThat(transportPackageRepeatList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTransportPackageIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = transportPackageRepeatRepository.findAll().size();
        // set the field null
        transportPackageRepeat.setTransportPackageId(null);

        // Create the TransportPackageRepeat, which fails.

        restTransportPackageRepeatMockMvc.perform(post("/api/transport-package-repeats")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transportPackageRepeat)))
            .andExpect(status().isBadRequest());

        List<TransportPackageRepeat> transportPackageRepeatList = transportPackageRepeatRepository.findAll();
        assertThat(transportPackageRepeatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRepeatNumIsRequired() throws Exception {
        int databaseSizeBeforeTest = transportPackageRepeatRepository.findAll().size();
        // set the field null
        transportPackageRepeat.setRepeatNum(null);

        // Create the TransportPackageRepeat, which fails.

        restTransportPackageRepeatMockMvc.perform(post("/api/transport-package-repeats")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transportPackageRepeat)))
            .andExpect(status().isBadRequest());

        List<TransportPackageRepeat> transportPackageRepeatList = transportPackageRepeatRepository.findAll();
        assertThat(transportPackageRepeatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = transportPackageRepeatRepository.findAll().size();
        // set the field null
        transportPackageRepeat.setCreatedAt(null);

        // Create the TransportPackageRepeat, which fails.

        restTransportPackageRepeatMockMvc.perform(post("/api/transport-package-repeats")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transportPackageRepeat)))
            .andExpect(status().isBadRequest());

        List<TransportPackageRepeat> transportPackageRepeatList = transportPackageRepeatRepository.findAll();
        assertThat(transportPackageRepeatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnswerCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transportPackageRepeatRepository.findAll().size();
        // set the field null
        transportPackageRepeat.setAnswerCode(null);

        // Create the TransportPackageRepeat, which fails.

        restTransportPackageRepeatMockMvc.perform(post("/api/transport-package-repeats")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transportPackageRepeat)))
            .andExpect(status().isBadRequest());

        List<TransportPackageRepeat> transportPackageRepeatList = transportPackageRepeatRepository.findAll();
        assertThat(transportPackageRepeatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnswerContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = transportPackageRepeatRepository.findAll().size();
        // set the field null
        transportPackageRepeat.setAnswerContent(null);

        // Create the TransportPackageRepeat, which fails.

        restTransportPackageRepeatMockMvc.perform(post("/api/transport-package-repeats")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transportPackageRepeat)))
            .andExpect(status().isBadRequest());

        List<TransportPackageRepeat> transportPackageRepeatList = transportPackageRepeatRepository.findAll();
        assertThat(transportPackageRepeatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransportPackageRepeats() throws Exception {
        // Initialize the database
        transportPackageRepeatRepository.saveAndFlush(transportPackageRepeat);

        // Get all the transportPackageRepeatList
        restTransportPackageRepeatMockMvc.perform(get("/api/transport-package-repeats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transportPackageRepeat.getId().intValue())))
            .andExpect(jsonPath("$.[*].transportPackageId").value(hasItem(DEFAULT_TRANSPORT_PACKAGE_ID)))
            .andExpect(jsonPath("$.[*].repeatNum").value(hasItem(DEFAULT_REPEAT_NUM)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].answerCode").value(hasItem(DEFAULT_ANSWER_CODE)))
            .andExpect(jsonPath("$.[*].answerContent").value(hasItem(DEFAULT_ANSWER_CONTENT)));
    }
    
    @Test
    @Transactional
    public void getTransportPackageRepeat() throws Exception {
        // Initialize the database
        transportPackageRepeatRepository.saveAndFlush(transportPackageRepeat);

        // Get the transportPackageRepeat
        restTransportPackageRepeatMockMvc.perform(get("/api/transport-package-repeats/{id}", transportPackageRepeat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transportPackageRepeat.getId().intValue()))
            .andExpect(jsonPath("$.transportPackageId").value(DEFAULT_TRANSPORT_PACKAGE_ID))
            .andExpect(jsonPath("$.repeatNum").value(DEFAULT_REPEAT_NUM))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.answerCode").value(DEFAULT_ANSWER_CODE))
            .andExpect(jsonPath("$.answerContent").value(DEFAULT_ANSWER_CONTENT));
    }

    @Test
    @Transactional
    public void getNonExistingTransportPackageRepeat() throws Exception {
        // Get the transportPackageRepeat
        restTransportPackageRepeatMockMvc.perform(get("/api/transport-package-repeats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransportPackageRepeat() throws Exception {
        // Initialize the database
        transportPackageRepeatRepository.saveAndFlush(transportPackageRepeat);

        int databaseSizeBeforeUpdate = transportPackageRepeatRepository.findAll().size();

        // Update the transportPackageRepeat
        TransportPackageRepeat updatedTransportPackageRepeat = transportPackageRepeatRepository.findById(transportPackageRepeat.getId()).get();
        // Disconnect from session so that the updates on updatedTransportPackageRepeat are not directly saved in db
        em.detach(updatedTransportPackageRepeat);
        updatedTransportPackageRepeat
            .transportPackageId(UPDATED_TRANSPORT_PACKAGE_ID)
            .repeatNum(UPDATED_REPEAT_NUM)
            .createdAt(UPDATED_CREATED_AT)
            .answerCode(UPDATED_ANSWER_CODE)
            .answerContent(UPDATED_ANSWER_CONTENT);

        restTransportPackageRepeatMockMvc.perform(put("/api/transport-package-repeats")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransportPackageRepeat)))
            .andExpect(status().isOk());

        // Validate the TransportPackageRepeat in the database
        List<TransportPackageRepeat> transportPackageRepeatList = transportPackageRepeatRepository.findAll();
        assertThat(transportPackageRepeatList).hasSize(databaseSizeBeforeUpdate);
        TransportPackageRepeat testTransportPackageRepeat = transportPackageRepeatList.get(transportPackageRepeatList.size() - 1);
        assertThat(testTransportPackageRepeat.getTransportPackageId()).isEqualTo(UPDATED_TRANSPORT_PACKAGE_ID);
        assertThat(testTransportPackageRepeat.getRepeatNum()).isEqualTo(UPDATED_REPEAT_NUM);
        assertThat(testTransportPackageRepeat.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTransportPackageRepeat.getAnswerCode()).isEqualTo(UPDATED_ANSWER_CODE);
        assertThat(testTransportPackageRepeat.getAnswerContent()).isEqualTo(UPDATED_ANSWER_CONTENT);
    }

    @Test
    @Transactional
    public void updateNonExistingTransportPackageRepeat() throws Exception {
        int databaseSizeBeforeUpdate = transportPackageRepeatRepository.findAll().size();

        // Create the TransportPackageRepeat

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransportPackageRepeatMockMvc.perform(put("/api/transport-package-repeats")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transportPackageRepeat)))
            .andExpect(status().isBadRequest());

        // Validate the TransportPackageRepeat in the database
        List<TransportPackageRepeat> transportPackageRepeatList = transportPackageRepeatRepository.findAll();
        assertThat(transportPackageRepeatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransportPackageRepeat() throws Exception {
        // Initialize the database
        transportPackageRepeatRepository.saveAndFlush(transportPackageRepeat);

        int databaseSizeBeforeDelete = transportPackageRepeatRepository.findAll().size();

        // Delete the transportPackageRepeat
        restTransportPackageRepeatMockMvc.perform(delete("/api/transport-package-repeats/{id}", transportPackageRepeat.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransportPackageRepeat> transportPackageRepeatList = transportPackageRepeatRepository.findAll();
        assertThat(transportPackageRepeatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
