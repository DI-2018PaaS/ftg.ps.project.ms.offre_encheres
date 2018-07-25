package ftg.ps.project.ms.offres.encheres.web.rest;

import com.codahale.metrics.annotation.Timed;
import ftg.ps.project.ms.offres.encheres.domain.OffreEnchere;
import ftg.ps.project.ms.offres.encheres.repository.OffreEnchereRepository;
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
 * REST controller for managing OffreEnchere.
 */
@RestController
@RequestMapping("/api")
public class OffreEnchereResource {

    private final Logger log = LoggerFactory.getLogger(OffreEnchereResource.class);

    private static final String ENTITY_NAME = "offreEnchere";

    private final OffreEnchereRepository offreEnchereRepository;

    public OffreEnchereResource(OffreEnchereRepository offreEnchereRepository) {
        this.offreEnchereRepository = offreEnchereRepository;
    }

    /**
     * POST  /offre-encheres : Create a new offreEnchere.
     *
     * @param offreEnchere the offreEnchere to create
     * @return the ResponseEntity with status 201 (Created) and with body the new offreEnchere, or with status 400 (Bad Request) if the offreEnchere has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/offre-encheres")
    @Timed
    public ResponseEntity<OffreEnchere> createOffreEnchere(@Valid @RequestBody OffreEnchere offreEnchere) throws URISyntaxException {
        log.debug("REST request to save OffreEnchere : {}", offreEnchere);
        if (offreEnchere.getId() != null) {
            throw new BadRequestAlertException("A new offreEnchere cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OffreEnchere result = offreEnchereRepository.save(offreEnchere);
        return ResponseEntity.created(new URI("/api/offre-encheres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /offre-encheres : Updates an existing offreEnchere.
     *
     * @param offreEnchere the offreEnchere to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated offreEnchere,
     * or with status 400 (Bad Request) if the offreEnchere is not valid,
     * or with status 500 (Internal Server Error) if the offreEnchere couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/offre-encheres")
    @Timed
    public ResponseEntity<OffreEnchere> updateOffreEnchere(@Valid @RequestBody OffreEnchere offreEnchere) throws URISyntaxException {
        log.debug("REST request to update OffreEnchere : {}", offreEnchere);
        if (offreEnchere.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OffreEnchere result = offreEnchereRepository.save(offreEnchere);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, offreEnchere.getId().toString()))
            .body(result);
    }

    /**
     * GET  /offre-encheres : get all the offreEncheres.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of offreEncheres in body
     */
    @GetMapping("/offre-encheres")
    @Timed
    public List<OffreEnchere> getAllOffreEncheres() {
        log.debug("REST request to get all OffreEncheres");
        return offreEnchereRepository.findAll();
    }

    /**
     * GET  /offre-encheres/:id : get the "id" offreEnchere.
     *
     * @param id the id of the offreEnchere to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the offreEnchere, or with status 404 (Not Found)
     */
    @GetMapping("/offre-encheres/{id}")
    @Timed
    public ResponseEntity<OffreEnchere> getOffreEnchere(@PathVariable Long id) {
        log.debug("REST request to get OffreEnchere : {}", id);
        Optional<OffreEnchere> offreEnchere = offreEnchereRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(offreEnchere);
    }

    /**
     * DELETE  /offre-encheres/:id : delete the "id" offreEnchere.
     *
     * @param id the id of the offreEnchere to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/offre-encheres/{id}")
    @Timed
    public ResponseEntity<Void> deleteOffreEnchere(@PathVariable Long id) {
        log.debug("REST request to delete OffreEnchere : {}", id);

        offreEnchereRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
