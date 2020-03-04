package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.MessageType;
import com.mycompany.myapp.repository.MessageTypeRepository;
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
 * Integration tests for the {@link MessageTypeResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class MessageTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private MessageTypeRepository messageTypeRepository;

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

    private MockMvc restMessageTypeMockMvc;

    private MessageType messageType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MessageTypeResource messageTypeResource = new MessageTypeResource(messageTypeRepository);
        this.restMessageTypeMockMvc = MockMvcBuilders.standaloneSetup(messageTypeResource)
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
    public static MessageType createEntity(EntityManager em) {
        MessageType messageType = new MessageType()
            .name(DEFAULT_NAME);
        return messageType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageType createUpdatedEntity(EntityManager em) {
        MessageType messageType = new MessageType()
            .name(UPDATED_NAME);
        return messageType;
    }

    @BeforeEach
    public void initTest() {
        messageType = createEntity(em);
    }

    @Test
    @Transactional
    public void createMessageType() throws Exception {
        int databaseSizeBeforeCreate = messageTypeRepository.findAll().size();

        // Create the MessageType
        restMessageTypeMockMvc.perform(post("/api/message-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageType)))
            .andExpect(status().isCreated());

        // Validate the MessageType in the database
        List<MessageType> messageTypeList = messageTypeRepository.findAll();
        assertThat(messageTypeList).hasSize(databaseSizeBeforeCreate + 1);
        MessageType testMessageType = messageTypeList.get(messageTypeList.size() - 1);
        assertThat(testMessageType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createMessageTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = messageTypeRepository.findAll().size();

        // Create the MessageType with an existing ID
        messageType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageTypeMockMvc.perform(post("/api/message-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageType)))
            .andExpect(status().isBadRequest());

        // Validate the MessageType in the database
        List<MessageType> messageTypeList = messageTypeRepository.findAll();
        assertThat(messageTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageTypeRepository.findAll().size();
        // set the field null
        messageType.setName(null);

        // Create the MessageType, which fails.

        restMessageTypeMockMvc.perform(post("/api/message-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageType)))
            .andExpect(status().isBadRequest());

        List<MessageType> messageTypeList = messageTypeRepository.findAll();
        assertThat(messageTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMessageTypes() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get all the messageTypeList
        restMessageTypeMockMvc.perform(get("/api/message-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getMessageType() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        // Get the messageType
        restMessageTypeMockMvc.perform(get("/api/message-types/{id}", messageType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(messageType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingMessageType() throws Exception {
        // Get the messageType
        restMessageTypeMockMvc.perform(get("/api/message-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMessageType() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        int databaseSizeBeforeUpdate = messageTypeRepository.findAll().size();

        // Update the messageType
        MessageType updatedMessageType = messageTypeRepository.findById(messageType.getId()).get();
        // Disconnect from session so that the updates on updatedMessageType are not directly saved in db
        em.detach(updatedMessageType);
        updatedMessageType
            .name(UPDATED_NAME);

        restMessageTypeMockMvc.perform(put("/api/message-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMessageType)))
            .andExpect(status().isOk());

        // Validate the MessageType in the database
        List<MessageType> messageTypeList = messageTypeRepository.findAll();
        assertThat(messageTypeList).hasSize(databaseSizeBeforeUpdate);
        MessageType testMessageType = messageTypeList.get(messageTypeList.size() - 1);
        assertThat(testMessageType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMessageType() throws Exception {
        int databaseSizeBeforeUpdate = messageTypeRepository.findAll().size();

        // Create the MessageType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageTypeMockMvc.perform(put("/api/message-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageType)))
            .andExpect(status().isBadRequest());

        // Validate the MessageType in the database
        List<MessageType> messageTypeList = messageTypeRepository.findAll();
        assertThat(messageTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMessageType() throws Exception {
        // Initialize the database
        messageTypeRepository.saveAndFlush(messageType);

        int databaseSizeBeforeDelete = messageTypeRepository.findAll().size();

        // Delete the messageType
        restMessageTypeMockMvc.perform(delete("/api/message-types/{id}", messageType.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MessageType> messageTypeList = messageTypeRepository.findAll();
        assertThat(messageTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
