package ftg.ps.project.ms.offres.encheres.web.rest;

import ftg.ps.project.ms.offres.encheres.MsencheresoffresApp;

import ftg.ps.project.ms.offres.encheres.config.SecurityBeanOverrideConfiguration;

import ftg.ps.project.ms.offres.encheres.domain.Enchere;
import ftg.ps.project.ms.offres.encheres.repository.EnchereRepository;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static ftg.ps.project.ms.offres.encheres.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EnchereResource REST controller.
 *
 * @see EnchereResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, MsencheresoffresApp.class})
public class EnchereResourceIntTest {

    private static final Long DEFAULT_N_UNIQUE_ENCHERE = 1L;
    private static final Long UPDATED_N_UNIQUE_ENCHERE = 2L;

    private static final LocalDate DEFAULT_DATE_LANCEMENT_ENCHERE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_LANCEMENT_ENCHERE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_CLOTURE_ENCHERE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CLOTURE_ENCHERE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_QUANTITE_PRODUIT = 1L;
    private static final Long UPDATED_QUANTITE_PRODUIT = 2L;

    private static final Long DEFAULT_N_AUTEUR_ENCHERE = 1L;
    private static final Long UPDATED_N_AUTEUR_ENCHERE = 2L;

    private static final String DEFAULT_TYPE_AUTEUR_ENCHERE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_AUTEUR_ENCHERE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRIX_DEPART = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRIX_DEPART = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRIX_COURANT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRIX_COURANT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRIX_DE_VENTE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRIX_DE_VENTE = new BigDecimal(2);

    private static final Long DEFAULT_ACTIVITY_ID = 1L;
    private static final Long UPDATED_ACTIVITY_ID = 2L;

    private static final Long DEFAULT_N_ARTICLES = 1L;
    private static final Long UPDATED_N_ARTICLES = 2L;

    @Autowired
    private EnchereRepository enchereRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEnchereMockMvc;

    private Enchere enchere;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EnchereResource enchereResource = new EnchereResource(enchereRepository);
        this.restEnchereMockMvc = MockMvcBuilders.standaloneSetup(enchereResource)
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
    public static Enchere createEntity(EntityManager em) {
        Enchere enchere = new Enchere()
            .nUniqueEnchere(DEFAULT_N_UNIQUE_ENCHERE)
            .dateLancementEnchere(DEFAULT_DATE_LANCEMENT_ENCHERE)
            .dateClotureEnchere(DEFAULT_DATE_CLOTURE_ENCHERE)
            .quantiteProduit(DEFAULT_QUANTITE_PRODUIT)
            .nAuteurEnchere(DEFAULT_N_AUTEUR_ENCHERE)
            .typeAuteurEnchere(DEFAULT_TYPE_AUTEUR_ENCHERE)
            .prixDepart(DEFAULT_PRIX_DEPART)
            .prixCourant(DEFAULT_PRIX_COURANT)
            .prixDeVente(DEFAULT_PRIX_DE_VENTE)
            .activityID(DEFAULT_ACTIVITY_ID)
            .nArticles(DEFAULT_N_ARTICLES);
        return enchere;
    }

