package com.cnote.novelas.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Episode entity.
 * @author CNote.
 */
@Entity
@Table(name = "episode")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Episode implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_number", nullable = false)
    private Integer number;

    @Column(name = "title")
    private String title;

    @Column(name = "rating")
    private Integer rating;

    @Size(max = 2048)
    @Column(name = "downloadlink", length = 2048)
    private String downloadlink;

    @Size(max = 2048)
    @Column(name = "streaminglink", length = 2048)
    private String streaminglink;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "episode_actor",
               joinColumns = @JoinColumn(name = "episode_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "id"))
    private Set<Actor> actors = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("episodes")
    private Telenovela telenovela;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public Episode number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public Episode title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRating() {
        return rating;
    }

    public Episode rating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getDownloadlink() {
        return downloadlink;
    }

    public Episode downloadlink(String downloadlink) {
        this.downloadlink = downloadlink;
        return this;
    }

    public void setDownloadlink(String downloadlink) {
        this.downloadlink = downloadlink;
    }

    public String getStreaminglink() {
        return streaminglink;
    }

    public Episode streaminglink(String streaminglink) {
        this.streaminglink = streaminglink;
        return this;
    }

    public void setStreaminglink(String streaminglink) {
        this.streaminglink = streaminglink;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public Episode actors(Set<Actor> actors) {
        this.actors = actors;
        return this;
    }

    public Episode addActor(Actor actor) {
        this.actors.add(actor);
        actor.getEpisodes().add(this);
        return this;
    }

    public Episode removeActor(Actor actor) {
        this.actors.remove(actor);
        actor.getEpisodes().remove(this);
        return this;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    public Telenovela getTelenovela() {
        return telenovela;
    }

    public Episode telenovela(Telenovela telenovela) {
        this.telenovela = telenovela;
        return this;
    }

    public void setTelenovela(Telenovela telenovela) {
        this.telenovela = telenovela;
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
        Episode episode = (Episode) o;
        if (episode.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), episode.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Episode{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", title='" + getTitle() + "'" +
            ", rating=" + getRating() +
            ", downloadlink='" + getDownloadlink() + "'" +
            ", streaminglink='" + getStreaminglink() + "'" +
            "}";
    }
}
