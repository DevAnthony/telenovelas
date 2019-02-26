package com.cnote.novelas.service.mapper;

import com.cnote.novelas.domain.*;
import com.cnote.novelas.service.dto.EpisodeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Episode and its DTO EpisodeDTO.
 */
@Mapper(componentModel = "spring", uses = {ActorMapper.class, TelenovelaMapper.class})
public interface EpisodeMapper extends EntityMapper<EpisodeDTO, Episode> {

    @Mapping(source = "telenovela.id", target = "telenovelaId")
    EpisodeDTO toDto(Episode episode);

    @Mapping(source = "telenovelaId", target = "telenovela")
    Episode toEntity(EpisodeDTO episodeDTO);

    default Episode fromId(Long id) {
        if (id == null) {
            return null;
        }
        Episode episode = new Episode();
        episode.setId(id);
        return episode;
    }
}
