package com.ngc.ts.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.ngc.ts.domain.enumeration.TrendOptions;

/**
 * A DTO for the HistoricSiteData entity.
 */
public class HistoricSiteDataDTO implements Serializable {

    private Long id;

    private Integer totalCapacity;

    private Integer availability;

    private TrendOptions trend;

    private Boolean open;

    private Boolean trustData;

    private Instant timeStamp;

    private Boolean verificationCheck;

    private Integer trueAvailable;

    private Long siteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(Integer totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public Integer getAvailability() {
        return availability;
    }

    public void setAvailability(Integer availability) {
        this.availability = availability;
    }

    public TrendOptions getTrend() {
        return trend;
    }

    public void setTrend(TrendOptions trend) {
        this.trend = trend;
    }

    public Boolean isOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Boolean isTrustData() {
        return trustData;
    }

    public void setTrustData(Boolean trustData) {
        this.trustData = trustData;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Boolean isVerificationCheck() {
        return verificationCheck;
    }

    public void setVerificationCheck(Boolean verificationCheck) {
        this.verificationCheck = verificationCheck;
    }

    public Integer getTrueAvailable() {
        return trueAvailable;
    }

    public void setTrueAvailable(Integer trueAvailable) {
        this.trueAvailable = trueAvailable;
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

        HistoricSiteDataDTO historicSiteDataDTO = (HistoricSiteDataDTO) o;
        if(historicSiteDataDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), historicSiteDataDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HistoricSiteDataDTO{" +
            "id=" + getId() +
            ", totalCapacity=" + getTotalCapacity() +
            ", availability=" + getAvailability() +
            ", trend='" + getTrend() + "'" +
            ", open='" + isOpen() + "'" +
            ", trustData='" + isTrustData() + "'" +
            ", timeStamp='" + getTimeStamp() + "'" +
            ", verificationCheck='" + isVerificationCheck() + "'" +
            ", trueAvailable=" + getTrueAvailable() +
            "}";
    }
}
