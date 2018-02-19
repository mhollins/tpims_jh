package com.ngc.ts.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.ngc.ts.domain.enumeration.TrendOptions;

/**
 * A DTO for the SiteStatus entity.
 */
public class SiteStatusDTO implements Serializable {

    private Long id;

    private Integer reportedAvailable;

    private TrendOptions trend;

    private Boolean open;

    private Boolean trustData;

    private ZonedDateTime lastDeviceUpdate;

    private ZonedDateTime lastOperatorUpdate;

    private Long siteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getReportedAvailable() {
        return reportedAvailable;
    }

    public void setReportedAvailable(Integer reportedAvailable) {
        this.reportedAvailable = reportedAvailable;
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

    public ZonedDateTime getLastDeviceUpdate() {
        return lastDeviceUpdate;
    }

    public void setLastDeviceUpdate(ZonedDateTime lastDeviceUpdate) {
        this.lastDeviceUpdate = lastDeviceUpdate;
    }

    public ZonedDateTime getLastOperatorUpdate() {
        return lastOperatorUpdate;
    }

    public void setLastOperatorUpdate(ZonedDateTime lastOperatorUpdate) {
        this.lastOperatorUpdate = lastOperatorUpdate;
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

        SiteStatusDTO siteStatusDTO = (SiteStatusDTO) o;
        if(siteStatusDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), siteStatusDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SiteStatusDTO{" +
            "id=" + getId() +
            ", reportedAvailable=" + getReportedAvailable() +
            ", trend='" + getTrend() + "'" +
            ", open='" + isOpen() + "'" +
            ", trustData='" + isTrustData() + "'" +
            ", lastDeviceUpdate='" + getLastDeviceUpdate() + "'" +
            ", lastOperatorUpdate='" + getLastOperatorUpdate() + "'" +
            "}";
    }
}
