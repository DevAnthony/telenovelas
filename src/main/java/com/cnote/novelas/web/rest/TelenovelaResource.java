package com.cnote.novelas.web.rest;
import com.cnote.novelas.service.TelenovelaService;
import com.cnote.novelas.web.rest.errors.BadRequestAlertException;
import com.cnote.novelas.web.rest.util.HeaderUtil;
import com.cnote.novelas.web.rest.util.PaginationUtil;
import com.cnote.novelas.service.dto.TelenovelaDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Telenovela.
 */
@RestController
@RequestMapping("/api")
public class TelenovelaResource {

    private final Logger log = LoggerFactory.getLogger(TelenovelaResource.class);

    private static final String ENTITY_NAME = "telenovela";

    private final TelenovelaService telenovelaService;

    public TelenovelaResource(TelenovelaService telenovelaService) {
        this.telenovelaService = telenovelaService;
    }

    /**
     * POST  /telenovelas : Create a new telenovela.
     *
     * @param telenovelaDTO the telenovelaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new telenovelaDTO, or with status 400 (Bad Request) if the telenovela has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/telenovelas")
    public ResponseEntity<TelenovelaDTO> createTelenovela(@Valid @RequestBody TelenovelaDTO telenovelaDTO) throws URISyntaxException {
        log.debug("REST request to save Telenovela : {}", telenovelaDTO);
        if (telenovelaDTO.getId() != null) {
            throw new BadRequestAlertException("A new telenovela cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TelenovelaDTO result = telenovelaService.save(telenovelaDTO);
        return ResponseEntity.created(new URI("/api/telenovelas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /telenovelas : Updates an existing telenovela.
     *
     * @param telenovelaDTO the telenovelaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated telenovelaDTO,
     * or with status 400 (Bad Request) if the telenovelaDTO is not valid,
     * or with status 500 (Internal Server Error) if the telenovelaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/telenovelas")
    public ResponseEntity<TelenovelaDTO> updateTelenovela(@Valid @RequestBody TelenovelaDTO telenovelaDTO) throws URISyntaxException {
        log.debug("REST request to update Telenovela : {}", telenovelaDTO);
        if (telenovelaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TelenovelaDTO result = telenovelaService.save(telenovelaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, telenovelaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /telenovelas : get all the telenovelas.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of telenovelas in body
     */
    @GetMapping("/telenovelas")
    public ResponseEntity<List<TelenovelaDTO>> getAllTelenovelas(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Telenovelas");
        Page<TelenovelaDTO> page;
        if (eagerload) {
            page = telenovelaService.findAllWithEagerRelationships(pageable);
        } else {
            page = telenovelaService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/telenovelas?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /telenovelas/:id : get the "id" telenovela.
     *
     * @param id the id of the telenovelaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the telenovelaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/telenovelas/{id}")
    public ResponseEntity<TelenovelaDTO> getTelenovela(@PathVariable Long id) {
        log.debug("REST request to get Telenovela : {}", id);
        Optional<TelenovelaDTO> telenovelaDTO = telenovelaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(telenovelaDTO);
    }

    /**
     * DELETE  /telenovelas/:id : delete the "id" telenovela.
     *
     * @param id the id of the telenovelaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/telenovelas/{id}")
    public ResponseEntity<Void> deleteTelenovela(@PathVariable Long id) {
        log.debug("REST request to delete Telenovela : {}", id);
        telenovelaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
