package com.cnote.novelas.service;

import com.cnote.novelas.service.dto.ActorDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Actor.
 */
public interface ActorService {

    /**
     * Save a actor.
     *
     * @param actorDTO the entity to save
     * @return the persisted entity
     */
    ActorDTO save(ActorDTO actorDTO);

    /**
     * Get all the actors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ActorDTO> findAll(Pageable pageable);


    /**
     * Get the "id" actor.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ActorDTO> findOne(Long id);

    /**
     * Delete the "id" actor.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
