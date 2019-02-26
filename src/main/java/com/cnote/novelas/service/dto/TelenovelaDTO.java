package com.cnote.novelas.service.dto;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Telenovela entity.
 */
@ApiModel(description = "Telenovela entity. @author CNote.")
public class TelenovelaDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    @Lob
    private String summary;

    private Integer rating;

    private Integer year;

    @Size(max = 15)
    private String country;

    @Size(max = 2048)
    private String playlist;

    @Size(max = 2048)
    private String thumbnail;

    @Size(max = 2048)
    private String poster;

    @Size(max = 2048)
    private String background;


    private Set<ActorDTO> actors = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPlaylist() {
        return playlist;
    }

    public void setPlaylist(String playlist) {
        this.playlist = playlist;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public Set<ActorDTO> getActors() {
        return actors;
    }

    public void setActors(Set<ActorDTO> actors) {
        this.actors = actors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TelenovelaDTO telenovelaDTO = (TelenovelaDTO) o;
        if (telenovelaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), telenovelaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TelenovelaDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", summary='" + getSummary() + "'" +
            ", rating=" + getRating() +
            ", year=" + getYear() +
            ", country='" + getCountry() + "'" +
            ", playlist='" + getPlaylist() + "'" +
            ", thumbnail='" + getThumbnail() + "'" +
            ", poster='" + getPoster() + "'" +
            ", background='" + getBackground() + "'" +
            "}";
    }
}
