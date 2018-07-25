package ftg.ps.project.ms.offres.encheres.web.rest;

import com.codahale.metrics.annotation.Timed;
import ftg.ps.project.ms.offres.encheres.domain.Offre;
import ftg.ps.project.ms.offres.encheres.repository.OffreRepository;
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
 * REST controller for managing Offre.
 */
@RestController
@RequestMapping("/api")
public class OffreResource {

    private final Logger log = LoggerFactory.getLogger(OffreResource.class);

    private static final String ENTITY_NAME = "offre";

    private final OffreRepository offreRepository;

    public OffreResource(OffreRepository offreRepository) {
        this.offreRepository = offreRepository;
    }

    /**
     * POST  /offres : Create a new offre.
     *
     * @param offre the offre to create
     * @return the ResponseEntity with status 201 (Created) and with body the new offre, or with status 400 (Bad Request) if the offre has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/offres")
    @Timed
    public ResponseEntity<Offre> createOffre(@Valid @RequestBody Offre offre) throws URISyntaxException {
        log.debug("REST request to save Offre : {}", offre);
        if (offre.getId() != null) {
            throw new BadRequestAlertException("A new offre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Offre result = offreRepository.save(offre);
        return ResponseEntity.created(new URI("/api/offres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /offres : Updates an existing offre.
     *
     * @param offre the offre to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated offre,
     * or with status 400 (Bad Request) if the offre is not valid,
     * or with status 500 (Internal Server Error) if the offre couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/offres")
    @Timed
    public ResponseEntity<Offre> updateOffre(@Valid @RequestBody Offre offre) throws URISyntaxException {
        log.debug("REST request to update Offre : {}", offre);
        if (offre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Offre result = offreRepository.save(offre);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, offre.getId().toString()))
            .body(result);
    }

    /**
     * GET  /offres : get all the offres.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of offres in body
     */
    @GetMapping("/offres")
    @Timed
    public List<Offre> getAllOffres() {
        log.debug("REST request to get all Offres");
        return offreRepository.findAll();
    }

    /**
     * GET  /offres/:id : get the "id" offre.
     *
     * @param id the id of the offre to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the offre, or with status 404 (Not Found)
     */
    @GetMapping("/offres/{id}")
    @Timed
    public ResponseEntity<Offre> getOffre(@PathVariable Long id) {
        log.debug("REST request to get Offre : {}", id);
        Optional<Offre> offre = offreRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(offre);
    }

    /**
     * DELETE  /offres/:id : delete the "id" offre.
     *
     * @param id the id of the offre to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/offres/{id}")
    @Timed
    public ResponseEntity<Void> deleteOffre(@PathVariable Long id) {
        log.debug("REST request to delete Offre : {}", id);

        offreRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
