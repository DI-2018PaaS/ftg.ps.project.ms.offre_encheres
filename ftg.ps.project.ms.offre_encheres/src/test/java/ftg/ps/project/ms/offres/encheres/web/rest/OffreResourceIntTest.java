package ftg.ps.project.ms.offres.encheres.web.rest;

import ftg.ps.project.ms.offres.encheres.MsencheresoffresApp;

import ftg.ps.project.ms.offres.encheres.config.SecurityBeanOverrideConfiguration;

import ftg.ps.project.ms.offres.encheres.domain.Offre;
import ftg.ps.project.ms.offres.encheres.repository.OffreRepository;
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
 * Test class for the OffreResource REST controller.
 *
 * @see OffreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, MsencheresoffresApp.class})
public class OffreResourceIntTest {

    private static final Long DEFAULT_N_UNIQUE_OFFRE = 1L;
    private static final Long UPDATED_N_UNIQUE_OFFRE = 2L;

    private static final LocalDate DEFAULT_DATE_LANCEMENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_LANCEMENT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_CLOTURE_OFFRE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CLOTURE_OFFRE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_QUANTITE_PRODUIT = 1L;
    private static final Long UPDATED_QUANTITE_PRODUIT = 2L;

    private static final Long DEFAULT_N_AUTEUR_OFFRE = 1L;
    private static final Long UPDATED_N_AUTEUR_OFFRE = 2L;

    private static final String DEFAULT_TYPE_AUTEUR_OFFRE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_AUTEUR_OFFRE = "BBBBBBBBBB";

    private static final Long DEFAULT_ACTIVITY_ID = 1L;
    private static final Long UPDATED_ACTIVITY_ID = 2L;

    private static final Long DEFAULT_N_ARTICLES = 1L;
    private static final Long UPDATED_N_ARTICLES = 2L;

    @Autowired
    private OffreRepository offreRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOffreMockMvc;

    private Offre offre;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OffreResource offreResource = new OffreResource(offreRepository);
        this.restOffreMockMvc = MockMvcBuilders.standaloneSetup(offreResource)
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
    public static Offre createEntity(EntityManager em) {
        Offre offre = new Offre()
            .nUniqueOffre(DEFAULT_N_UNIQUE_OFFRE)
            .dateLancement(DEFAULT_DATE_LANCEMENT)
            .dateClotureOffre(DEFAULT_DATE_CLOTURE_OFFRE)
            .quantiteProduit(DEFAULT_QUANTITE_PRODUIT)
            .nAuteurOffre(DEFAULT_N_AUTEUR_OFFRE)
            .typeAuteurOffre(DEFAULT_TYPE_AUTEUR_OFFRE)
            .activityID(DEFAULT_ACTIVITY_ID)
            .nArticles(DEFAULT_N_ARTICLES);
        return offre;
    }

    @Before
    public void initTest() {
        offre = createEntity(em);
    }

