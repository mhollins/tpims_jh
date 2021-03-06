package com.ngc.ts.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.ngc.ts.domain.enumeration.TrendOptions;

/**
 * Dynamic Site Status
 */
@ApiModel(description = "Dynamic Site Status")
@Entity
@Table(name = "site_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sitestatus")
public class SiteStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "reported_available")
    private Integer reportedAvailable;

    @Column(name = "vehicles_counted")
    private Integer vehiclesCounted;

    @Enumerated(EnumType.STRING)
    @Column(name = "trend")
    private TrendOptions trend;

    /**
     * wheather parking site is open or closed
     */
    @ApiModelProperty(value = "wheather parking site is open or closed")
    @Column(name = "jhi_open")
    private Boolean open;

    /**
     * reports that the site is opeating normally
     * Possible reasons for false include construction,
     * maintenance, equipment failures
     */
    @ApiModelProperty(value = "reports that the site is opeating normally Possible reasons for false include construction, maintenance, equipment failures")
    @Column(name = "trust_data")
    private Boolean trustData;

    @Column(name = "last_device_update")
    private Instant lastDeviceUpdate;

    @Column(name = "last_operator_update")
    private Instant lastOperatorUpdate;

    @Column(name = "verification_check_amplitude")
    private Integer verificationCheckAmplitude;

    @OneToOne
    @JoinColumn(unique = true)
    private Site site;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getReportedAvailable() {
        return reportedAvailable;
    }

    public SiteStatus reportedAvailable(Integer reportedAvailable) {
        this.reportedAvailable = reportedAvailable;
        return this;
    }

    public void setReportedAvailable(Integer reportedAvailable) {
        this.reportedAvailable = reportedAvailable;
    }

    public Integer getVehiclesCounted() {
        return vehiclesCounted;
    }

    public SiteStatus vehiclesCounted(Integer vehiclesCounted) {
        this.vehiclesCounted = vehiclesCounted;
        return this;
    }

    public void setVehiclesCounted(Integer vehiclesCounted) {
        this.vehiclesCounted = vehiclesCounted;
    }

    public TrendOptions getTrend() {
        return trend;
    }

    public SiteStatus trend(TrendOptions trend) {
        this.trend = trend;
        return this;
    }

    public void setTrend(TrendOptions trend) {
        this.trend = trend;
    }

    public Boolean isOpen() {
        return open;
    }

    public SiteStatus open(Boolean open) {
        this.open = open;
        return this;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Boolean isTrustData() {
        return trustData;
    }

    public SiteStatus trustData(Boolean trustData) {
        this.trustData = trustData;
        return this;
    }

    public void setTrustData(Boolean trustData) {
        this.trustData = trustData;
    }

    public Instant getLastDeviceUpdate() {
        return lastDeviceUpdate;
    }

    public SiteStatus lastDeviceUpdate(Instant lastDeviceUpdate) {
        this.lastDeviceUpdate = lastDeviceUpdate;
        return this;
    }

    public void setLastDeviceUpdate(Instant lastDeviceUpdate) {
        this.lastDeviceUpdate = lastDeviceUpdate;
    }

    public Instant getLastOperatorUpdate() {
        return lastOperatorUpdate;
    }

    public SiteStatus lastOperatorUpdate(Instant lastOperatorUpdate) {
        this.lastOperatorUpdate = lastOperatorUpdate;
        return this;
    }

    public void setLastOperatorUpdate(Instant lastOperatorUpdate) {
        this.lastOperatorUpdate = lastOperatorUpdate;
    }

    public Integer getVerificationCheckAmplitude() {
        return verificationCheckAmplitude;
    }

    public SiteStatus verificationCheckAmplitude(Integer verificationCheckAmplitude) {
        this.verificationCheckAmplitude = verificationCheckAmplitude;
        return this;
    }

    public void setVerificationCheckAmplitude(Integer verificationCheckAmplitude) {
        this.verificationCheckAmplitude = verificationCheckAmplitude;
    }

    public Site getSite() {
        return site;
    }

    public SiteStatus site(Site site) {
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
        SiteStatus siteStatus = (SiteStatus) o;
        if (siteStatus.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), siteStatus.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SiteStatus{" +
            "id=" + getId() +
            ", reportedAvailable=" + getReportedAvailable() +
            ", vehiclesCounted=" + getVehiclesCounted() +
            ", trend='" + getTrend() + "'" +
            ", open='" + isOpen() + "'" +
            ", trustData='" + isTrustData() + "'" +
            ", lastDeviceUpdate='" + getLastDeviceUpdate() + "'" +
            ", lastOperatorUpdate='" + getLastOperatorUpdate() + "'" +
            ", verificationCheckAmplitude=" + getVerificationCheckAmplitude() +
            "}";
    }
}
