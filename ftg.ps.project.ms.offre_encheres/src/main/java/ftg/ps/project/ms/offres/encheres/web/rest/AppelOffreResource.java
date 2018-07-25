package ftg.ps.project.ms.offres.encheres.web.rest;

import com.codahale.metrics.annotation.Timed;
import ftg.ps.project.ms.offres.encheres.domain.AppelOffre;
import ftg.ps.project.ms.offres.encheres.repository.AppelOffreRepository;
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
 * REST controller for managing AppelOffre.
 */
@RestController
@RequestMapping("/api")
public class AppelOffreResource {

    private final Logger log = LoggerFactory.getLogger(AppelOffreResource.class);

    private static final String ENTITY_NAME = "appelOffre";

    private final AppelOffreRepository appelOffreRepository;

    public AppelOffreResource(AppelOffreRepository appelOffreRepository) {
        this.appelOffreRepository = appelOffreRepository;
    }

    /**
     * POST  /appel-offres : Create a new appelOffre.
     *
     * @param appelOffre the appelOffre to create
     * @return the ResponseEntity with status 201 (Created) and with body the new appelOffre, or with status 400 (Bad Request) if the appelOffre has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/appel-offres")
    @Timed
    public ResponseEntity<AppelOffre> createAppelOffre(@Valid @RequestBody AppelOffre appelOffre) throws URISyntaxException {
        log.debug("REST request to save AppelOffre : {}", appelOffre);
        if (appelOffre.getId() != null) {
            throw new BadRequestAlertException("A new appelOffre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppelOffre result = appelOffreRepository.save(appelOffre);
        return ResponseEntity.created(new URI("/api/appel-offres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /appel-offres : Updates an existing appelOffre.
     *
     * @param appelOffre the appelOffre to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated appelOffre,
     * or with status 400 (Bad Request) if the appelOffre is not valid,
     * or with status 500 (Internal Server Error) if the appelOffre couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/appel-offres")
    @Timed
    public ResponseEntity<AppelOffre> updateAppelOffre(@Valid @RequestBody AppelOffre appelOffre) throws URISyntaxException {
        log.debug("REST request to update AppelOffre : {}", appelOffre);
        if (appelOffre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AppelOffre result = appelOffreRepository.save(appelOffre);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, appelOffre.getId().toString()))
            .body(result);
    }

    /**
     * GET  /appel-offres : get all the appelOffres.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of appelOffres in body
     */
    @GetMapping("/appel-offres")
    @Timed
    public List<AppelOffre> getAllAppelOffres() {
        log.debug("REST request to get all AppelOffres");
        return appelOffreRepository.findAll();
    }

    /**
     * GET  /appel-offres/:id : get the "id" appelOffre.
     *
     * @param id the id of the appelOffre to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the appelOffre, or with status 404 (Not Found)
     */
    @GetMapping("/appel-offres/{id}")
    @Timed
    public ResponseEntity<AppelOffre> getAppelOffre(@PathVariable Long id) {
        log.debug("REST request to get AppelOffre : {}", id);
        Optional<AppelOffre> appelOffre = appelOffreRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(appelOffre);
    }

    /**
     * DELETE  /appel-offres/:id : delete the "id" appelOffre.
     *
     * @param id the id of the appelOffre to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/appel-offres/{id}")
    @Timed
    public ResponseEntity<Void> deleteAppelOffre(@PathVariable Long id) {
        log.debug("REST request to delete AppelOffre : {}", id);

        appelOffreRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
