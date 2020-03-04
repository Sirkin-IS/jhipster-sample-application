package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Direction;
import com.mycompany.myapp.repository.DirectionRepository;
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
 * Integration tests for the {@link DirectionResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class DirectionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DirectionRepository directionRepository;

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

    private MockMvc restDirectionMockMvc;

    private Direction direction;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DirectionResource directionResource = new DirectionResource(directionRepository);
        this.restDirectionMockMvc = MockMvcBuilders.standaloneSetup(directionResource)
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
    public static Direction createEntity(EntityManager em) {
        Direction direction = new Direction()
            .name(DEFAULT_NAME);
        return direction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Direction createUpdatedEntity(EntityManager em) {
        Direction direction = new Direction()
            .name(UPDATED_NAME);
        return direction;
    }

    @BeforeEach
    public void initTest() {
        direction = createEntity(em);
    }

    @Test
    @Transactional
    public void createDirection() throws Exception {
        int databaseSizeBeforeCreate = directionRepository.findAll().size();

        // Create the Direction
        restDirectionMockMvc.perform(post("/api/directions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(direction)))
            .andExpect(status().isCreated());

        // Validate the Direction in the database
        List<Direction> directionList = directionRepository.findAll();
        assertThat(directionList).hasSize(databaseSizeBeforeCreate + 1);
        Direction testDirection = directionList.get(directionList.size() - 1);
        assertThat(testDirection.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDirectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = directionRepository.findAll().size();

        // Create the Direction with an existing ID
        direction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDirectionMockMvc.perform(post("/api/directions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(direction)))
            .andExpect(status().isBadRequest());

        // Validate the Direction in the database
        List<Direction> directionList = directionRepository.findAll();
        assertThat(directionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = directionRepository.findAll().size();
        // set the field null
        direction.setName(null);

        // Create the Direction, which fails.

        restDirectionMockMvc.perform(post("/api/directions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(direction)))
            .andExpect(status().isBadRequest());

        List<Direction> directionList = directionRepository.findAll();
        assertThat(directionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDirections() throws Exception {
        // Initialize the database
        directionRepository.saveAndFlush(direction);

        // Get all the directionList
        restDirectionMockMvc.perform(get("/api/directions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(direction.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getDirection() throws Exception {
        // Initialize the database
        directionRepository.saveAndFlush(direction);

        // Get the direction
        restDirectionMockMvc.perform(get("/api/directions/{id}", direction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(direction.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingDirection() throws Exception {
        // Get the direction
        restDirectionMockMvc.perform(get("/api/directions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDirection() throws Exception {
        // Initialize the database
        directionRepository.saveAndFlush(direction);

        int databaseSizeBeforeUpdate = directionRepository.findAll().size();

        // Update the direction
        Direction updatedDirection = directionRepository.findById(direction.getId()).get();
        // Disconnect from session so that the updates on updatedDirection are not directly saved in db
        em.detach(updatedDirection);
        updatedDirection
            .name(UPDATED_NAME);

        restDirectionMockMvc.perform(put("/api/directions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDirection)))
            .andExpect(status().isOk());

        // Validate the Direction in the database
        List<Direction> directionList = directionRepository.findAll();
        assertThat(directionList).hasSize(databaseSizeBeforeUpdate);
        Direction testDirection = directionList.get(directionList.size() - 1);
        assertThat(testDirection.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDirection() throws Exception {
        int databaseSizeBeforeUpdate = directionRepository.findAll().size();

        // Create the Direction

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDirectionMockMvc.perform(put("/api/directions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(direction)))
            .andExpect(status().isBadRequest());

        // Validate the Direction in the database
        List<Direction> directionList = directionRepository.findAll();
        assertThat(directionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDirection() throws Exception {
        // Initialize the database
        directionRepository.saveAndFlush(direction);

        int databaseSizeBeforeDelete = directionRepository.findAll().size();

        // Delete the direction
        restDirectionMockMvc.perform(delete("/api/directions/{id}", direction.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Direction> directionList = directionRepository.findAll();
        assertThat(directionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
