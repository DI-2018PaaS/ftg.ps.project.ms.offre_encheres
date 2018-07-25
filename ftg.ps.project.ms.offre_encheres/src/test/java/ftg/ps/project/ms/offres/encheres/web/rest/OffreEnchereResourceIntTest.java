package ftg.ps.project.ms.offres.encheres.web.rest;

import ftg.ps.project.ms.offres.encheres.MsencheresoffresApp;

import ftg.ps.project.ms.offres.encheres.config.SecurityBeanOverrideConfiguration;

import ftg.ps.project.ms.offres.encheres.domain.OffreEnchere;
import ftg.ps.project.ms.offres.encheres.repository.OffreEnchereRepository;
import ftg.ps.project.ms.offres.encheres.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static ftg.ps.project.ms.offres.encheres.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OffreEnchereResource REST controller.
 *
 * @see OffreEnchereResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, MsencheresoffresApp.class})
public class OffreEnchereResourceIntTest {

    private static final Long DEFAULT_N_INSCRIPTION = 1L;
    private static final Long UPDATED_N_INSCRIPTION = 2L;

    private static final Long DEFAULT_N_UNIQUE_ENCHERE = 1L;
    private static final Long UPDATED_N_UNIQUE_ENCHERE = 2L;

    private static final LocalDate DEFAULT_DATE_REPONSE_ENCHERE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_REPONSE_ENCHERE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_N_ACTEUR_EXT = 1L;
    private static final Long UPDATED_N_ACTEUR_EXT = 2L;

    @Autowired
    private OffreEnchereRepository offreEnchereRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOffreEnchereMockMvc;

