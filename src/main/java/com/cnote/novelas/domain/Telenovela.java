package com.cnote.novelas.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Telenovela entity.
 * @author CNote.
 */
@Entity
@Table(name = "telenovela")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Telenovela implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "summary")
    private String summary;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "jhi_year")
    private Integer year;

    @Size(max = 15)
    @Column(name = "country", length = 15)
    private String country;

    @Size(max = 2048)
    @Column(name = "playlist", length = 2048)
    private String playlist;

    @Size(max = 2048)
    @Column(name = "thumbnail", length = 2048)
    private String thumbnail;

    @Size(max = 2048)
    @Column(name = "poster", length = 2048)
    private String poster;

    @Size(max = 2048)
    @Column(name = "background", length = 2048)
    private String background;

    @OneToMany(mappedBy = "telenovela")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Episode> episodes = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "telenovela_actor",
               joinColumns = @JoinColumn(name = "telenovela_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "id"))
    private Set<Actor> actors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Telenovela title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public Telenovela summary(String summary) {
        this.summary = summary;
        return this;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getRating() {
        return rating;
    }

    public Telenovela rating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getYear() {
        return year;
    }

    public Telenovela year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public Telenovela country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPlaylist() {
        return playlist;
    }

    public Telenovela playlist(String playlist) {
        this.playlist = playlist;
        return this;
    }

    public void setPlaylist(String playlist) {
        this.playlist = playlist;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Telenovela thumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPoster() {
        return poster;
    }

    public Telenovela poster(String poster) {
        this.poster = poster;
        return this;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getBackground() {
        return background;
    }

    public Telenovela background(String background) {
        this.background = background;
        return this;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public Set<Episode> getEpisodes() {
        return episodes;
    }

    public Telenovela episodes(Set<Episode> episodes) {
        this.episodes = episodes;
        return this;
    }

    public Telenovela addEpisode(Episode episode) {
        this.episodes.add(episode);
        episode.setTelenovela(this);
        return this;
    }

    public Telenovela removeEpisode(Episode episode) {
        this.episodes.remove(episode);
        episode.setTelenovela(null);
        return this;
    }

    public void setEpisodes(Set<Episode> episodes) {
        this.episodes = episodes;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public Telenovela actors(Set<Actor> actors) {
        this.actors = actors;
        return this;
    }

    public Telenovela addActor(Actor actor) {
        this.actors.add(actor);
        actor.getTelenovelas().add(this);
        return this;
    }

    public Telenovela removeActor(Actor actor) {
        this.actors.remove(actor);
        actor.getTelenovelas().remove(this);
        return this;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Telenovela telenovela = (Telenovela) o;
        if (telenovela.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), telenovela.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Telenovela{" +
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
