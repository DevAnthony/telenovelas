package com.cnote.novelas.service.impl;

import com.cnote.novelas.service.TelenovelaService;
import com.cnote.novelas.domain.Telenovela;
import com.cnote.novelas.repository.TelenovelaRepository;
import com.cnote.novelas.service.dto.TelenovelaDTO;
import com.cnote.novelas.service.mapper.TelenovelaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Telenovela.
 */
@Service
@Transactional
public class TelenovelaServiceImpl implements TelenovelaService {

    private final Logger log = LoggerFactory.getLogger(TelenovelaServiceImpl.class);

    private final TelenovelaRepository telenovelaRepository;

    private final TelenovelaMapper telenovelaMapper;

    public TelenovelaServiceImpl(TelenovelaRepository telenovelaRepository, TelenovelaMapper telenovelaMapper) {
        this.telenovelaRepository = telenovelaRepository;
        this.telenovelaMapper = telenovelaMapper;
    }

    /**
     * Save a telenovela.
     *
     * @param telenovelaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TelenovelaDTO save(TelenovelaDTO telenovelaDTO) {
        log.debug("Request to save Telenovela : {}", telenovelaDTO);
        Telenovela telenovela = telenovelaMapper.toEntity(telenovelaDTO);
        telenovela = telenovelaRepository.save(telenovela);
        return telenovelaMapper.toDto(telenovela);
    }

    /**
     * Get all the telenovelas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TelenovelaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Telenovelas");
        return telenovelaRepository.findAll(pageable)
            .map(telenovelaMapper::toDto);
    }

    /**
     * Get all the Telenovela with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<TelenovelaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return telenovelaRepository.findAllWithEagerRelationships(pageable).map(telenovelaMapper::toDto);
    }
    

    /**
     * Get one telenovela by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TelenovelaDTO> findOne(Long id) {
        log.debug("Request to get Telenovela : {}", id);
        return telenovelaRepository.findOneWithEagerRelationships(id)
            .map(telenovelaMapper::toDto);
    }

    /**
     * Delete the telenovela by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Telenovela : {}", id);        telenovelaRepository.deleteById(id);
    }
}
