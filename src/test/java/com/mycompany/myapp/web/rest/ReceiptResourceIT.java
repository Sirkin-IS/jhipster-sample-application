package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Receipt;
import com.mycompany.myapp.repository.ReceiptRepository;
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
 * Integration tests for the {@link ReceiptResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class ReceiptResourceIT {

    private static final Integer DEFAULT_CMS_ID = 1;
    private static final Integer UPDATED_CMS_ID = 2;

    private static final Integer DEFAULT_RECEIPT_TYPE_ID = 1;
    private static final Integer UPDATED_RECEIPT_TYPE_ID = 2;

    private static final LocalDate DEFAULT_RECEIPT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECEIPT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ReceiptRepository receiptRepository;

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

    private MockMvc restReceiptMockMvc;

    private Receipt receipt;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReceiptResource receiptResource = new ReceiptResource(receiptRepository);
        this.restReceiptMockMvc = MockMvcBuilders.standaloneSetup(receiptResource)
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
    public static Receipt createEntity(EntityManager em) {
        Receipt receipt = new Receipt()
            .cmsId(DEFAULT_CMS_ID)
            .receiptTypeId(DEFAULT_RECEIPT_TYPE_ID)
            .receiptDate(DEFAULT_RECEIPT_DATE)
            .createdAt(DEFAULT_CREATED_AT);
        return receipt;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Receipt createUpdatedEntity(EntityManager em) {
        Receipt receipt = new Receipt()
            .cmsId(UPDATED_CMS_ID)
            .receiptTypeId(UPDATED_RECEIPT_TYPE_ID)
            .receiptDate(UPDATED_RECEIPT_DATE)
            .createdAt(UPDATED_CREATED_AT);
        return receipt;
    }

    @BeforeEach
    public void initTest() {
        receipt = createEntity(em);
    }

    @Test
    @Transactional
    public void createReceipt() throws Exception {
        int databaseSizeBeforeCreate = receiptRepository.findAll().size();

        // Create the Receipt
        restReceiptMockMvc.perform(post("/api/receipts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receipt)))
            .andExpect(status().isCreated());

        // Validate the Receipt in the database
        List<Receipt> receiptList = receiptRepository.findAll();
        assertThat(receiptList).hasSize(databaseSizeBeforeCreate + 1);
        Receipt testReceipt = receiptList.get(receiptList.size() - 1);
        assertThat(testReceipt.getCmsId()).isEqualTo(DEFAULT_CMS_ID);
        assertThat(testReceipt.getReceiptTypeId()).isEqualTo(DEFAULT_RECEIPT_TYPE_ID);
        assertThat(testReceipt.getReceiptDate()).isEqualTo(DEFAULT_RECEIPT_DATE);
        assertThat(testReceipt.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    public void createReceiptWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = receiptRepository.findAll().size();

        // Create the Receipt with an existing ID
        receipt.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReceiptMockMvc.perform(post("/api/receipts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receipt)))
            .andExpect(status().isBadRequest());

        // Validate the Receipt in the database
        List<Receipt> receiptList = receiptRepository.findAll();
        assertThat(receiptList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCmsIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = receiptRepository.findAll().size();
        // set the field null
        receipt.setCmsId(null);

        // Create the Receipt, which fails.

        restReceiptMockMvc.perform(post("/api/receipts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receipt)))
            .andExpect(status().isBadRequest());

        List<Receipt> receiptList = receiptRepository.findAll();
        assertThat(receiptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReceiptTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = receiptRepository.findAll().size();
        // set the field null
        receipt.setReceiptTypeId(null);

        // Create the Receipt, which fails.

        restReceiptMockMvc.perform(post("/api/receipts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receipt)))
            .andExpect(status().isBadRequest());

        List<Receipt> receiptList = receiptRepository.findAll();
        assertThat(receiptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReceiptDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = receiptRepository.findAll().size();
        // set the field null
        receipt.setReceiptDate(null);

        // Create the Receipt, which fails.

        restReceiptMockMvc.perform(post("/api/receipts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receipt)))
            .andExpect(status().isBadRequest());

        List<Receipt> receiptList = receiptRepository.findAll();
        assertThat(receiptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = receiptRepository.findAll().size();
        // set the field null
        receipt.setCreatedAt(null);

        // Create the Receipt, which fails.

        restReceiptMockMvc.perform(post("/api/receipts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receipt)))
            .andExpect(status().isBadRequest());

        List<Receipt> receiptList = receiptRepository.findAll();
        assertThat(receiptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReceipts() throws Exception {
        // Initialize the database
        receiptRepository.saveAndFlush(receipt);

        // Get all the receiptList
        restReceiptMockMvc.perform(get("/api/receipts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receipt.getId().intValue())))
            .andExpect(jsonPath("$.[*].cmsId").value(hasItem(DEFAULT_CMS_ID)))
            .andExpect(jsonPath("$.[*].receiptTypeId").value(hasItem(DEFAULT_RECEIPT_TYPE_ID)))
            .andExpect(jsonPath("$.[*].receiptDate").value(hasItem(DEFAULT_RECEIPT_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getReceipt() throws Exception {
        // Initialize the database
        receiptRepository.saveAndFlush(receipt);

        // Get the receipt
        restReceiptMockMvc.perform(get("/api/receipts/{id}", receipt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(receipt.getId().intValue()))
            .andExpect(jsonPath("$.cmsId").value(DEFAULT_CMS_ID))
            .andExpect(jsonPath("$.receiptTypeId").value(DEFAULT_RECEIPT_TYPE_ID))
            .andExpect(jsonPath("$.receiptDate").value(DEFAULT_RECEIPT_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReceipt() throws Exception {
        // Get the receipt
        restReceiptMockMvc.perform(get("/api/receipts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReceipt() throws Exception {
        // Initialize the database
        receiptRepository.saveAndFlush(receipt);

        int databaseSizeBeforeUpdate = receiptRepository.findAll().size();

        // Update the receipt
        Receipt updatedReceipt = receiptRepository.findById(receipt.getId()).get();
        // Disconnect from session so that the updates on updatedReceipt are not directly saved in db
        em.detach(updatedReceipt);
        updatedReceipt
            .cmsId(UPDATED_CMS_ID)
            .receiptTypeId(UPDATED_RECEIPT_TYPE_ID)
            .receiptDate(UPDATED_RECEIPT_DATE)
            .createdAt(UPDATED_CREATED_AT);

        restReceiptMockMvc.perform(put("/api/receipts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedReceipt)))
            .andExpect(status().isOk());

        // Validate the Receipt in the database
        List<Receipt> receiptList = receiptRepository.findAll();
        assertThat(receiptList).hasSize(databaseSizeBeforeUpdate);
        Receipt testReceipt = receiptList.get(receiptList.size() - 1);
        assertThat(testReceipt.getCmsId()).isEqualTo(UPDATED_CMS_ID);
        assertThat(testReceipt.getReceiptTypeId()).isEqualTo(UPDATED_RECEIPT_TYPE_ID);
        assertThat(testReceipt.getReceiptDate()).isEqualTo(UPDATED_RECEIPT_DATE);
        assertThat(testReceipt.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingReceipt() throws Exception {
        int databaseSizeBeforeUpdate = receiptRepository.findAll().size();

        // Create the Receipt

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReceiptMockMvc.perform(put("/api/receipts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receipt)))
            .andExpect(status().isBadRequest());

        // Validate the Receipt in the database
        List<Receipt> receiptList = receiptRepository.findAll();
        assertThat(receiptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReceipt() throws Exception {
        // Initialize the database
        receiptRepository.saveAndFlush(receipt);

        int databaseSizeBeforeDelete = receiptRepository.findAll().size();

        // Delete the receipt
        restReceiptMockMvc.perform(delete("/api/receipts/{id}", receipt.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Receipt> receiptList = receiptRepository.findAll();
        assertThat(receiptList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
