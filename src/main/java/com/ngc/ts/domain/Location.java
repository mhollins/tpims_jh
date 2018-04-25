package com.ngc.ts.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Site Location
 */
@ApiModel(description = "Site Location")
@Entity
@Table(name = "location")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "location")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "location_type")
    private String locationType;

    @Column(name = "location_owner")
    private String locationOwner;

    @Column(name = "relevant_highway")
    private String relevantHighway;

    @Column(name = "reference_post")
    private String referencePost;

    @Column(name = "exit_id")
    private String exitId;

    @Column(name = "direction_of_travel")
    private String directionOfTravel;

    @Column(name = "street_adr")
    private String streetAdr;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "time_zone")
    private String timeZone;

    @Column(name = "latitude", precision=10, scale=2)
    private BigDecimal latitude;

    @Column(name = "longitude", precision=10, scale=2)
    private BigDecimal longitude;

    @ManyToOne
    private County county;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocationType() {
        return locationType;
    }

    public Location locationType(String locationType) {
        this.locationType = locationType;
        return this;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getLocationOwner() {
        return locationOwner;
    }

    public Location locationOwner(String locationOwner) {
        this.locationOwner = locationOwner;
        return this;
    }

    public void setLocationOwner(String locationOwner) {
        this.locationOwner = locationOwner;
    }

    public String getRelevantHighway() {
        return relevantHighway;
    }

    public Location relevantHighway(String relevantHighway) {
        this.relevantHighway = relevantHighway;
        return this;
    }

    public void setRelevantHighway(String relevantHighway) {
        this.relevantHighway = relevantHighway;
    }

    public String getReferencePost() {
        return referencePost;
    }

    public Location referencePost(String referencePost) {
        this.referencePost = referencePost;
        return this;
    }

    public void setReferencePost(String referencePost) {
        this.referencePost = referencePost;
    }

    public String getExitId() {
        return exitId;
    }

    public Location exitId(String exitId) {
        this.exitId = exitId;
        return this;
    }

    public void setExitId(String exitId) {
        this.exitId = exitId;
    }

    public String getDirectionOfTravel() {
        return directionOfTravel;
    }

    public Location directionOfTravel(String directionOfTravel) {
        this.directionOfTravel = directionOfTravel;
        return this;
    }

    public void setDirectionOfTravel(String directionOfTravel) {
        this.directionOfTravel = directionOfTravel;
    }

    public String getStreetAdr() {
        return streetAdr;
    }

    public Location streetAdr(String streetAdr) {
        this.streetAdr = streetAdr;
        return this;
    }

    public void setStreetAdr(String streetAdr) {
        this.streetAdr = streetAdr;
    }

    public String getCity() {
        return city;
    }

    public Location city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Location postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public Location timeZone(String timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public Location latitude(BigDecimal latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public Location longitude(BigDecimal longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public County getCounty() {
        return county;
    }

    public Location county(County county) {
        this.county = county;
        return this;
    }

    public void setCounty(County county) {
        this.county = county;
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
        Location location = (Location) o;
        if (location.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), location.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", locationType='" + getLocationType() + "'" +
            ", locationOwner='" + getLocationOwner() + "'" +
            ", relevantHighway='" + getRelevantHighway() + "'" +
            ", referencePost='" + getReferencePost() + "'" +
            ", exitId='" + getExitId() + "'" +
            ", directionOfTravel='" + getDirectionOfTravel() + "'" +
            ", streetAdr='" + getStreetAdr() + "'" +
            ", city='" + getCity() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", timeZone='" + getTimeZone() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            "}";
    }
}