    private OffreEnchere offreEnchere;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OffreEnchereResource offreEnchereResource = new OffreEnchereResource(offreEnchereRepository);
        this.restOffreEnchereMockMvc = MockMvcBuilders.standaloneSetup(offreEnchereResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OffreEnchere createEntity(EntityManager em) {
        OffreEnchere offreEnchere = new OffreEnchere()
            .nInscription(DEFAULT_N_INSCRIPTION)
            .nUniqueEnchere(DEFAULT_N_UNIQUE_ENCHERE)
            .dateReponseEnchere(DEFAULT_DATE_REPONSE_ENCHERE)
            .nActeurExt(DEFAULT_N_ACTEUR_EXT);
        return offreEnchere;
    }

    @Before
    public void initTest() {
        offreEnchere = createEntity(em);
    }

    @Test
    @Transactional
    public void createOffreEnchere() throws Exception {
        int databaseSizeBeforeCreate = offreEnchereRepository.findAll().size();

        // Create the OffreEnchere
        restOffreEnchereMockMvc.perform(post("/api/offre-encheres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offreEnchere)))
            .andExpect(status().isCreated());

        // Validate the OffreEnchere in the database
        List<OffreEnchere> offreEnchereList = offreEnchereRepository.findAll();
        assertThat(offreEnchereList).hasSize(databaseSizeBeforeCreate + 1);
        OffreEnchere testOffreEnchere = offreEnchereList.get(offreEnchereList.size() - 1);
        assertThat(testOffreEnchere.getnInscription()).isEqualTo(DEFAULT_N_INSCRIPTION);
        assertThat(testOffreEnchere.getnUniqueEnchere()).isEqualTo(DEFAULT_N_UNIQUE_ENCHERE);
        assertThat(testOffreEnchere.getDateReponseEnchere()).isEqualTo(DEFAULT_DATE_REPONSE_ENCHERE);
        assertThat(testOffreEnchere.getnActeurExt()).isEqualTo(DEFAULT_N_ACTEUR_EXT);
    }

    @Test
    @Transactional
    public void createOffreEnchereWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = offreEnchereRepository.findAll().size();

        // Create the OffreEnchere with an existing ID
        offreEnchere.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOffreEnchereMockMvc.perform(post("/api/offre-encheres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offreEnchere)))
            .andExpect(status().isBadRequest());

        // Validate the OffreEnchere in the database
        List<OffreEnchere> offreEnchereList = offreEnchereRepository.findAll();
        assertThat(offreEnchereList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checknInscriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = offreEnchereRepository.findAll().size();
        // set the field null
        offreEnchere.setnInscription(null);

        // Create the OffreEnchere, which fails.

        restOffreEnchereMockMvc.perform(post("/api/offre-encheres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offreEnchere)))
            .andExpect(status().isBadRequest());

        List<OffreEnchere> offreEnchereList = offreEnchereRepository.findAll();
        assertThat(offreEnchereList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOffreEncheres() throws Exception {
        // Initialize the database
        offreEnchereRepository.saveAndFlush(offreEnchere);

        // Get all the offreEnchereList
        restOffreEnchereMockMvc.perform(get("/api/offre-encheres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offreEnchere.getId().intValue())))
            .andExpect(jsonPath("$.[*].nInscription").value(hasItem(DEFAULT_N_INSCRIPTION.intValue())))
            .andExpect(jsonPath("$.[*].nUniqueEnchere").value(hasItem(DEFAULT_N_UNIQUE_ENCHERE.intValue())))
            .andExpect(jsonPath("$.[*].dateReponseEnchere").value(hasItem(DEFAULT_DATE_REPONSE_ENCHERE.toString())))
            .andExpect(jsonPath("$.[*].nActeurExt").value(hasItem(DEFAULT_N_ACTEUR_EXT.intValue())));
    }
    

    @Test
    @Transactional
    public void getOffreEnchere() throws Exception {
        // Initialize the database
        offreEnchereRepository.saveAndFlush(offreEnchere);

        // Get the offreEnchere
        restOffreEnchereMockMvc.perform(get("/api/offre-encheres/{id}", offreEnchere.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(offreEnchere.getId().intValue()))
            .andExpect(jsonPath("$.nInscription").value(DEFAULT_N_INSCRIPTION.intValue()))
            .andExpect(jsonPath("$.nUniqueEnchere").value(DEFAULT_N_UNIQUE_ENCHERE.intValue()))
            .andExpect(jsonPath("$.dateReponseEnchere").value(DEFAULT_DATE_REPONSE_ENCHERE.toString()))
            .andExpect(jsonPath("$.nActeurExt").value(DEFAULT_N_ACTEUR_EXT.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingOffreEnchere() throws Exception {
        // Get the offreEnchere
        restOffreEnchereMockMvc.perform(get("/api/offre-encheres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOffreEnchere() throws Exception {
        // Initialize the database
        offreEnchereRepository.saveAndFlush(offreEnchere);

        int databaseSizeBeforeUpdate = offreEnchereRepository.findAll().size();

        // Update the offreEnchere
        OffreEnchere updatedOffreEnchere = offreEnchereRepository.findById(offreEnchere.getId()).get();
        // Disconnect from session so that the updates on updatedOffreEnchere are not directly saved in db
        em.detach(updatedOffreEnchere);
        updatedOffreEnchere
            .nInscription(UPDATED_N_INSCRIPTION)
            .nUniqueEnchere(UPDATED_N_UNIQUE_ENCHERE)
            .dateReponseEnchere(UPDATED_DATE_REPONSE_ENCHERE)
            .nActeurExt(UPDATED_N_ACTEUR_EXT);

        restOffreEnchereMockMvc.perform(put("/api/offre-encheres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOffreEnchere)))
            .andExpect(status().isOk());

        // Validate the OffreEnchere in the database
        List<OffreEnchere> offreEnchereList = offreEnchereRepository.findAll();
        assertThat(offreEnchereList).hasSize(databaseSizeBeforeUpdate);
        OffreEnchere testOffreEnchere = offreEnchereList.get(offreEnchereList.size() - 1);
        assertThat(testOffreEnchere.getnInscription()).isEqualTo(UPDATED_N_INSCRIPTION);
        assertThat(testOffreEnchere.getnUniqueEnchere()).isEqualTo(UPDATED_N_UNIQUE_ENCHERE);
        assertThat(testOffreEnchere.getDateReponseEnchere()).isEqualTo(UPDATED_DATE_REPONSE_ENCHERE);
        assertThat(testOffreEnchere.getnActeurExt()).isEqualTo(UPDATED_N_ACTEUR_EXT);
    }

    @Test
    @Transactional
    public void updateNonExistingOffreEnchere() throws Exception {
        int databaseSizeBeforeUpdate = offreEnchereRepository.findAll().size();

        // Create the OffreEnchere

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOffreEnchereMockMvc.perform(put("/api/offre-encheres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offreEnchere)))
            .andExpect(status().isBadRequest());

        // Validate the OffreEnchere in the database
        List<OffreEnchere> offreEnchereList = offreEnchereRepository.findAll();
        assertThat(offreEnchereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOffreEnchere() throws Exception {
        // Initialize the database
        offreEnchereRepository.saveAndFlush(offreEnchere);

        int databaseSizeBeforeDelete = offreEnchereRepository.findAll().size();

        // Get the offreEnchere
        restOffreEnchereMockMvc.perform(delete("/api/offre-encheres/{id}", offreEnchere.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OffreEnchere> offreEnchereList = offreEnchereRepository.findAll();
        assertThat(offreEnchereList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OffreEnchere.class);
        OffreEnchere offreEnchere1 = new OffreEnchere();
        offreEnchere1.setId(1L);
        OffreEnchere offreEnchere2 = new OffreEnchere();
        offreEnchere2.setId(offreEnchere1.getId());
        assertThat(offreEnchere1).isEqualTo(offreEnchere2);
        offreEnchere2.setId(2L);
        assertThat(offreEnchere1).isNotEqualTo(offreEnchere2);
        offreEnchere1.setId(null);
        assertThat(offreEnchere1).isNotEqualTo(offreEnchere2);
    }
}
