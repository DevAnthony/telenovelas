package com.cnote.novelas.service;

import com.cnote.novelas.service.dto.TelenovelaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Telenovela.
 */
public interface TelenovelaService {

    /**
     * Save a telenovela.
     *
     * @param telenovelaDTO the entity to save
     * @return the persisted entity
     */
    TelenovelaDTO save(TelenovelaDTO telenovelaDTO);

    /**
     * Get all the telenovelas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TelenovelaDTO> findAll(Pageable pageable);

    /**
     * Get all the Telenovela with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<TelenovelaDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" telenovela.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TelenovelaDTO> findOne(Long id);

    /**
     * Delete the "id" telenovela.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