    @Before
    public void initTest() {
        enchere = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnchere() throws Exception {
        int databaseSizeBeforeCreate = enchereRepository.findAll().size();

        // Create the Enchere
        restEnchereMockMvc.perform(post("/api/encheres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enchere)))
            .andExpect(status().isCreated());

        // Validate the Enchere in the database
        List<Enchere> enchereList = enchereRepository.findAll();
        assertThat(enchereList).hasSize(databaseSizeBeforeCreate + 1);
        Enchere testEnchere = enchereList.get(enchereList.size() - 1);
        assertThat(testEnchere.getnUniqueEnchere()).isEqualTo(DEFAULT_N_UNIQUE_ENCHERE);
        assertThat(testEnchere.getDateLancementEnchere()).isEqualTo(DEFAULT_DATE_LANCEMENT_ENCHERE);
        assertThat(testEnchere.getDateClotureEnchere()).isEqualTo(DEFAULT_DATE_CLOTURE_ENCHERE);
        assertThat(testEnchere.getQuantiteProduit()).isEqualTo(DEFAULT_QUANTITE_PRODUIT);
        assertThat(testEnchere.getnAuteurEnchere()).isEqualTo(DEFAULT_N_AUTEUR_ENCHERE);
        assertThat(testEnchere.getTypeAuteurEnchere()).isEqualTo(DEFAULT_TYPE_AUTEUR_ENCHERE);
        assertThat(testEnchere.getPrixDepart()).isEqualTo(DEFAULT_PRIX_DEPART);
        assertThat(testEnchere.getPrixCourant()).isEqualTo(DEFAULT_PRIX_COURANT);
        assertThat(testEnchere.getPrixDeVente()).isEqualTo(DEFAULT_PRIX_DE_VENTE);
        assertThat(testEnchere.getActivityID()).isEqualTo(DEFAULT_ACTIVITY_ID);
        assertThat(testEnchere.getnArticles()).isEqualTo(DEFAULT_N_ARTICLES);
    }

    @Test
    @Transactional
    public void createEnchereWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enchereRepository.findAll().size();

        // Create the Enchere with an existing ID
        enchere.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnchereMockMvc.perform(post("/api/encheres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enchere)))
            .andExpect(status().isBadRequest());

        // Validate the Enchere in the database
        List<Enchere> enchereList = enchereRepository.findAll();
        assertThat(enchereList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checknUniqueEnchereIsRequired() throws Exception {
        int databaseSizeBeforeTest = enchereRepository.findAll().size();
        // set the field null
        enchere.setnUniqueEnchere(null);

        // Create the Enchere, which fails.

        restEnchereMockMvc.perform(post("/api/encheres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enchere)))
            .andExpect(status().isBadRequest());

        List<Enchere> enchereList = enchereRepository.findAll();
        assertThat(enchereList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEncheres() throws Exception {
        // Initialize the database
        enchereRepository.saveAndFlush(enchere);

        // Get all the enchereList
        restEnchereMockMvc.perform(get("/api/encheres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enchere.getId().intValue())))
            .andExpect(jsonPath("$.[*].nUniqueEnchere").value(hasItem(DEFAULT_N_UNIQUE_ENCHERE.intValue())))
            .andExpect(jsonPath("$.[*].dateLancementEnchere").value(hasItem(DEFAULT_DATE_LANCEMENT_ENCHERE.toString())))
            .andExpect(jsonPath("$.[*].dateClotureEnchere").value(hasItem(DEFAULT_DATE_CLOTURE_ENCHERE.toString())))
            .andExpect(jsonPath("$.[*].quantiteProduit").value(hasItem(DEFAULT_QUANTITE_PRODUIT.intValue())))
            .andExpect(jsonPath("$.[*].nAuteurEnchere").value(hasItem(DEFAULT_N_AUTEUR_ENCHERE.intValue())))
            .andExpect(jsonPath("$.[*].typeAuteurEnchere").value(hasItem(DEFAULT_TYPE_AUTEUR_ENCHERE.toString())))
            .andExpect(jsonPath("$.[*].prixDepart").value(hasItem(DEFAULT_PRIX_DEPART.intValue())))
            .andExpect(jsonPath("$.[*].prixCourant").value(hasItem(DEFAULT_PRIX_COURANT.intValue())))
            .andExpect(jsonPath("$.[*].prixDeVente").value(hasItem(DEFAULT_PRIX_DE_VENTE.intValue())))
            .andExpect(jsonPath("$.[*].activityID").value(hasItem(DEFAULT_ACTIVITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].nArticles").value(hasItem(DEFAULT_N_ARTICLES.intValue())));
    }
    

    @Test
    @Transactional
    public void getEnchere() throws Exception {
        // Initialize the database
        enchereRepository.saveAndFlush(enchere);

        // Get the enchere
        restEnchereMockMvc.perform(get("/api/encheres/{id}", enchere.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(enchere.getId().intValue()))
            .andExpect(jsonPath("$.nUniqueEnchere").value(DEFAULT_N_UNIQUE_ENCHERE.intValue()))
            .andExpect(jsonPath("$.dateLancementEnchere").value(DEFAULT_DATE_LANCEMENT_ENCHERE.toString()))
            .andExpect(jsonPath("$.dateClotureEnchere").value(DEFAULT_DATE_CLOTURE_ENCHERE.toString()))
            .andExpect(jsonPath("$.quantiteProduit").value(DEFAULT_QUANTITE_PRODUIT.intValue()))
            .andExpect(jsonPath("$.nAuteurEnchere").value(DEFAULT_N_AUTEUR_ENCHERE.intValue()))
            .andExpect(jsonPath("$.typeAuteurEnchere").value(DEFAULT_TYPE_AUTEUR_ENCHERE.toString()))
            .andExpect(jsonPath("$.prixDepart").value(DEFAULT_PRIX_DEPART.intValue()))
            .andExpect(jsonPath("$.prixCourant").value(DEFAULT_PRIX_COURANT.intValue()))
            .andExpect(jsonPath("$.prixDeVente").value(DEFAULT_PRIX_DE_VENTE.intValue()))
            .andExpect(jsonPath("$.activityID").value(DEFAULT_ACTIVITY_ID.intValue()))
            .andExpect(jsonPath("$.nArticles").value(DEFAULT_N_ARTICLES.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingEnchere() throws Exception {
        // Get the enchere
        restEnchereMockMvc.perform(get("/api/encheres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnchere() throws Exception {
        // Initialize the database
        enchereRepository.saveAndFlush(enchere);

        int databaseSizeBeforeUpdate = enchereRepository.findAll().size();

        // Update the enchere
        Enchere updatedEnchere = enchereRepository.findById(enchere.getId()).get();
        // Disconnect from session so that the updates on updatedEnchere are not directly saved in db
        em.detach(updatedEnchere);
        updatedEnchere
            .nUniqueEnchere(UPDATED_N_UNIQUE_ENCHERE)
            .dateLancementEnchere(UPDATED_DATE_LANCEMENT_ENCHERE)
            .dateClotureEnchere(UPDATED_DATE_CLOTURE_ENCHERE)
            .quantiteProduit(UPDATED_QUANTITE_PRODUIT)
            .nAuteurEnchere(UPDATED_N_AUTEUR_ENCHERE)
            .typeAuteurEnchere(UPDATED_TYPE_AUTEUR_ENCHERE)
            .prixDepart(UPDATED_PRIX_DEPART)
            .prixCourant(UPDATED_PRIX_COURANT)
            .prixDeVente(UPDATED_PRIX_DE_VENTE)
            .activityID(UPDATED_ACTIVITY_ID)
            .nArticles(UPDATED_N_ARTICLES);

        restEnchereMockMvc.perform(put("/api/encheres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEnchere)))
            .andExpect(status().isOk());

        // Validate the Enchere in the database
        List<Enchere> enchereList = enchereRepository.findAll();
        assertThat(enchereList).hasSize(databaseSizeBeforeUpdate);
        Enchere testEnchere = enchereList.get(enchereList.size() - 1);
        assertThat(testEnchere.getnUniqueEnchere()).isEqualTo(UPDATED_N_UNIQUE_ENCHERE);
        assertThat(testEnchere.getDateLancementEnchere()).isEqualTo(UPDATED_DATE_LANCEMENT_ENCHERE);
        assertThat(testEnchere.getDateClotureEnchere()).isEqualTo(UPDATED_DATE_CLOTURE_ENCHERE);
        assertThat(testEnchere.getQuantiteProduit()).isEqualTo(UPDATED_QUANTITE_PRODUIT);
        assertThat(testEnchere.getnAuteurEnchere()).isEqualTo(UPDATED_N_AUTEUR_ENCHERE);
        assertThat(testEnchere.getTypeAuteurEnchere()).isEqualTo(UPDATED_TYPE_AUTEUR_ENCHERE);
        assertThat(testEnchere.getPrixDepart()).isEqualTo(UPDATED_PRIX_DEPART);
        assertThat(testEnchere.getPrixCourant()).isEqualTo(UPDATED_PRIX_COURANT);
        assertThat(testEnchere.getPrixDeVente()).isEqualTo(UPDATED_PRIX_DE_VENTE);
        assertThat(testEnchere.getActivityID()).isEqualTo(UPDATED_ACTIVITY_ID);
        assertThat(testEnchere.getnArticles()).isEqualTo(UPDATED_N_ARTICLES);
    }

    @Test
    @Transactional
    public void updateNonExistingEnchere() throws Exception {
        int databaseSizeBeforeUpdate = enchereRepository.findAll().size();

        // Create the Enchere

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEnchereMockMvc.perform(put("/api/encheres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enchere)))
            .andExpect(status().isBadRequest());

        // Validate the Enchere in the database
        List<Enchere> enchereList = enchereRepository.findAll();
        assertThat(enchereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnchere() throws Exception {
        // Initialize the database
        enchereRepository.saveAndFlush(enchere);

        int databaseSizeBeforeDelete = enchereRepository.findAll().size();

        // Get the enchere
        restEnchereMockMvc.perform(delete("/api/encheres/{id}", enchere.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Enchere> enchereList = enchereRepository.findAll();
        assertThat(enchereList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Enchere.class);
        Enchere enchere1 = new Enchere();
        enchere1.setId(1L);
        Enchere enchere2 = new Enchere();
        enchere2.setId(enchere1.getId());
        assertThat(enchere1).isEqualTo(enchere2);
        enchere2.setId(2L);
        assertThat(enchere1).isNotEqualTo(enchere2);
        enchere1.setId(null);
        assertThat(enchere1).isNotEqualTo(enchere2);
    }
}
