package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.CMS;
import com.mycompany.myapp.repository.CMSRepository;
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
 * Integration tests for the {@link CMSResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class CMSResourceIT {

    private static final String DEFAULT_FILE_ID = "AAAAAAAAAA";
    private static final String UPDATED_FILE_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_MESSAGE_COUNT = 1;
    private static final Integer UPDATED_MESSAGE_COUNT = 2;

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CMSRepository cMSRepository;

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

    private MockMvc restCMSMockMvc;

    private CMS cMS;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CMSResource cMSResource = new CMSResource(cMSRepository);
        this.restCMSMockMvc = MockMvcBuilders.standaloneSetup(cMSResource)
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
    public static CMS createEntity(EntityManager em) {
        CMS cMS = new CMS()
            .fileId(DEFAULT_FILE_ID)
            .messageCount(DEFAULT_MESSAGE_COUNT)
            .createdAt(DEFAULT_CREATED_AT);
        return cMS;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CMS createUpdatedEntity(EntityManager em) {
        CMS cMS = new CMS()
            .fileId(UPDATED_FILE_ID)
            .messageCount(UPDATED_MESSAGE_COUNT)
            .createdAt(UPDATED_CREATED_AT);
        return cMS;
    }

    @BeforeEach
    public void initTest() {
        cMS = createEntity(em);
    }

    @Test
    @Transactional
    public void createCMS() throws Exception {
        int databaseSizeBeforeCreate = cMSRepository.findAll().size();

        // Create the CMS
        restCMSMockMvc.perform(post("/api/cms")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cMS)))
            .andExpect(status().isCreated());

        // Validate the CMS in the database
        List<CMS> cMSList = cMSRepository.findAll();
        assertThat(cMSList).hasSize(databaseSizeBeforeCreate + 1);
        CMS testCMS = cMSList.get(cMSList.size() - 1);
        assertThat(testCMS.getFileId()).isEqualTo(DEFAULT_FILE_ID);
        assertThat(testCMS.getMessageCount()).isEqualTo(DEFAULT_MESSAGE_COUNT);
        assertThat(testCMS.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    public void createCMSWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cMSRepository.findAll().size();

        // Create the CMS with an existing ID
        cMS.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCMSMockMvc.perform(post("/api/cms")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cMS)))
            .andExpect(status().isBadRequest());

        // Validate the CMS in the database
        List<CMS> cMSList = cMSRepository.findAll();
        assertThat(cMSList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFileIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = cMSRepository.findAll().size();
        // set the field null
        cMS.setFileId(null);

        // Create the CMS, which fails.

        restCMSMockMvc.perform(post("/api/cms")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cMS)))
            .andExpect(status().isBadRequest());

        List<CMS> cMSList = cMSRepository.findAll();
        assertThat(cMSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMessageCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = cMSRepository.findAll().size();
        // set the field null
        cMS.setMessageCount(null);

        // Create the CMS, which fails.

        restCMSMockMvc.perform(post("/api/cms")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cMS)))
            .andExpect(status().isBadRequest());

        List<CMS> cMSList = cMSRepository.findAll();
        assertThat(cMSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = cMSRepository.findAll().size();
        // set the field null
        cMS.setCreatedAt(null);

        // Create the CMS, which fails.

        restCMSMockMvc.perform(post("/api/cms")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cMS)))
            .andExpect(status().isBadRequest());

        List<CMS> cMSList = cMSRepository.findAll();
        assertThat(cMSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCMS() throws Exception {
        // Initialize the database
        cMSRepository.saveAndFlush(cMS);

        // Get all the cMSList
        restCMSMockMvc.perform(get("/api/cms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cMS.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileId").value(hasItem(DEFAULT_FILE_ID)))
            .andExpect(jsonPath("$.[*].messageCount").value(hasItem(DEFAULT_MESSAGE_COUNT)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getCMS() throws Exception {
        // Initialize the database
        cMSRepository.saveAndFlush(cMS);

        // Get the cMS
        restCMSMockMvc.perform(get("/api/cms/{id}", cMS.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cMS.getId().intValue()))
            .andExpect(jsonPath("$.fileId").value(DEFAULT_FILE_ID))
            .andExpect(jsonPath("$.messageCount").value(DEFAULT_MESSAGE_COUNT))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCMS() throws Exception {
        // Get the cMS
        restCMSMockMvc.perform(get("/api/cms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCMS() throws Exception {
        // Initialize the database
        cMSRepository.saveAndFlush(cMS);

        int databaseSizeBeforeUpdate = cMSRepository.findAll().size();

        // Update the cMS
        CMS updatedCMS = cMSRepository.findById(cMS.getId()).get();
        // Disconnect from session so that the updates on updatedCMS are not directly saved in db
        em.detach(updatedCMS);
        updatedCMS
            .fileId(UPDATED_FILE_ID)
            .messageCount(UPDATED_MESSAGE_COUNT)
            .createdAt(UPDATED_CREATED_AT);

        restCMSMockMvc.perform(put("/api/cms")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCMS)))
            .andExpect(status().isOk());

        // Validate the CMS in the database
        List<CMS> cMSList = cMSRepository.findAll();
        assertThat(cMSList).hasSize(databaseSizeBeforeUpdate);
        CMS testCMS = cMSList.get(cMSList.size() - 1);
        assertThat(testCMS.getFileId()).isEqualTo(UPDATED_FILE_ID);
        assertThat(testCMS.getMessageCount()).isEqualTo(UPDATED_MESSAGE_COUNT);
        assertThat(testCMS.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingCMS() throws Exception {
        int databaseSizeBeforeUpdate = cMSRepository.findAll().size();

        // Create the CMS

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCMSMockMvc.perform(put("/api/cms")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cMS)))
            .andExpect(status().isBadRequest());

        // Validate the CMS in the database
        List<CMS> cMSList = cMSRepository.findAll();
        assertThat(cMSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCMS() throws Exception {
        // Initialize the database
        cMSRepository.saveAndFlush(cMS);

        int databaseSizeBeforeDelete = cMSRepository.findAll().size();

        // Delete the cMS
        restCMSMockMvc.perform(delete("/api/cms/{id}", cMS.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CMS> cMSList = cMSRepository.findAll();
        assertThat(cMSList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
