package ftg.ps.project.ms.offres.encheres.web.rest;

import ftg.ps.project.ms.offres.encheres.MsencheresoffresApp;

import ftg.ps.project.ms.offres.encheres.config.SecurityBeanOverrideConfiguration;

import ftg.ps.project.ms.offres.encheres.domain.AppelOffre;
import ftg.ps.project.ms.offres.encheres.repository.AppelOffreRepository;
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
 * Test class for the AppelOffreResource REST controller.
 *
 * @see AppelOffreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, MsencheresoffresApp.class})
public class AppelOffreResourceIntTest {

    private static final Long DEFAULT_N_INSCRIPTION = 1L;
    private static final Long UPDATED_N_INSCRIPTION = 2L;

    private static final LocalDate DEFAULT_DATE_REPONSE_OFFRE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_REPONSE_OFFRE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_N_UNIQUE_OFFRE = 1L;
    private static final Long UPDATED_N_UNIQUE_OFFRE = 2L;

    private static final Long DEFAULT_N_FOURNISSEUR_EXT = 1L;
    private static final Long UPDATED_N_FOURNISSEUR_EXT = 2L;

    @Autowired
    private AppelOffreRepository appelOffreRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAppelOffreMockMvc;

    private AppelOffre appelOffre;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AppelOffreResource appelOffreResource = new AppelOffreResource(appelOffreRepository);
        this.restAppelOffreMockMvc = MockMvcBuilders.standaloneSetup(appelOffreResource)
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
    public static AppelOffre createEntity(EntityManager em) {
        AppelOffre appelOffre = new AppelOffre()
            .nInscription(DEFAULT_N_INSCRIPTION)
            .dateReponseOffre(DEFAULT_DATE_REPONSE_OFFRE)
            .nUniqueOffre(DEFAULT_N_UNIQUE_OFFRE)
            .nFournisseurExt(DEFAULT_N_FOURNISSEUR_EXT);
        return appelOffre;
    }

