package ftg.ps.project.ms.offres.encheres.web.rest;

import com.codahale.metrics.annotation.Timed;
import ftg.ps.project.ms.offres.encheres.domain.Enchere;
import ftg.ps.project.ms.offres.encheres.repository.EnchereRepository;
import ftg.ps.project.ms.offres.encheres.web.rest.errors.BadRequestAlertException;
import ftg.ps.project.ms.offres.encheres.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Enchere.
 */
@RestController
@RequestMapping("/api")
public class EnchereResource {

    private final Logger log = LoggerFactory.getLogger(EnchereResource.class);

    private static final String ENTITY_NAME = "enchere";

    private final EnchereRepository enchereRepository;

    public EnchereResource(EnchereRepository enchereRepository) {
        this.enchereRepository = enchereRepository;
    }

    /**
     * POST  /encheres : Create a new enchere.
     *
     * @param enchere the enchere to create
     * @return the ResponseEntity with status 201 (Created) and with body the new enchere, or with status 400 (Bad Request) if the enchere has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/encheres")
    @Timed
    public ResponseEntity<Enchere> createEnchere(@Valid @RequestBody Enchere enchere) throws URISyntaxException {
        log.debug("REST request to save Enchere : {}", enchere);
        if (enchere.getId() != null) {
            throw new BadRequestAlertException("A new enchere cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Enchere result = enchereRepository.save(enchere);
        return ResponseEntity.created(new URI("/api/encheres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /encheres : Updates an existing enchere.
     *
     * @param enchere the enchere to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated enchere,
     * or with status 400 (Bad Request) if the enchere is not valid,
     * or with status 500 (Internal Server Error) if the enchere couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/encheres")
    @Timed
    public ResponseEntity<Enchere> updateEnchere(@Valid @RequestBody Enchere enchere) throws URISyntaxException {
        log.debug("REST request to update Enchere : {}", enchere);
        if (enchere.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Enchere result = enchereRepository.save(enchere);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, enchere.getId().toString()))
            .body(result);
    }

    /**
     * GET  /encheres : get all the encheres.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of encheres in body
     */
    @GetMapping("/encheres")
    @Timed
    public List<Enchere> getAllEncheres() {
        log.debug("REST request to get all Encheres");
        return enchereRepository.findAll();
    }

    /**
     * GET  /encheres/:id : get the "id" enchere.
     *
     * @param id the id of the enchere to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the enchere, or with status 404 (Not Found)
     */
    @GetMapping("/encheres/{id}")
    @Timed
    public ResponseEntity<Enchere> getEnchere(@PathVariable Long id) {
        log.debug("REST request to get Enchere : {}", id);
        Optional<Enchere> enchere = enchereRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(enchere);
    }

    /**
     * DELETE  /encheres/:id : delete the "id" enchere.
     *
     * @param id the id of the enchere to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/encheres/{id}")
    @Timed
    public ResponseEntity<Void> deleteEnchere(@PathVariable Long id) {
        log.debug("REST request to delete Enchere : {}", id);

        enchereRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
