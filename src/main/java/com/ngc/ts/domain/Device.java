package com.ngc.ts.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import com.ngc.ts.domain.enumeration.LocationFunctions;

/**
 * Static Device Configuration
 */
@ApiModel(description = "Static Device Configuration")
@Entity
@Table(name = "device")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "device")
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "ip_port")
    private Integer ipPort;

    @Column(name = "device_address")
    private Integer deviceAddress;

    @Column(name = "polling_rate")
    private Integer pollingRate;

    @Column(name = "jms_domain")
    private String jmsDomain;

    @Column(name = "timeout")
    private Integer timeout;

    @Column(name = "max_per_cycle")
    private Integer maxPerCycle;

    @Enumerated(EnumType.STRING)
    @Column(name = "locationfunction")
    private LocationFunctions locationfunction;

    @ManyToOne
    private Site site;

    @ManyToOne
    private Location location;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public Device deviceName(String deviceName) {
        this.deviceName = deviceName;
        return this;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Device ipAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getIpPort() {
        return ipPort;
    }

    public Device ipPort(Integer ipPort) {
        this.ipPort = ipPort;
        return this;
    }

    public void setIpPort(Integer ipPort) {
        this.ipPort = ipPort;
    }

    public Integer getDeviceAddress() {
        return deviceAddress;
    }

    public Device deviceAddress(Integer deviceAddress) {
        this.deviceAddress = deviceAddress;
        return this;
    }

    public void setDeviceAddress(Integer deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public Integer getPollingRate() {
        return pollingRate;
    }

    public Device pollingRate(Integer pollingRate) {
        this.pollingRate = pollingRate;
        return this;
    }

    public void setPollingRate(Integer pollingRate) {
        this.pollingRate = pollingRate;
    }

    public String getJmsDomain() {
        return jmsDomain;
    }

    public Device jmsDomain(String jmsDomain) {
        this.jmsDomain = jmsDomain;
        return this;
    }

    public void setJmsDomain(String jmsDomain) {
        this.jmsDomain = jmsDomain;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public Device timeout(Integer timeout) {
        this.timeout = timeout;
        return this;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public LocationFunctions getLocationfunction() {
        return locationfunction;
    }

    public Device locationfunction(LocationFunctions locationfunction) {
        this.locationfunction = locationfunction;
        return this;
    }

    public void setLocationfunction(LocationFunctions locationfunction) {
        this.locationfunction = locationfunction;
    }

    public Site getSite() {
        return site;
    }

    public Device site(Site site) {
        this.site = site;
        return this;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Location getLocation() {
        return location;
    }

    public Device location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    public Integer getMaxPerCycle() {
        return maxPerCycle;
    }

    public void setMaxPerCycle(Integer maxPerCycle) {
        this.maxPerCycle = maxPerCycle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Device device = (Device) o;
        if (device.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), device.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Device{" +
            "id=" + getId() +
            ", deviceName='" + getDeviceName() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", ipPort=" + getIpPort() +
            ", deviceAddress=" + getDeviceAddress() +
            ", pollingRate=" + getPollingRate() +
            ", jmsDomain='" + getJmsDomain() + "'" +
            ", timeout=" + getTimeout() +
            ", locationfunction='" + getLocationfunction() + "'" +
            "}";
    }
}
