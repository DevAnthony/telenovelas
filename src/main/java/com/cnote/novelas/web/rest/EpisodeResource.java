package com.cnote.novelas.web.rest;
import com.cnote.novelas.service.EpisodeService;
import com.cnote.novelas.web.rest.errors.BadRequestAlertException;
import com.cnote.novelas.web.rest.util.HeaderUtil;
import com.cnote.novelas.web.rest.util.PaginationUtil;
import com.cnote.novelas.service.dto.EpisodeDTO;
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
 * REST controller for managing Episode.
 */
@RestController
@RequestMapping("/api")
public class EpisodeResource {

    private final Logger log = LoggerFactory.getLogger(EpisodeResource.class);

    private static final String ENTITY_NAME = "episode";

    private final EpisodeService episodeService;

    public EpisodeResource(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    /**
     * POST  /episodes : Create a new episode.
     *
     * @param episodeDTO the episodeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new episodeDTO, or with status 400 (Bad Request) if the episode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/episodes")
    public ResponseEntity<EpisodeDTO> createEpisode(@Valid @RequestBody EpisodeDTO episodeDTO) throws URISyntaxException {
        log.debug("REST request to save Episode : {}", episodeDTO);
        if (episodeDTO.getId() != null) {
            throw new BadRequestAlertException("A new episode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EpisodeDTO result = episodeService.save(episodeDTO);
        return ResponseEntity.created(new URI("/api/episodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /episodes : Updates an existing episode.
     *
     * @param episodeDTO the episodeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated episodeDTO,
     * or with status 400 (Bad Request) if the episodeDTO is not valid,
     * or with status 500 (Internal Server Error) if the episodeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/episodes")
    public ResponseEntity<EpisodeDTO> updateEpisode(@Valid @RequestBody EpisodeDTO episodeDTO) throws URISyntaxException {
        log.debug("REST request to update Episode : {}", episodeDTO);
        if (episodeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EpisodeDTO result = episodeService.save(episodeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, episodeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /episodes : get all the episodes.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of episodes in body
     */
    @GetMapping("/episodes")
    public ResponseEntity<List<EpisodeDTO>> getAllEpisodes(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Episodes");
        Page<EpisodeDTO> page;
        if (eagerload) {
            page = episodeService.findAllWithEagerRelationships(pageable);
        } else {
            page = episodeService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/episodes?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /episodes/:id : get the "id" episode.
     *
     * @param id the id of the episodeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the episodeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/episodes/{id}")
    public ResponseEntity<EpisodeDTO> getEpisode(@PathVariable Long id) {
        log.debug("REST request to get Episode : {}", id);
        Optional<EpisodeDTO> episodeDTO = episodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(episodeDTO);
    }

    /**
     * DELETE  /episodes/:id : delete the "id" episode.
     *
     * @param id the id of the episodeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/episodes/{id}")
    public ResponseEntity<Void> deleteEpisode(@PathVariable Long id) {
        log.debug("REST request to delete Episode : {}", id);
        episodeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
