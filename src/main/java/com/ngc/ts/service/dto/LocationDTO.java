package com.ngc.ts.service.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Location entity.
 */
public class LocationDTO implements Serializable {

    private Long id;

    private String locationType;

    private String locationOwner;

    private String relevantHighway;

    private String referencePost;

    private String exitId;

    private String directionOfTravel;

    private String streetAdr;

    private String city;

    private String postalCode;

    private String timeZone;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private Long countyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getLocationOwner() {
        return locationOwner;
    }

    public void setLocationOwner(String locationOwner) {
        this.locationOwner = locationOwner;
    }

    public String getRelevantHighway() {
        return relevantHighway;
    }

    public void setRelevantHighway(String relevantHighway) {
        this.relevantHighway = relevantHighway;
    }

    public String getReferencePost() {
        return referencePost;
    }

    public void setReferencePost(String referencePost) {
        this.referencePost = referencePost;
    }

    public String getExitId() {
        return exitId;
    }

    public void setExitId(String exitId) {
        this.exitId = exitId;
    }

    public String getDirectionOfTravel() {
        return directionOfTravel;
    }

    public void setDirectionOfTravel(String directionOfTravel) {
        this.directionOfTravel = directionOfTravel;
    }

    public String getStreetAdr() {
        return streetAdr;
    }

    public void setStreetAdr(String streetAdr) {
        this.streetAdr = streetAdr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Long getCountyId() {
        return countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LocationDTO locationDTO = (LocationDTO) o;
        if(locationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), locationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LocationDTO{" +
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
