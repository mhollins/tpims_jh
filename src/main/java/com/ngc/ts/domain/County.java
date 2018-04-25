package com.ngc.ts.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A County.
 */
@Entity
@Table(name = "county")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "county")
public class County implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "county_name")
    private String countyName;

    @ManyToOne
    private District district;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public County countyName(String countyName) {
        this.countyName = countyName;
        return this;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public District getDistrict() {
        return district;
    }

    public County district(District district) {
        this.district = district;
        return this;
    }

    public void setDistrict(District district) {
        this.district = district;
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
        County county = (County) o;
        if (county.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), county.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "County{" +
            "id=" + getId() +
            ", countyName='" + getCountyName() + "'" +
            "}";
    }
}
