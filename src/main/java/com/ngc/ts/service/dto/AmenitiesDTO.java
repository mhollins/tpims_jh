package com.ngc.ts.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Amenities entity.
 */
public class AmenitiesDTO implements Serializable {

    private Long id;

    private String amenity;

    private Long siteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAmenity() {
        return amenity;
    }

    public void setAmenity(String amenity) {
        this.amenity = amenity;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AmenitiesDTO amenitiesDTO = (AmenitiesDTO) o;
        if(amenitiesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), amenitiesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AmenitiesDTO{" +
            "id=" + getId() +
            ", amenity='" + getAmenity() + "'" +
            "}";
    }
}
