package com.cnote.novelas.service;

import com.cnote.novelas.service.dto.EpisodeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Episode.
 */
public interface EpisodeService {

    /**
     * Save a episode.
     *
     * @param episodeDTO the entity to save
     * @return the persisted entity
     */
    EpisodeDTO save(EpisodeDTO episodeDTO);

    /**
     * Get all the episodes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EpisodeDTO> findAll(Pageable pageable);

    /**
     * Get all the Episode with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<EpisodeDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" episode.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EpisodeDTO> findOne(Long id);

    /**
     * Delete the "id" episode.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
