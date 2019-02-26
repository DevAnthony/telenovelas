package com.cnote.novelas.service.mapper;

import com.cnote.novelas.domain.*;
import com.cnote.novelas.service.dto.ActorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Actor and its DTO ActorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ActorMapper extends EntityMapper<ActorDTO, Actor> {


    @Mapping(target = "telenovelas", ignore = true)
    @Mapping(target = "episodes", ignore = true)
    Actor toEntity(ActorDTO actorDTO);

    default Actor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Actor actor = new Actor();
        actor.setId(id);
        return actor;
    }
}
