package com.cnote.novelas.service.dto;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Episode entity.
 */
@ApiModel(description = "Episode entity. @author CNote.")
public class EpisodeDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer number;

    private String title;

    private Integer rating;

    @Size(max = 2048)
    private String downloadlink;

    @Size(max = 2048)
    private String streaminglink;


    private Set<ActorDTO> actors = new HashSet<>();

    private Long telenovelaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getDownloadlink() {
        return downloadlink;
    }

    public void setDownloadlink(String downloadlink) {
        this.downloadlink = downloadlink;
    }

    public String getStreaminglink() {
        return streaminglink;
    }

    public void setStreaminglink(String streaminglink) {
        this.streaminglink = streaminglink;
    }

    public Set<ActorDTO> getActors() {
        return actors;
    }

    public void setActors(Set<ActorDTO> actors) {
        this.actors = actors;
    }

    public Long getTelenovelaId() {
        return telenovelaId;
    }

    public void setTelenovelaId(Long telenovelaId) {
        this.telenovelaId = telenovelaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EpisodeDTO episodeDTO = (EpisodeDTO) o;
        if (episodeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), episodeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EpisodeDTO{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", title='" + getTitle() + "'" +
            ", rating=" + getRating() +
            ", downloadlink='" + getDownloadlink() + "'" +
            ", streaminglink='" + getStreaminglink() + "'" +
            ", telenovela=" + getTelenovelaId() +
            "}";
    }
}
