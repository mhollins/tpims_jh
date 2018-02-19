package com.ngc.ts.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.ngc.ts.domain.enumeration.OwnerShipOptions;

/**
 * A DTO for the Site entity.
 */
public class SiteDTO implements Serializable {

    private Long id;

    @NotNull
    private String siteId;

    private String siteName;

    private Integer totalCapacity;

    private Integer lowThreshold;

    private ZonedDateTime staticDataUpdated;

    private OwnerShipOptions ownership;

    private Long locationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Integer getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(Integer totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public Integer getLowThreshold() {
        return lowThreshold;
    }

    public void setLowThreshold(Integer lowThreshold) {
        this.lowThreshold = lowThreshold;
    }

    public ZonedDateTime getStaticDataUpdated() {
        return staticDataUpdated;
    }

    public void setStaticDataUpdated(ZonedDateTime staticDataUpdated) {
        this.staticDataUpdated = staticDataUpdated;
    }

    public OwnerShipOptions getOwnership() {
        return ownership;
    }

    public void setOwnership(OwnerShipOptions ownership) {
        this.ownership = ownership;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SiteDTO siteDTO = (SiteDTO) o;
        if(siteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), siteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SiteDTO{" +
            "id=" + getId() +
            ", siteId='" + getSiteId() + "'" +
            ", siteName='" + getSiteName() + "'" +
            ", totalCapacity=" + getTotalCapacity() +
            ", lowThreshold=" + getLowThreshold() +
            ", staticDataUpdated='" + getStaticDataUpdated() + "'" +
            ", ownership='" + getOwnership() + "'" +
            "}";
    }
}