    @Before
    public void initTest() {
        appelOffre = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppelOffre() throws Exception {
        int databaseSizeBeforeCreate = appelOffreRepository.findAll().size();

        // Create the AppelOffre
        restAppelOffreMockMvc.perform(post("/api/appel-offres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appelOffre)))
            .andExpect(status().isCreated());

        // Validate the AppelOffre in the database
        List<AppelOffre> appelOffreList = appelOffreRepository.findAll();
        assertThat(appelOffreList).hasSize(databaseSizeBeforeCreate + 1);
        AppelOffre testAppelOffre = appelOffreList.get(appelOffreList.size() - 1);
        assertThat(testAppelOffre.getnInscription()).isEqualTo(DEFAULT_N_INSCRIPTION);
        assertThat(testAppelOffre.getDateReponseOffre()).isEqualTo(DEFAULT_DATE_REPONSE_OFFRE);
        assertThat(testAppelOffre.getnUniqueOffre()).isEqualTo(DEFAULT_N_UNIQUE_OFFRE);
        assertThat(testAppelOffre.getnFournisseurExt()).isEqualTo(DEFAULT_N_FOURNISSEUR_EXT);
    }

    @Test
    @Transactional
    public void createAppelOffreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appelOffreRepository.findAll().size();

        // Create the AppelOffre with an existing ID
        appelOffre.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppelOffreMockMvc.perform(post("/api/appel-offres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appelOffre)))
            .andExpect(status().isBadRequest());

        // Validate the AppelOffre in the database
        List<AppelOffre> appelOffreList = appelOffreRepository.findAll();
        assertThat(appelOffreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checknInscriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = appelOffreRepository.findAll().size();
        // set the field null
        appelOffre.setnInscription(null);

        // Create the AppelOffre, which fails.

        restAppelOffreMockMvc.perform(post("/api/appel-offres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appelOffre)))
            .andExpect(status().isBadRequest());

        List<AppelOffre> appelOffreList = appelOffreRepository.findAll();
        assertThat(appelOffreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAppelOffres() throws Exception {
        // Initialize the database
        appelOffreRepository.saveAndFlush(appelOffre);

        // Get all the appelOffreList
        restAppelOffreMockMvc.perform(get("/api/appel-offres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appelOffre.getId().intValue())))
            .andExpect(jsonPath("$.[*].nInscription").value(hasItem(DEFAULT_N_INSCRIPTION.intValue())))
            .andExpect(jsonPath("$.[*].dateReponseOffre").value(hasItem(DEFAULT_DATE_REPONSE_OFFRE.toString())))
            .andExpect(jsonPath("$.[*].nUniqueOffre").value(hasItem(DEFAULT_N_UNIQUE_OFFRE.intValue())))
            .andExpect(jsonPath("$.[*].nFournisseurExt").value(hasItem(DEFAULT_N_FOURNISSEUR_EXT.intValue())));
    }
    

    @Test
    @Transactional
    public void getAppelOffre() throws Exception {
        // Initialize the database
        appelOffreRepository.saveAndFlush(appelOffre);

        // Get the appelOffre
        restAppelOffreMockMvc.perform(get("/api/appel-offres/{id}", appelOffre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appelOffre.getId().intValue()))
            .andExpect(jsonPath("$.nInscription").value(DEFAULT_N_INSCRIPTION.intValue()))
            .andExpect(jsonPath("$.dateReponseOffre").value(DEFAULT_DATE_REPONSE_OFFRE.toString()))
            .andExpect(jsonPath("$.nUniqueOffre").value(DEFAULT_N_UNIQUE_OFFRE.intValue()))
            .andExpect(jsonPath("$.nFournisseurExt").value(DEFAULT_N_FOURNISSEUR_EXT.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingAppelOffre() throws Exception {
        // Get the appelOffre
        restAppelOffreMockMvc.perform(get("/api/appel-offres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppelOffre() throws Exception {
        // Initialize the database
        appelOffreRepository.saveAndFlush(appelOffre);

        int databaseSizeBeforeUpdate = appelOffreRepository.findAll().size();

        // Update the appelOffre
        AppelOffre updatedAppelOffre = appelOffreRepository.findById(appelOffre.getId()).get();
        // Disconnect from session so that the updates on updatedAppelOffre are not directly saved in db
        em.detach(updatedAppelOffre);
        updatedAppelOffre
            .nInscription(UPDATED_N_INSCRIPTION)
            .dateReponseOffre(UPDATED_DATE_REPONSE_OFFRE)
            .nUniqueOffre(UPDATED_N_UNIQUE_OFFRE)
            .nFournisseurExt(UPDATED_N_FOURNISSEUR_EXT);

        restAppelOffreMockMvc.perform(put("/api/appel-offres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAppelOffre)))
            .andExpect(status().isOk());

        // Validate the AppelOffre in the database
        List<AppelOffre> appelOffreList = appelOffreRepository.findAll();
        assertThat(appelOffreList).hasSize(databaseSizeBeforeUpdate);
        AppelOffre testAppelOffre = appelOffreList.get(appelOffreList.size() - 1);
        assertThat(testAppelOffre.getnInscription()).isEqualTo(UPDATED_N_INSCRIPTION);
        assertThat(testAppelOffre.getDateReponseOffre()).isEqualTo(UPDATED_DATE_REPONSE_OFFRE);
        assertThat(testAppelOffre.getnUniqueOffre()).isEqualTo(UPDATED_N_UNIQUE_OFFRE);
        assertThat(testAppelOffre.getnFournisseurExt()).isEqualTo(UPDATED_N_FOURNISSEUR_EXT);
    }

    @Test
    @Transactional
    public void updateNonExistingAppelOffre() throws Exception {
        int databaseSizeBeforeUpdate = appelOffreRepository.findAll().size();

        // Create the AppelOffre

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAppelOffreMockMvc.perform(put("/api/appel-offres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appelOffre)))
            .andExpect(status().isBadRequest());

        // Validate the AppelOffre in the database
        List<AppelOffre> appelOffreList = appelOffreRepository.findAll();
        assertThat(appelOffreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAppelOffre() throws Exception {
        // Initialize the database
        appelOffreRepository.saveAndFlush(appelOffre);

        int databaseSizeBeforeDelete = appelOffreRepository.findAll().size();

        // Get the appelOffre
        restAppelOffreMockMvc.perform(delete("/api/appel-offres/{id}", appelOffre.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AppelOffre> appelOffreList = appelOffreRepository.findAll();
        assertThat(appelOffreList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppelOffre.class);
        AppelOffre appelOffre1 = new AppelOffre();
        appelOffre1.setId(1L);
        AppelOffre appelOffre2 = new AppelOffre();
        appelOffre2.setId(appelOffre1.getId());
        assertThat(appelOffre1).isEqualTo(appelOffre2);
        appelOffre2.setId(2L);
        assertThat(appelOffre1).isNotEqualTo(appelOffre2);
        appelOffre1.setId(null);
        assertThat(appelOffre1).isNotEqualTo(appelOffre2);
    }
}
