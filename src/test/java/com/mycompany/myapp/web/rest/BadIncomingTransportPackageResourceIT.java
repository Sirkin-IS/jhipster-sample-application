package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.BadIncomingTransportPackage;
import com.mycompany.myapp.repository.BadIncomingTransportPackageRepository;
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
import org.springframework.util.Base64Utils;
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
 * Integration tests for the {@link BadIncomingTransportPackageResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class BadIncomingTransportPackageResourceIT {

    private static final Integer DEFAULT_TRANSPORT_PACKAGE_ID = 1;
    private static final Integer UPDATED_TRANSPORT_PACKAGE_ID = 2;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_CONTENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONTENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTENT_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_ANSWER_CODE = 1;
    private static final Integer UPDATED_ANSWER_CODE = 2;

    @Autowired
    private BadIncomingTransportPackageRepository badIncomingTransportPackageRepository;

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

    private MockMvc restBadIncomingTransportPackageMockMvc;

    private BadIncomingTransportPackage badIncomingTransportPackage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BadIncomingTransportPackageResource badIncomingTransportPackageResource = new BadIncomingTransportPackageResource(badIncomingTransportPackageRepository);
        this.restBadIncomingTransportPackageMockMvc = MockMvcBuilders.standaloneSetup(badIncomingTransportPackageResource)
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
    public static BadIncomingTransportPackage createEntity(EntityManager em) {
        BadIncomingTransportPackage badIncomingTransportPackage = new BadIncomingTransportPackage()
            .transportPackageId(DEFAULT_TRANSPORT_PACKAGE_ID)
            .date(DEFAULT_DATE)
            .content(DEFAULT_CONTENT)
            .contentContentType(DEFAULT_CONTENT_CONTENT_TYPE)
            .answerCode(DEFAULT_ANSWER_CODE);
        return badIncomingTransportPackage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BadIncomingTransportPackage createUpdatedEntity(EntityManager em) {
        BadIncomingTransportPackage badIncomingTransportPackage = new BadIncomingTransportPackage()
            .transportPackageId(UPDATED_TRANSPORT_PACKAGE_ID)
            .date(UPDATED_DATE)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .answerCode(UPDATED_ANSWER_CODE);
        return badIncomingTransportPackage;
    }

    @BeforeEach
    public void initTest() {
        badIncomingTransportPackage = createEntity(em);
    }

    @Test
    @Transactional
    public void createBadIncomingTransportPackage() throws Exception {
        int databaseSizeBeforeCreate = badIncomingTransportPackageRepository.findAll().size();

        // Create the BadIncomingTransportPackage
        restBadIncomingTransportPackageMockMvc.perform(post("/api/bad-incoming-transport-packages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badIncomingTransportPackage)))
            .andExpect(status().isCreated());

        // Validate the BadIncomingTransportPackage in the database
        List<BadIncomingTransportPackage> badIncomingTransportPackageList = badIncomingTransportPackageRepository.findAll();
        assertThat(badIncomingTransportPackageList).hasSize(databaseSizeBeforeCreate + 1);
        BadIncomingTransportPackage testBadIncomingTransportPackage = badIncomingTransportPackageList.get(badIncomingTransportPackageList.size() - 1);
        assertThat(testBadIncomingTransportPackage.getTransportPackageId()).isEqualTo(DEFAULT_TRANSPORT_PACKAGE_ID);
        assertThat(testBadIncomingTransportPackage.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testBadIncomingTransportPackage.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testBadIncomingTransportPackage.getContentContentType()).isEqualTo(DEFAULT_CONTENT_CONTENT_TYPE);
        assertThat(testBadIncomingTransportPackage.getAnswerCode()).isEqualTo(DEFAULT_ANSWER_CODE);
    }

    @Test
    @Transactional
    public void createBadIncomingTransportPackageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = badIncomingTransportPackageRepository.findAll().size();

        // Create the BadIncomingTransportPackage with an existing ID
        badIncomingTransportPackage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBadIncomingTransportPackageMockMvc.perform(post("/api/bad-incoming-transport-packages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badIncomingTransportPackage)))
            .andExpect(status().isBadRequest());

        // Validate the BadIncomingTransportPackage in the database
        List<BadIncomingTransportPackage> badIncomingTransportPackageList = badIncomingTransportPackageRepository.findAll();
        assertThat(badIncomingTransportPackageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTransportPackageIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = badIncomingTransportPackageRepository.findAll().size();
        // set the field null
        badIncomingTransportPackage.setTransportPackageId(null);

        // Create the BadIncomingTransportPackage, which fails.

        restBadIncomingTransportPackageMockMvc.perform(post("/api/bad-incoming-transport-packages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badIncomingTransportPackage)))
            .andExpect(status().isBadRequest());

        List<BadIncomingTransportPackage> badIncomingTransportPackageList = badIncomingTransportPackageRepository.findAll();
        assertThat(badIncomingTransportPackageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = badIncomingTransportPackageRepository.findAll().size();
        // set the field null
        badIncomingTransportPackage.setDate(null);

        // Create the BadIncomingTransportPackage, which fails.

        restBadIncomingTransportPackageMockMvc.perform(post("/api/bad-incoming-transport-packages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badIncomingTransportPackage)))
            .andExpect(status().isBadRequest());

        List<BadIncomingTransportPackage> badIncomingTransportPackageList = badIncomingTransportPackageRepository.findAll();
        assertThat(badIncomingTransportPackageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnswerCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = badIncomingTransportPackageRepository.findAll().size();
        // set the field null
        badIncomingTransportPackage.setAnswerCode(null);

        // Create the BadIncomingTransportPackage, which fails.

        restBadIncomingTransportPackageMockMvc.perform(post("/api/bad-incoming-transport-packages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badIncomingTransportPackage)))
            .andExpect(status().isBadRequest());

        List<BadIncomingTransportPackage> badIncomingTransportPackageList = badIncomingTransportPackageRepository.findAll();
        assertThat(badIncomingTransportPackageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBadIncomingTransportPackages() throws Exception {
        // Initialize the database
        badIncomingTransportPackageRepository.saveAndFlush(badIncomingTransportPackage);

        // Get all the badIncomingTransportPackageList
        restBadIncomingTransportPackageMockMvc.perform(get("/api/bad-incoming-transport-packages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(badIncomingTransportPackage.getId().intValue())))
            .andExpect(jsonPath("$.[*].transportPackageId").value(hasItem(DEFAULT_TRANSPORT_PACKAGE_ID)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))))
            .andExpect(jsonPath("$.[*].answerCode").value(hasItem(DEFAULT_ANSWER_CODE)));
    }
    
    @Test
    @Transactional
    public void getBadIncomingTransportPackage() throws Exception {
        // Initialize the database
        badIncomingTransportPackageRepository.saveAndFlush(badIncomingTransportPackage);

        // Get the badIncomingTransportPackage
        restBadIncomingTransportPackageMockMvc.perform(get("/api/bad-incoming-transport-packages/{id}", badIncomingTransportPackage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(badIncomingTransportPackage.getId().intValue()))
            .andExpect(jsonPath("$.transportPackageId").value(DEFAULT_TRANSPORT_PACKAGE_ID))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.contentContentType").value(DEFAULT_CONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.content").value(Base64Utils.encodeToString(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.answerCode").value(DEFAULT_ANSWER_CODE));
    }

    @Test
    @Transactional
    public void getNonExistingBadIncomingTransportPackage() throws Exception {
        // Get the badIncomingTransportPackage
        restBadIncomingTransportPackageMockMvc.perform(get("/api/bad-incoming-transport-packages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBadIncomingTransportPackage() throws Exception {
        // Initialize the database
        badIncomingTransportPackageRepository.saveAndFlush(badIncomingTransportPackage);

        int databaseSizeBeforeUpdate = badIncomingTransportPackageRepository.findAll().size();

        // Update the badIncomingTransportPackage
        BadIncomingTransportPackage updatedBadIncomingTransportPackage = badIncomingTransportPackageRepository.findById(badIncomingTransportPackage.getId()).get();
        // Disconnect from session so that the updates on updatedBadIncomingTransportPackage are not directly saved in db
        em.detach(updatedBadIncomingTransportPackage);
        updatedBadIncomingTransportPackage
            .transportPackageId(UPDATED_TRANSPORT_PACKAGE_ID)
            .date(UPDATED_DATE)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .answerCode(UPDATED_ANSWER_CODE);

        restBadIncomingTransportPackageMockMvc.perform(put("/api/bad-incoming-transport-packages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBadIncomingTransportPackage)))
            .andExpect(status().isOk());

        // Validate the BadIncomingTransportPackage in the database
        List<BadIncomingTransportPackage> badIncomingTransportPackageList = badIncomingTransportPackageRepository.findAll();
        assertThat(badIncomingTransportPackageList).hasSize(databaseSizeBeforeUpdate);
        BadIncomingTransportPackage testBadIncomingTransportPackage = badIncomingTransportPackageList.get(badIncomingTransportPackageList.size() - 1);
        assertThat(testBadIncomingTransportPackage.getTransportPackageId()).isEqualTo(UPDATED_TRANSPORT_PACKAGE_ID);
        assertThat(testBadIncomingTransportPackage.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testBadIncomingTransportPackage.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testBadIncomingTransportPackage.getContentContentType()).isEqualTo(UPDATED_CONTENT_CONTENT_TYPE);
        assertThat(testBadIncomingTransportPackage.getAnswerCode()).isEqualTo(UPDATED_ANSWER_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingBadIncomingTransportPackage() throws Exception {
        int databaseSizeBeforeUpdate = badIncomingTransportPackageRepository.findAll().size();

        // Create the BadIncomingTransportPackage

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBadIncomingTransportPackageMockMvc.perform(put("/api/bad-incoming-transport-packages")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badIncomingTransportPackage)))
            .andExpect(status().isBadRequest());

        // Validate the BadIncomingTransportPackage in the database
        List<BadIncomingTransportPackage> badIncomingTransportPackageList = badIncomingTransportPackageRepository.findAll();
        assertThat(badIncomingTransportPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBadIncomingTransportPackage() throws Exception {
        // Initialize the database
        badIncomingTransportPackageRepository.saveAndFlush(badIncomingTransportPackage);

        int databaseSizeBeforeDelete = badIncomingTransportPackageRepository.findAll().size();

        // Delete the badIncomingTransportPackage
        restBadIncomingTransportPackageMockMvc.perform(delete("/api/bad-incoming-transport-packages/{id}", badIncomingTransportPackage.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BadIncomingTransportPackage> badIncomingTransportPackageList = badIncomingTransportPackageRepository.findAll();
        assertThat(badIncomingTransportPackageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
