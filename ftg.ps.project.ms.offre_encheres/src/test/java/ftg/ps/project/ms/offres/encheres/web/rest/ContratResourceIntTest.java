package ftg.ps.project.ms.offres.encheres.web.rest;

import ftg.ps.project.ms.offres.encheres.MsencheresoffresApp;

import ftg.ps.project.ms.offres.encheres.config.SecurityBeanOverrideConfiguration;

import ftg.ps.project.ms.offres.encheres.domain.Contrat;
import ftg.ps.project.ms.offres.encheres.repository.ContratRepository;
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
 * Test class for the ContratResource REST controller.
 *
 * @see ContratResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, MsencheresoffresApp.class})
public class ContratResourceIntTest {

    private static final Long DEFAULT_N_CONTRAT = 1L;
    private static final Long UPDATED_N_CONTRAT = 2L;

    private static final LocalDate DEFAULT_DATE_SIGNATURE_CONTRAT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_SIGNATURE_CONTRAT = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_N_FOURNISSEUR_EXT = 1L;
    private static final Long UPDATED_N_FOURNISSEUR_EXT = 2L;

    @Autowired
    private ContratRepository contratRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContratMockMvc;

    private Contrat contrat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContratResource contratResource = new ContratResource(contratRepository);
        this.restContratMockMvc = MockMvcBuilders.standaloneSetup(contratResource)
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
    public static Contrat createEntity(EntityManager em) {
        Contrat contrat = new Contrat()
            .nContrat(DEFAULT_N_CONTRAT)
            .dateSignatureContrat(DEFAULT_DATE_SIGNATURE_CONTRAT)
            .nFournisseurExt(DEFAULT_N_FOURNISSEUR_EXT);
        return contrat;
    }

    @Before
    public void initTest() {
        contrat = createEntity(em);
    }

    @Test
    @Transactional
    public void createContrat() throws Exception {
        int databaseSizeBeforeCreate = contratRepository.findAll().size();

        // Create the Contrat
        restContratMockMvc.perform(post("/api/contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contrat)))
            .andExpect(status().isCreated());

        // Validate the Contrat in the database
        List<Contrat> contratList = contratRepository.findAll();
        assertThat(contratList).hasSize(databaseSizeBeforeCreate + 1);
        Contrat testContrat = contratList.get(contratList.size() - 1);
        assertThat(testContrat.getnContrat()).isEqualTo(DEFAULT_N_CONTRAT);
        assertThat(testContrat.getDateSignatureContrat()).isEqualTo(DEFAULT_DATE_SIGNATURE_CONTRAT);
        assertThat(testContrat.getnFournisseurExt()).isEqualTo(DEFAULT_N_FOURNISSEUR_EXT);
    }

    @Test
    @Transactional
    public void createContratWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contratRepository.findAll().size();

        // Create the Contrat with an existing ID
        contrat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContratMockMvc.perform(post("/api/contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contrat)))
            .andExpect(status().isBadRequest());

        // Validate the Contrat in the database
        List<Contrat> contratList = contratRepository.findAll();
        assertThat(contratList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllContrats() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList
        restContratMockMvc.perform(get("/api/contrats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contrat.getId().intValue())))
            .andExpect(jsonPath("$.[*].nContrat").value(hasItem(DEFAULT_N_CONTRAT.intValue())))
            .andExpect(jsonPath("$.[*].dateSignatureContrat").value(hasItem(DEFAULT_DATE_SIGNATURE_CONTRAT.toString())))
            .andExpect(jsonPath("$.[*].nFournisseurExt").value(hasItem(DEFAULT_N_FOURNISSEUR_EXT.intValue())));
    }
    

    @Test
    @Transactional
    public void getContrat() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get the contrat
        restContratMockMvc.perform(get("/api/contrats/{id}", contrat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contrat.getId().intValue()))
            .andExpect(jsonPath("$.nContrat").value(DEFAULT_N_CONTRAT.intValue()))
            .andExpect(jsonPath("$.dateSignatureContrat").value(DEFAULT_DATE_SIGNATURE_CONTRAT.toString()))
            .andExpect(jsonPath("$.nFournisseurExt").value(DEFAULT_N_FOURNISSEUR_EXT.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingContrat() throws Exception {
        // Get the contrat
        restContratMockMvc.perform(get("/api/contrats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContrat() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        int databaseSizeBeforeUpdate = contratRepository.findAll().size();

        // Update the contrat
        Contrat updatedContrat = contratRepository.findById(contrat.getId()).get();
        // Disconnect from session so that the updates on updatedContrat are not directly saved in db
        em.detach(updatedContrat);
        updatedContrat
            .nContrat(UPDATED_N_CONTRAT)
            .dateSignatureContrat(UPDATED_DATE_SIGNATURE_CONTRAT)
            .nFournisseurExt(UPDATED_N_FOURNISSEUR_EXT);

        restContratMockMvc.perform(put("/api/contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContrat)))
            .andExpect(status().isOk());

        // Validate the Contrat in the database
        List<Contrat> contratList = contratRepository.findAll();
        assertThat(contratList).hasSize(databaseSizeBeforeUpdate);
        Contrat testContrat = contratList.get(contratList.size() - 1);
        assertThat(testContrat.getnContrat()).isEqualTo(UPDATED_N_CONTRAT);
        assertThat(testContrat.getDateSignatureContrat()).isEqualTo(UPDATED_DATE_SIGNATURE_CONTRAT);
        assertThat(testContrat.getnFournisseurExt()).isEqualTo(UPDATED_N_FOURNISSEUR_EXT);
    }

    @Test
    @Transactional
    public void updateNonExistingContrat() throws Exception {
        int databaseSizeBeforeUpdate = contratRepository.findAll().size();

        // Create the Contrat

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContratMockMvc.perform(put("/api/contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contrat)))
            .andExpect(status().isBadRequest());

        // Validate the Contrat in the database
        List<Contrat> contratList = contratRepository.findAll();
        assertThat(contratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContrat() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        int databaseSizeBeforeDelete = contratRepository.findAll().size();

        // Get the contrat
        restContratMockMvc.perform(delete("/api/contrats/{id}", contrat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Contrat> contratList = contratRepository.findAll();
        assertThat(contratList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contrat.class);
        Contrat contrat1 = new Contrat();
        contrat1.setId(1L);
        Contrat contrat2 = new Contrat();
        contrat2.setId(contrat1.getId());
        assertThat(contrat1).isEqualTo(contrat2);
        contrat2.setId(2L);
        assertThat(contrat1).isNotEqualTo(contrat2);
        contrat1.setId(null);
        assertThat(contrat1).isNotEqualTo(contrat2);
    }
}
