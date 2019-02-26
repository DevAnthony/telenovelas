package com.cnote.novelas.service.mapper;

import com.cnote.novelas.domain.*;
import com.cnote.novelas.service.dto.TelenovelaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Telenovela and its DTO TelenovelaDTO.
 */
@Mapper(componentModel = "spring", uses = {ActorMapper.class})
public interface TelenovelaMapper extends EntityMapper<TelenovelaDTO, Telenovela> {


    @Mapping(target = "episodes", ignore = true)
    Telenovela toEntity(TelenovelaDTO telenovelaDTO);

    default Telenovela fromId(Long id) {
        if (id == null) {
            return null;
        }
        Telenovela telenovela = new Telenovela();
        telenovela.setId(id);
        return telenovela;
    }
}
