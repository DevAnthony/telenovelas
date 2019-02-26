package com.cnote.novelas.service.impl;

import com.cnote.novelas.service.EpisodeService;
import com.cnote.novelas.domain.Episode;
import com.cnote.novelas.repository.EpisodeRepository;
import com.cnote.novelas.service.dto.EpisodeDTO;
import com.cnote.novelas.service.mapper.EpisodeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Episode.
 */
@Service
@Transactional
public class EpisodeServiceImpl implements EpisodeService {

    private final Logger log = LoggerFactory.getLogger(EpisodeServiceImpl.class);

    private final EpisodeRepository episodeRepository;

    private final EpisodeMapper episodeMapper;

    public EpisodeServiceImpl(EpisodeRepository episodeRepository, EpisodeMapper episodeMapper) {
        this.episodeRepository = episodeRepository;
        this.episodeMapper = episodeMapper;
    }

    /**
     * Save a episode.
     *
     * @param episodeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EpisodeDTO save(EpisodeDTO episodeDTO) {
        log.debug("Request to save Episode : {}", episodeDTO);
        Episode episode = episodeMapper.toEntity(episodeDTO);
        episode = episodeRepository.save(episode);
        return episodeMapper.toDto(episode);
    }

    /**
     * Get all the episodes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EpisodeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Episodes");
        return episodeRepository.findAll(pageable)
            .map(episodeMapper::toDto);
    }

    /**
     * Get all the Episode with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<EpisodeDTO> findAllWithEagerRelationships(Pageable pageable) {
        return episodeRepository.findAllWithEagerRelationships(pageable).map(episodeMapper::toDto);
    }
    

    /**
     * Get one episode by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EpisodeDTO> findOne(Long id) {
        log.debug("Request to get Episode : {}", id);
        return episodeRepository.findOneWithEagerRelationships(id)
            .map(episodeMapper::toDto);
    }

    /**
     * Delete the episode by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Episode : {}", id);        episodeRepository.deleteById(id);
    }
}
