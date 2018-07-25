package ftg.ps.project.ms.offres.encheres.web.rest;

import com.codahale.metrics.annotation.Timed;
import ftg.ps.project.ms.offres.encheres.domain.Contrat;
import ftg.ps.project.ms.offres.encheres.repository.ContratRepository;
import ftg.ps.project.ms.offres.encheres.web.rest.errors.BadRequestAlertException;
import ftg.ps.project.ms.offres.encheres.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Contrat.
 */
@RestController
@RequestMapping("/api")
public class ContratResource {

    private final Logger log = LoggerFactory.getLogger(ContratResource.class);

    private static final String ENTITY_NAME = "contrat";

    private final ContratRepository contratRepository;

    public ContratResource(ContratRepository contratRepository) {
        this.contratRepository = contratRepository;
    }

    /**
     * POST  /contrats : Create a new contrat.
     *
     * @param contrat the contrat to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contrat, or with status 400 (Bad Request) if the contrat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contrats")
    @Timed
    public ResponseEntity<Contrat> createContrat(@RequestBody Contrat contrat) throws URISyntaxException {
        log.debug("REST request to save Contrat : {}", contrat);
        if (contrat.getId() != null) {
            throw new BadRequestAlertException("A new contrat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Contrat result = contratRepository.save(contrat);
        return ResponseEntity.created(new URI("/api/contrats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contrats : Updates an existing contrat.
     *
     * @param contrat the contrat to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contrat,
     * or with status 400 (Bad Request) if the contrat is not valid,
     * or with status 500 (Internal Server Error) if the contrat couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contrats")
    @Timed
    public ResponseEntity<Contrat> updateContrat(@RequestBody Contrat contrat) throws URISyntaxException {
        log.debug("REST request to update Contrat : {}", contrat);
        if (contrat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Contrat result = contratRepository.save(contrat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contrat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contrats : get all the contrats.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contrats in body
     */
    @GetMapping("/contrats")
    @Timed
    public List<Contrat> getAllContrats() {
        log.debug("REST request to get all Contrats");
        return contratRepository.findAll();
    }

    /**
     * GET  /contrats/:id : get the "id" contrat.
     *
     * @param id the id of the contrat to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contrat, or with status 404 (Not Found)
     */
    @GetMapping("/contrats/{id}")
    @Timed
    public ResponseEntity<Contrat> getContrat(@PathVariable Long id) {
        log.debug("REST request to get Contrat : {}", id);
        Optional<Contrat> contrat = contratRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(contrat);
    }

    /**
     * DELETE  /contrats/:id : delete the "id" contrat.
     *
     * @param id the id of the contrat to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contrats/{id}")
    @Timed
    public ResponseEntity<Void> deleteContrat(@PathVariable Long id) {
        log.debug("REST request to delete Contrat : {}", id);

        contratRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
