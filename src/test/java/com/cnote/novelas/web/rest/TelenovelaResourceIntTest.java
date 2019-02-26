package com.cnote.novelas.web.rest;

import com.cnote.novelas.NovelasApp;

import com.cnote.novelas.domain.Telenovela;
import com.cnote.novelas.repository.TelenovelaRepository;
import com.cnote.novelas.service.TelenovelaService;
import com.cnote.novelas.service.dto.TelenovelaDTO;
import com.cnote.novelas.service.mapper.TelenovelaMapper;
import com.cnote.novelas.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.cnote.novelas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TelenovelaResource REST controller.
 *
 * @see TelenovelaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NovelasApp.class)
public class TelenovelaResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SUMMARY = "AAAAAAAAAA";
    private static final String UPDATED_SUMMARY = "BBBBBBBBBB";

    private static final Integer DEFAULT_RATING = 1;
    private static final Integer UPDATED_RATING = 2;

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_PLAYLIST = "AAAAAAAAAA";
    private static final String UPDATED_PLAYLIST = "BBBBBBBBBB";

    private static final String DEFAULT_THUMBNAIL = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL = "BBBBBBBBBB";

    private static final String DEFAULT_POSTER = "AAAAAAAAAA";
    private static final String UPDATED_POSTER = "BBBBBBBBBB";

    private static final String DEFAULT_BACKGROUND = "AAAAAAAAAA";
    private static final String UPDATED_BACKGROUND = "BBBBBBBBBB";

    @Autowired
    private TelenovelaRepository telenovelaRepository;

    @Mock
    private TelenovelaRepository telenovelaRepositoryMock;

    @Autowired
    private TelenovelaMapper telenovelaMapper;

    @Mock
    private TelenovelaService telenovelaServiceMock;

    @Autowired
    private TelenovelaService telenovelaService;

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

    private MockMvc restTelenovelaMockMvc;

    private Telenovela telenovela;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TelenovelaResource telenovelaResource = new TelenovelaResource(telenovelaService);
        this.restTelenovelaMockMvc = MockMvcBuilders.standaloneSetup(telenovelaResource)
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
    public static Telenovela createEntity(EntityManager em) {
        Telenovela telenovela = new Telenovela()
            .title(DEFAULT_TITLE)
            .summary(DEFAULT_SUMMARY)
            .rating(DEFAULT_RATING)
            .year(DEFAULT_YEAR)
            .country(DEFAULT_COUNTRY)
            .playlist(DEFAULT_PLAYLIST)
            .thumbnail(DEFAULT_THUMBNAIL)
            .poster(DEFAULT_POSTER)
            .background(DEFAULT_BACKGROUND);
        return telenovela;
    }

    @Before
    public void initTest() {
        telenovela = createEntity(em);
    }

    @Test
    @Transactional
    public void createTelenovela() throws Exception {
        int databaseSizeBeforeCreate = telenovelaRepository.findAll().size();

        // Create the Telenovela
        TelenovelaDTO telenovelaDTO = telenovelaMapper.toDto(telenovela);
        restTelenovelaMockMvc.perform(post("/api/telenovelas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(telenovelaDTO)))
            .andExpect(status().isCreated());

        // Validate the Telenovela in the database
        List<Telenovela> telenovelaList = telenovelaRepository.findAll();
        assertThat(telenovelaList).hasSize(databaseSizeBeforeCreate + 1);
        Telenovela testTelenovela = telenovelaList.get(telenovelaList.size() - 1);
        assertThat(testTelenovela.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTelenovela.getSummary()).isEqualTo(DEFAULT_SUMMARY);
        assertThat(testTelenovela.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testTelenovela.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testTelenovela.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testTelenovela.getPlaylist()).isEqualTo(DEFAULT_PLAYLIST);
        assertThat(testTelenovela.getThumbnail()).isEqualTo(DEFAULT_THUMBNAIL);
        assertThat(testTelenovela.getPoster()).isEqualTo(DEFAULT_POSTER);
        assertThat(testTelenovela.getBackground()).isEqualTo(DEFAULT_BACKGROUND);
    }

    @Test
    @Transactional
    public void createTelenovelaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = telenovelaRepository.findAll().size();

        // Create the Telenovela with an existing ID
        telenovela.setId(1L);
        TelenovelaDTO telenovelaDTO = telenovelaMapper.toDto(telenovela);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTelenovelaMockMvc.perform(post("/api/telenovelas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(telenovelaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Telenovela in the database
        List<Telenovela> telenovelaList = telenovelaRepository.findAll();
        assertThat(telenovelaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = telenovelaRepository.findAll().size();
        // set the field null
        telenovela.setTitle(null);

        // Create the Telenovela, which fails.
        TelenovelaDTO telenovelaDTO = telenovelaMapper.toDto(telenovela);

        restTelenovelaMockMvc.perform(post("/api/telenovelas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(telenovelaDTO)))
            .andExpect(status().isBadRequest());

        List<Telenovela> telenovelaList = telenovelaRepository.findAll();
        assertThat(telenovelaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTelenovelas() throws Exception {
        // Initialize the database
        telenovelaRepository.saveAndFlush(telenovela);

        // Get all the telenovelaList
        restTelenovelaMockMvc.perform(get("/api/telenovelas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(telenovela.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].summary").value(hasItem(DEFAULT_SUMMARY.toString())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].playlist").value(hasItem(DEFAULT_PLAYLIST.toString())))
            .andExpect(jsonPath("$.[*].thumbnail").value(hasItem(DEFAULT_THUMBNAIL.toString())))
            .andExpect(jsonPath("$.[*].poster").value(hasItem(DEFAULT_POSTER.toString())))
            .andExpect(jsonPath("$.[*].background").value(hasItem(DEFAULT_BACKGROUND.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllTelenovelasWithEagerRelationshipsIsEnabled() throws Exception {
        TelenovelaResource telenovelaResource = new TelenovelaResource(telenovelaServiceMock);
        when(telenovelaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restTelenovelaMockMvc = MockMvcBuilders.standaloneSetup(telenovelaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTelenovelaMockMvc.perform(get("/api/telenovelas?eagerload=true"))
        .andExpect(status().isOk());

        verify(telenovelaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllTelenovelasWithEagerRelationshipsIsNotEnabled() throws Exception {
        TelenovelaResource telenovelaResource = new TelenovelaResource(telenovelaServiceMock);
            when(telenovelaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restTelenovelaMockMvc = MockMvcBuilders.standaloneSetup(telenovelaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTelenovelaMockMvc.perform(get("/api/telenovelas?eagerload=true"))
        .andExpect(status().isOk());

            verify(telenovelaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getTelenovela() throws Exception {
        // Initialize the database
        telenovelaRepository.saveAndFlush(telenovela);

        // Get the telenovela
        restTelenovelaMockMvc.perform(get("/api/telenovelas/{id}", telenovela.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(telenovela.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.summary").value(DEFAULT_SUMMARY.toString()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.playlist").value(DEFAULT_PLAYLIST.toString()))
            .andExpect(jsonPath("$.thumbnail").value(DEFAULT_THUMBNAIL.toString()))
            .andExpect(jsonPath("$.poster").value(DEFAULT_POSTER.toString()))
            .andExpect(jsonPath("$.background").value(DEFAULT_BACKGROUND.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTelenovela() throws Exception {
        // Get the telenovela
        restTelenovelaMockMvc.perform(get("/api/telenovelas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTelenovela() throws Exception {
        // Initialize the database
        telenovelaRepository.saveAndFlush(telenovela);

        int databaseSizeBeforeUpdate = telenovelaRepository.findAll().size();

        // Update the telenovela
        Telenovela updatedTelenovela = telenovelaRepository.findById(telenovela.getId()).get();
        // Disconnect from session so that the updates on updatedTelenovela are not directly saved in db
        em.detach(updatedTelenovela);
        updatedTelenovela
            .title(UPDATED_TITLE)
            .summary(UPDATED_SUMMARY)
            .rating(UPDATED_RATING)
            .year(UPDATED_YEAR)
            .country(UPDATED_COUNTRY)
            .playlist(UPDATED_PLAYLIST)
            .thumbnail(UPDATED_THUMBNAIL)
            .poster(UPDATED_POSTER)
            .background(UPDATED_BACKGROUND);
        TelenovelaDTO telenovelaDTO = telenovelaMapper.toDto(updatedTelenovela);

        restTelenovelaMockMvc.perform(put("/api/telenovelas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(telenovelaDTO)))
            .andExpect(status().isOk());

        // Validate the Telenovela in the database
        List<Telenovela> telenovelaList = telenovelaRepository.findAll();
        assertThat(telenovelaList).hasSize(databaseSizeBeforeUpdate);
        Telenovela testTelenovela = telenovelaList.get(telenovelaList.size() - 1);
        assertThat(testTelenovela.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTelenovela.getSummary()).isEqualTo(UPDATED_SUMMARY);
        assertThat(testTelenovela.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testTelenovela.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testTelenovela.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testTelenovela.getPlaylist()).isEqualTo(UPDATED_PLAYLIST);
        assertThat(testTelenovela.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testTelenovela.getPoster()).isEqualTo(UPDATED_POSTER);
        assertThat(testTelenovela.getBackground()).isEqualTo(UPDATED_BACKGROUND);
    }

    @Test
    @Transactional
    public void updateNonExistingTelenovela() throws Exception {
        int databaseSizeBeforeUpdate = telenovelaRepository.findAll().size();

        // Create the Telenovela
        TelenovelaDTO telenovelaDTO = telenovelaMapper.toDto(telenovela);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelenovelaMockMvc.perform(put("/api/telenovelas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(telenovelaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Telenovela in the database
        List<Telenovela> telenovelaList = telenovelaRepository.findAll();
        assertThat(telenovelaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTelenovela() throws Exception {
        // Initialize the database
        telenovelaRepository.saveAndFlush(telenovela);

        int databaseSizeBeforeDelete = telenovelaRepository.findAll().size();

        // Delete the telenovela
        restTelenovelaMockMvc.perform(delete("/api/telenovelas/{id}", telenovela.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Telenovela> telenovelaList = telenovelaRepository.findAll();
        assertThat(telenovelaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Telenovela.class);
        Telenovela telenovela1 = new Telenovela();
        telenovela1.setId(1L);
        Telenovela telenovela2 = new Telenovela();
        telenovela2.setId(telenovela1.getId());
        assertThat(telenovela1).isEqualTo(telenovela2);
        telenovela2.setId(2L);
        assertThat(telenovela1).isNotEqualTo(telenovela2);
        telenovela1.setId(null);
        assertThat(telenovela1).isNotEqualTo(telenovela2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TelenovelaDTO.class);
        TelenovelaDTO telenovelaDTO1 = new TelenovelaDTO();
        telenovelaDTO1.setId(1L);
        TelenovelaDTO telenovelaDTO2 = new TelenovelaDTO();
        assertThat(telenovelaDTO1).isNotEqualTo(telenovelaDTO2);
        telenovelaDTO2.setId(telenovelaDTO1.getId());
        assertThat(telenovelaDTO1).isEqualTo(telenovelaDTO2);
        telenovelaDTO2.setId(2L);
        assertThat(telenovelaDTO1).isNotEqualTo(telenovelaDTO2);
        telenovelaDTO1.setId(null);
        assertThat(telenovelaDTO1).isNotEqualTo(telenovelaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(telenovelaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(telenovelaMapper.fromId(null)).isNull();
    }
}
