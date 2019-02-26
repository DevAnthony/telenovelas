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
 * Actor entity.
 * @author CNote.
 */
@Entity
@Table(name = "actor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Actor implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "middlename")
    private String middlename;

    @NotNull
    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Size(max = 2048)
    @Column(name = "profilepicture", length = 2048)
    private String profilepicture;

    @Size(max = 2048)
    @Column(name = "biolink", length = 2048)
    private String biolink;

    @ManyToMany(mappedBy = "actors")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Telenovela> telenovelas = new HashSet<>();

    @ManyToMany(mappedBy = "actors")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Episode> episodes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public Actor firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public Actor middlename(String middlename) {
        this.middlename = middlename;
        return this;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public Actor lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getProfilepicture() {
        return profilepicture;
    }

    public Actor profilepicture(String profilepicture) {
        this.profilepicture = profilepicture;
        return this;
    }

    public void setProfilepicture(String profilepicture) {
        this.profilepicture = profilepicture;
    }

    public String getBiolink() {
        return biolink;
    }

    public Actor biolink(String biolink) {
        this.biolink = biolink;
        return this;
    }

    public void setBiolink(String biolink) {
        this.biolink = biolink;
    }

    public Set<Telenovela> getTelenovelas() {
        return telenovelas;
    }

    public Actor telenovelas(Set<Telenovela> telenovelas) {
        this.telenovelas = telenovelas;
        return this;
    }

    public Actor addTelenovela(Telenovela telenovela) {
        this.telenovelas.add(telenovela);
        telenovela.getActors().add(this);
        return this;
    }

    public Actor removeTelenovela(Telenovela telenovela) {
        this.telenovelas.remove(telenovela);
        telenovela.getActors().remove(this);
        return this;
    }

    public void setTelenovelas(Set<Telenovela> telenovelas) {
        this.telenovelas = telenovelas;
    }

    public Set<Episode> getEpisodes() {
        return episodes;
    }

    public Actor episodes(Set<Episode> episodes) {
        this.episodes = episodes;
        return this;
    }

    public Actor addEpisode(Episode episode) {
        this.episodes.add(episode);
        episode.getActors().add(this);
        return this;
    }

    public Actor removeEpisode(Episode episode) {
        this.episodes.remove(episode);
        episode.getActors().remove(this);
        return this;
    }

    public void setEpisodes(Set<Episode> episodes) {
        this.episodes = episodes;
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
        Actor actor = (Actor) o;
        if (actor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), actor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Actor{" +
            "id=" + getId() +
            ", firstname='" + getFirstname() + "'" +
            ", middlename='" + getMiddlename() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", profilepicture='" + getProfilepicture() + "'" +
            ", biolink='" + getBiolink() + "'" +
            "}";
    }
}
