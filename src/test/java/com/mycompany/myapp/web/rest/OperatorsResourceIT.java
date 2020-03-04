package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Operators;
import com.mycompany.myapp.repository.OperatorsRepository;
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
 * Integration tests for the {@link OperatorsResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class OperatorsResourceIT {

    private static final String DEFAULT_OPERATOR_ID = "AAAAAAAAAA";
    private static final String UPDATED_OPERATOR_ID = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_SECOND_URL = "AAAAAAAAAA";
    private static final String UPDATED_SECOND_URL = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_ALIAS = "AAAAAAAAAA";
    private static final String UPDATED_ALIAS = "BBBBBBBBBB";

    @Autowired
    private OperatorsRepository operatorsRepository;

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

    private MockMvc restOperatorsMockMvc;

    private Operators operators;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OperatorsResource operatorsResource = new OperatorsResource(operatorsRepository);
        this.restOperatorsMockMvc = MockMvcBuilders.standaloneSetup(operatorsResource)
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
    public static Operators createEntity(EntityManager em) {
        Operators operators = new Operators()
            .operatorId(DEFAULT_OPERATOR_ID)
            .url(DEFAULT_URL)
            .secondUrl(DEFAULT_SECOND_URL)
            .title(DEFAULT_TITLE)
            .alias(DEFAULT_ALIAS);
        return operators;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Operators createUpdatedEntity(EntityManager em) {
        Operators operators = new Operators()
            .operatorId(UPDATED_OPERATOR_ID)
            .url(UPDATED_URL)
            .secondUrl(UPDATED_SECOND_URL)
            .title(UPDATED_TITLE)
            .alias(UPDATED_ALIAS);
        return operators;
    }

    @BeforeEach
    public void initTest() {
        operators = createEntity(em);
    }

    @Test
    @Transactional
    public void createOperators() throws Exception {
        int databaseSizeBeforeCreate = operatorsRepository.findAll().size();

        // Create the Operators
        restOperatorsMockMvc.perform(post("/api/operators")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(operators)))
            .andExpect(status().isCreated());

        // Validate the Operators in the database
        List<Operators> operatorsList = operatorsRepository.findAll();
        assertThat(operatorsList).hasSize(databaseSizeBeforeCreate + 1);
        Operators testOperators = operatorsList.get(operatorsList.size() - 1);
        assertThat(testOperators.getOperatorId()).isEqualTo(DEFAULT_OPERATOR_ID);
        assertThat(testOperators.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testOperators.getSecondUrl()).isEqualTo(DEFAULT_SECOND_URL);
        assertThat(testOperators.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testOperators.getAlias()).isEqualTo(DEFAULT_ALIAS);
    }

    @Test
    @Transactional
    public void createOperatorsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = operatorsRepository.findAll().size();

        // Create the Operators with an existing ID
        operators.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperatorsMockMvc.perform(post("/api/operators")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(operators)))
            .andExpect(status().isBadRequest());

        // Validate the Operators in the database
        List<Operators> operatorsList = operatorsRepository.findAll();
        assertThat(operatorsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkOperatorIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = operatorsRepository.findAll().size();
        // set the field null
        operators.setOperatorId(null);

        // Create the Operators, which fails.

        restOperatorsMockMvc.perform(post("/api/operators")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(operators)))
            .andExpect(status().isBadRequest());

        List<Operators> operatorsList = operatorsRepository.findAll();
        assertThat(operatorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = operatorsRepository.findAll().size();
        // set the field null
        operators.setUrl(null);

        // Create the Operators, which fails.

        restOperatorsMockMvc.perform(post("/api/operators")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(operators)))
            .andExpect(status().isBadRequest());

        List<Operators> operatorsList = operatorsRepository.findAll();
        assertThat(operatorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSecondUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = operatorsRepository.findAll().size();
        // set the field null
        operators.setSecondUrl(null);

        // Create the Operators, which fails.

        restOperatorsMockMvc.perform(post("/api/operators")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(operators)))
            .andExpect(status().isBadRequest());

        List<Operators> operatorsList = operatorsRepository.findAll();
        assertThat(operatorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = operatorsRepository.findAll().size();
        // set the field null
        operators.setTitle(null);

        // Create the Operators, which fails.

        restOperatorsMockMvc.perform(post("/api/operators")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(operators)))
            .andExpect(status().isBadRequest());

        List<Operators> operatorsList = operatorsRepository.findAll();
        assertThat(operatorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAliasIsRequired() throws Exception {
        int databaseSizeBeforeTest = operatorsRepository.findAll().size();
        // set the field null
        operators.setAlias(null);

        // Create the Operators, which fails.

        restOperatorsMockMvc.perform(post("/api/operators")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(operators)))
            .andExpect(status().isBadRequest());

        List<Operators> operatorsList = operatorsRepository.findAll();
        assertThat(operatorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOperators() throws Exception {
        // Initialize the database
        operatorsRepository.saveAndFlush(operators);

        // Get all the operatorsList
        restOperatorsMockMvc.perform(get("/api/operators?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operators.getId().intValue())))
            .andExpect(jsonPath("$.[*].operatorId").value(hasItem(DEFAULT_OPERATOR_ID)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].secondUrl").value(hasItem(DEFAULT_SECOND_URL)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].alias").value(hasItem(DEFAULT_ALIAS)));
    }
    
    @Test
    @Transactional
    public void getOperators() throws Exception {
        // Initialize the database
        operatorsRepository.saveAndFlush(operators);

        // Get the operators
        restOperatorsMockMvc.perform(get("/api/operators/{id}", operators.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(operators.getId().intValue()))
            .andExpect(jsonPath("$.operatorId").value(DEFAULT_OPERATOR_ID))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.secondUrl").value(DEFAULT_SECOND_URL))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.alias").value(DEFAULT_ALIAS));
    }

    @Test
    @Transactional
    public void getNonExistingOperators() throws Exception {
        // Get the operators
        restOperatorsMockMvc.perform(get("/api/operators/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOperators() throws Exception {
        // Initialize the database
        operatorsRepository.saveAndFlush(operators);

        int databaseSizeBeforeUpdate = operatorsRepository.findAll().size();

        // Update the operators
        Operators updatedOperators = operatorsRepository.findById(operators.getId()).get();
        // Disconnect from session so that the updates on updatedOperators are not directly saved in db
        em.detach(updatedOperators);
        updatedOperators
            .operatorId(UPDATED_OPERATOR_ID)
            .url(UPDATED_URL)
            .secondUrl(UPDATED_SECOND_URL)
            .title(UPDATED_TITLE)
            .alias(UPDATED_ALIAS);

        restOperatorsMockMvc.perform(put("/api/operators")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOperators)))
            .andExpect(status().isOk());

        // Validate the Operators in the database
        List<Operators> operatorsList = operatorsRepository.findAll();
        assertThat(operatorsList).hasSize(databaseSizeBeforeUpdate);
        Operators testOperators = operatorsList.get(operatorsList.size() - 1);
        assertThat(testOperators.getOperatorId()).isEqualTo(UPDATED_OPERATOR_ID);
        assertThat(testOperators.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testOperators.getSecondUrl()).isEqualTo(UPDATED_SECOND_URL);
        assertThat(testOperators.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testOperators.getAlias()).isEqualTo(UPDATED_ALIAS);
    }

    @Test
    @Transactional
    public void updateNonExistingOperators() throws Exception {
        int databaseSizeBeforeUpdate = operatorsRepository.findAll().size();

        // Create the Operators

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperatorsMockMvc.perform(put("/api/operators")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(operators)))
            .andExpect(status().isBadRequest());

        // Validate the Operators in the database
        List<Operators> operatorsList = operatorsRepository.findAll();
        assertThat(operatorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOperators() throws Exception {
        // Initialize the database
        operatorsRepository.saveAndFlush(operators);

        int databaseSizeBeforeDelete = operatorsRepository.findAll().size();

        // Delete the operators
        restOperatorsMockMvc.perform(delete("/api/operators/{id}", operators.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Operators> operatorsList = operatorsRepository.findAll();
        assertThat(operatorsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