    @Test
    @Transactional
    public void createOffre() throws Exception {
        int databaseSizeBeforeCreate = offreRepository.findAll().size();

        // Create the Offre
        restOffreMockMvc.perform(post("/api/offres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offre)))
            .andExpect(status().isCreated());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeCreate + 1);
        Offre testOffre = offreList.get(offreList.size() - 1);
        assertThat(testOffre.getnUniqueOffre()).isEqualTo(DEFAULT_N_UNIQUE_OFFRE);
        assertThat(testOffre.getDateLancement()).isEqualTo(DEFAULT_DATE_LANCEMENT);
        assertThat(testOffre.getDateClotureOffre()).isEqualTo(DEFAULT_DATE_CLOTURE_OFFRE);
        assertThat(testOffre.getQuantiteProduit()).isEqualTo(DEFAULT_QUANTITE_PRODUIT);
        assertThat(testOffre.getnAuteurOffre()).isEqualTo(DEFAULT_N_AUTEUR_OFFRE);
        assertThat(testOffre.getTypeAuteurOffre()).isEqualTo(DEFAULT_TYPE_AUTEUR_OFFRE);
        assertThat(testOffre.getActivityID()).isEqualTo(DEFAULT_ACTIVITY_ID);
        assertThat(testOffre.getnArticles()).isEqualTo(DEFAULT_N_ARTICLES);
    }

    @Test
    @Transactional
    public void createOffreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = offreRepository.findAll().size();

        // Create the Offre with an existing ID
        offre.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOffreMockMvc.perform(post("/api/offres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offre)))
            .andExpect(status().isBadRequest());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checknUniqueOffreIsRequired() throws Exception {
        int databaseSizeBeforeTest = offreRepository.findAll().size();
        // set the field null
        offre.setnUniqueOffre(null);

        // Create the Offre, which fails.

        restOffreMockMvc.perform(post("/api/offres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offre)))
            .andExpect(status().isBadRequest());

        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOffres() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList
        restOffreMockMvc.perform(get("/api/offres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offre.getId().intValue())))
            .andExpect(jsonPath("$.[*].nUniqueOffre").value(hasItem(DEFAULT_N_UNIQUE_OFFRE.intValue())))
            .andExpect(jsonPath("$.[*].dateLancement").value(hasItem(DEFAULT_DATE_LANCEMENT.toString())))
            .andExpect(jsonPath("$.[*].dateClotureOffre").value(hasItem(DEFAULT_DATE_CLOTURE_OFFRE.toString())))
            .andExpect(jsonPath("$.[*].quantiteProduit").value(hasItem(DEFAULT_QUANTITE_PRODUIT.intValue())))
            .andExpect(jsonPath("$.[*].nAuteurOffre").value(hasItem(DEFAULT_N_AUTEUR_OFFRE.intValue())))
            .andExpect(jsonPath("$.[*].typeAuteurOffre").value(hasItem(DEFAULT_TYPE_AUTEUR_OFFRE.toString())))
            .andExpect(jsonPath("$.[*].activityID").value(hasItem(DEFAULT_ACTIVITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].nArticles").value(hasItem(DEFAULT_N_ARTICLES.intValue())));
    }
    

    @Test
    @Transactional
    public void getOffre() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get the offre
        restOffreMockMvc.perform(get("/api/offres/{id}", offre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(offre.getId().intValue()))
            .andExpect(jsonPath("$.nUniqueOffre").value(DEFAULT_N_UNIQUE_OFFRE.intValue()))
            .andExpect(jsonPath("$.dateLancement").value(DEFAULT_DATE_LANCEMENT.toString()))
            .andExpect(jsonPath("$.dateClotureOffre").value(DEFAULT_DATE_CLOTURE_OFFRE.toString()))
            .andExpect(jsonPath("$.quantiteProduit").value(DEFAULT_QUANTITE_PRODUIT.intValue()))
            .andExpect(jsonPath("$.nAuteurOffre").value(DEFAULT_N_AUTEUR_OFFRE.intValue()))
            .andExpect(jsonPath("$.typeAuteurOffre").value(DEFAULT_TYPE_AUTEUR_OFFRE.toString()))
            .andExpect(jsonPath("$.activityID").value(DEFAULT_ACTIVITY_ID.intValue()))
            .andExpect(jsonPath("$.nArticles").value(DEFAULT_N_ARTICLES.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingOffre() throws Exception {
        // Get the offre
        restOffreMockMvc.perform(get("/api/offres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOffre() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        int databaseSizeBeforeUpdate = offreRepository.findAll().size();

        // Update the offre
        Offre updatedOffre = offreRepository.findById(offre.getId()).get();
        // Disconnect from session so that the updates on updatedOffre are not directly saved in db
        em.detach(updatedOffre);
        updatedOffre
            .nUniqueOffre(UPDATED_N_UNIQUE_OFFRE)
            .dateLancement(UPDATED_DATE_LANCEMENT)
            .dateClotureOffre(UPDATED_DATE_CLOTURE_OFFRE)
            .quantiteProduit(UPDATED_QUANTITE_PRODUIT)
            .nAuteurOffre(UPDATED_N_AUTEUR_OFFRE)
            .typeAuteurOffre(UPDATED_TYPE_AUTEUR_OFFRE)
            .activityID(UPDATED_ACTIVITY_ID)
            .nArticles(UPDATED_N_ARTICLES);

        restOffreMockMvc.perform(put("/api/offres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOffre)))
            .andExpect(status().isOk());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeUpdate);
        Offre testOffre = offreList.get(offreList.size() - 1);
        assertThat(testOffre.getnUniqueOffre()).isEqualTo(UPDATED_N_UNIQUE_OFFRE);
        assertThat(testOffre.getDateLancement()).isEqualTo(UPDATED_DATE_LANCEMENT);
        assertThat(testOffre.getDateClotureOffre()).isEqualTo(UPDATED_DATE_CLOTURE_OFFRE);
        assertThat(testOffre.getQuantiteProduit()).isEqualTo(UPDATED_QUANTITE_PRODUIT);
        assertThat(testOffre.getnAuteurOffre()).isEqualTo(UPDATED_N_AUTEUR_OFFRE);
        assertThat(testOffre.getTypeAuteurOffre()).isEqualTo(UPDATED_TYPE_AUTEUR_OFFRE);
        assertThat(testOffre.getActivityID()).isEqualTo(UPDATED_ACTIVITY_ID);
        assertThat(testOffre.getnArticles()).isEqualTo(UPDATED_N_ARTICLES);
    }

    @Test
    @Transactional
    public void updateNonExistingOffre() throws Exception {
        int databaseSizeBeforeUpdate = offreRepository.findAll().size();

        // Create the Offre

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOffreMockMvc.perform(put("/api/offres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offre)))
            .andExpect(status().isBadRequest());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOffre() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        int databaseSizeBeforeDelete = offreRepository.findAll().size();

        // Get the offre
        restOffreMockMvc.perform(delete("/api/offres/{id}", offre.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Offre.class);
        Offre offre1 = new Offre();
        offre1.setId(1L);
        Offre offre2 = new Offre();
        offre2.setId(offre1.getId());
        assertThat(offre1).isEqualTo(offre2);
        offre2.setId(2L);
        assertThat(offre1).isNotEqualTo(offre2);
        offre1.setId(null);
        assertThat(offre1).isNotEqualTo(offre2);
    }
}
