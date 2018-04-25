package com.ngc.ts.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.ngc.ts.domain.enumeration.TrendOptions;

/**
 * A HistoricSiteData.
 */
@Entity
@Table(name = "historic_site_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "historicsitedata")
public class HistoricSiteData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "total_capacity")
    private Integer totalCapacity;

    @Column(name = "availability")
    private Integer availability;

    @Enumerated(EnumType.STRING)
    @Column(name = "trend")
    private TrendOptions trend;

    @Column(name = "jhi_open")
    private Boolean open;

    @Column(name = "trust_data")
    private Boolean trustData;

    @Column(name = "time_stamp")
    private ZonedDateTime timeStamp;

    /**
     * notes whether available spots
     * is based on a manual reset
     */
    @ApiModelProperty(value = "notes whether available spots is based on a manual reset")
    @Column(name = "verification_check")
    private Boolean verificationCheck;

    /**
     * actual number availble, can
     * exceed capacity & go below low threshold
     */
    @ApiModelProperty(value = "actual number availble, can exceed capacity & go below low threshold")
    @Column(name = "true_available")
    private Integer trueAvailable;

    @ManyToOne
    private Site site;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalCapacity() {
        return totalCapacity;
    }

    public HistoricSiteData totalCapacity(Integer totalCapacity) {
        this.totalCapacity = totalCapacity;
        return this;
    }

    public void setTotalCapacity(Integer totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public Integer getAvailability() {
        return availability;
    }

    public HistoricSiteData availability(Integer availability) {
        this.availability = availability;
        return this;
    }

    public void setAvailability(Integer availability) {
        this.availability = availability;
    }

    public TrendOptions getTrend() {
        return trend;
    }

    public HistoricSiteData trend(TrendOptions trend) {
        this.trend = trend;
        return this;
    }

    public void setTrend(TrendOptions trend) {
        this.trend = trend;
    }

    public Boolean isOpen() {
        return open;
    }

    public HistoricSiteData open(Boolean open) {
        this.open = open;
        return this;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Boolean isTrustData() {
        return trustData;
    }

    public HistoricSiteData trustData(Boolean trustData) {
        this.trustData = trustData;
        return this;
    }

    public void setTrustData(Boolean trustData) {
        this.trustData = trustData;
    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }

    public HistoricSiteData timeStamp(ZonedDateTime timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public void setTimeStamp(ZonedDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Boolean isVerificationCheck() {
        return verificationCheck;
    }

    public HistoricSiteData verificationCheck(Boolean verificationCheck) {
        this.verificationCheck = verificationCheck;
        return this;
    }

    public void setVerificationCheck(Boolean verificationCheck) {
        this.verificationCheck = verificationCheck;
    }

    public Integer getTrueAvailable() {
        return trueAvailable;
    }

    public HistoricSiteData trueAvailable(Integer trueAvailable) {
        this.trueAvailable = trueAvailable;
        return this;
    }

    public void setTrueAvailable(Integer trueAvailable) {
        this.trueAvailable = trueAvailable;
    }

    public Site getSite() {
        return site;
    }

    public HistoricSiteData site(Site site) {
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
        HistoricSiteData historicSiteData = (HistoricSiteData) o;
        if (historicSiteData.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), historicSiteData.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HistoricSiteData{" +
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
