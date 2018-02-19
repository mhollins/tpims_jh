package com.ngc.ts.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Amenities.
 */
@Entity
@Table(name = "amenities")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "amenities")
public class Amenities implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "amenity")
    private String amenity;

    @ManyToOne
    private Site site;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAmenity() {
        return amenity;
    }

    public Amenities amenity(String amenity) {
        this.amenity = amenity;
        return this;
    }

    public void setAmenity(String amenity) {
        this.amenity = amenity;
    }

    public Site getSite() {
        return site;
    }

    public Amenities site(Site site) {
        this.site = site;
        return this;
    }

    public void setSite(Site site) {
        this.site = site;
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
        Amenities amenities = (Amenities) o;
        if (amenities.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), amenities.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Amenities{" +
            "id=" + getId() +
            ", amenity='" + getAmenity() + "'" +
            "}";
    }
}
