package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.HbaseFile;
import com.mycompany.myapp.repository.HbaseFileRepository;
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
 * Integration tests for the {@link HbaseFileResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class HbaseFileResourceIT {

    private static final Integer DEFAULT_FILE_ID = 1;
    private static final Integer UPDATED_FILE_ID = 2;

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private HbaseFileRepository hbaseFileRepository;

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

    private MockMvc restHbaseFileMockMvc;

    private HbaseFile hbaseFile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HbaseFileResource hbaseFileResource = new HbaseFileResource(hbaseFileRepository);
        this.restHbaseFileMockMvc = MockMvcBuilders.standaloneSetup(hbaseFileResource)
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
    public static HbaseFile createEntity(EntityManager em) {
        HbaseFile hbaseFile = new HbaseFile()
            .fileId(DEFAULT_FILE_ID)
            .content(DEFAULT_CONTENT);
        return hbaseFile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HbaseFile createUpdatedEntity(EntityManager em) {
        HbaseFile hbaseFile = new HbaseFile()
            .fileId(UPDATED_FILE_ID)
            .content(UPDATED_CONTENT);
        return hbaseFile;
    }

    @BeforeEach
    public void initTest() {
        hbaseFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createHbaseFile() throws Exception {
        int databaseSizeBeforeCreate = hbaseFileRepository.findAll().size();

        // Create the HbaseFile
        restHbaseFileMockMvc.perform(post("/api/hbase-files")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hbaseFile)))
            .andExpect(status().isCreated());

        // Validate the HbaseFile in the database
        List<HbaseFile> hbaseFileList = hbaseFileRepository.findAll();
        assertThat(hbaseFileList).hasSize(databaseSizeBeforeCreate + 1);
        HbaseFile testHbaseFile = hbaseFileList.get(hbaseFileList.size() - 1);
        assertThat(testHbaseFile.getFileId()).isEqualTo(DEFAULT_FILE_ID);
        assertThat(testHbaseFile.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    public void createHbaseFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hbaseFileRepository.findAll().size();

        // Create the HbaseFile with an existing ID
        hbaseFile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHbaseFileMockMvc.perform(post("/api/hbase-files")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hbaseFile)))
            .andExpect(status().isBadRequest());

        // Validate the HbaseFile in the database
        List<HbaseFile> hbaseFileList = hbaseFileRepository.findAll();
        assertThat(hbaseFileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFileIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = hbaseFileRepository.findAll().size();
        // set the field null
        hbaseFile.setFileId(null);

        // Create the HbaseFile, which fails.

        restHbaseFileMockMvc.perform(post("/api/hbase-files")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hbaseFile)))
            .andExpect(status().isBadRequest());

        List<HbaseFile> hbaseFileList = hbaseFileRepository.findAll();
        assertThat(hbaseFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = hbaseFileRepository.findAll().size();
        // set the field null
        hbaseFile.setContent(null);

        // Create the HbaseFile, which fails.

        restHbaseFileMockMvc.perform(post("/api/hbase-files")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hbaseFile)))
            .andExpect(status().isBadRequest());

        List<HbaseFile> hbaseFileList = hbaseFileRepository.findAll();
        assertThat(hbaseFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHbaseFiles() throws Exception {
        // Initialize the database
        hbaseFileRepository.saveAndFlush(hbaseFile);

        // Get all the hbaseFileList
        restHbaseFileMockMvc.perform(get("/api/hbase-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hbaseFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileId").value(hasItem(DEFAULT_FILE_ID)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
    
    @Test
    @Transactional
    public void getHbaseFile() throws Exception {
        // Initialize the database
        hbaseFileRepository.saveAndFlush(hbaseFile);

        // Get the hbaseFile
        restHbaseFileMockMvc.perform(get("/api/hbase-files/{id}", hbaseFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hbaseFile.getId().intValue()))
            .andExpect(jsonPath("$.fileId").value(DEFAULT_FILE_ID))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    public void getNonExistingHbaseFile() throws Exception {
        // Get the hbaseFile
        restHbaseFileMockMvc.perform(get("/api/hbase-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHbaseFile() throws Exception {
        // Initialize the database
        hbaseFileRepository.saveAndFlush(hbaseFile);

        int databaseSizeBeforeUpdate = hbaseFileRepository.findAll().size();

        // Update the hbaseFile
        HbaseFile updatedHbaseFile = hbaseFileRepository.findById(hbaseFile.getId()).get();
        // Disconnect from session so that the updates on updatedHbaseFile are not directly saved in db
        em.detach(updatedHbaseFile);
        updatedHbaseFile
            .fileId(UPDATED_FILE_ID)
            .content(UPDATED_CONTENT);

        restHbaseFileMockMvc.perform(put("/api/hbase-files")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedHbaseFile)))
            .andExpect(status().isOk());

        // Validate the HbaseFile in the database
        List<HbaseFile> hbaseFileList = hbaseFileRepository.findAll();
        assertThat(hbaseFileList).hasSize(databaseSizeBeforeUpdate);
        HbaseFile testHbaseFile = hbaseFileList.get(hbaseFileList.size() - 1);
        assertThat(testHbaseFile.getFileId()).isEqualTo(UPDATED_FILE_ID);
        assertThat(testHbaseFile.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void updateNonExistingHbaseFile() throws Exception {
        int databaseSizeBeforeUpdate = hbaseFileRepository.findAll().size();

        // Create the HbaseFile

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHbaseFileMockMvc.perform(put("/api/hbase-files")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hbaseFile)))
            .andExpect(status().isBadRequest());

        // Validate the HbaseFile in the database
        List<HbaseFile> hbaseFileList = hbaseFileRepository.findAll();
        assertThat(hbaseFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHbaseFile() throws Exception {
        // Initialize the database
        hbaseFileRepository.saveAndFlush(hbaseFile);

        int databaseSizeBeforeDelete = hbaseFileRepository.findAll().size();

        // Delete the hbaseFile
        restHbaseFileMockMvc.perform(delete("/api/hbase-files/{id}", hbaseFile.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HbaseFile> hbaseFileList = hbaseFileRepository.findAll();
        assertThat(hbaseFileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
