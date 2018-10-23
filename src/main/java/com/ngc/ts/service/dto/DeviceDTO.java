package com.ngc.ts.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.ngc.ts.domain.enumeration.LocationFunctions;

/**
 * A DTO for the Device entity.
 */
public class DeviceDTO implements Serializable {

    private Long id;

    private String deviceName;

    private String ipAddress;

    private Integer ipPort;

    private Integer deviceAddress;

    private Integer pollingRate;

    private String jmsDomain;

    private Integer timeout;

    private LocationFunctions locationfunction;

    private Long siteId;

    private String siteName;

    private Long locationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getIpPort() {
        return ipPort;
    }

    public void setIpPort(Integer ipPort) {
        this.ipPort = ipPort;
    }

    public Integer getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(Integer deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public Integer getPollingRate() {
        return pollingRate;
    }

    public void setPollingRate(Integer pollingRate) {
        this.pollingRate = pollingRate;
    }

    public String getJmsDomain() {
        return jmsDomain;
    }

    public void setJmsDomain(String jmsDomain) {
        this.jmsDomain = jmsDomain;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public LocationFunctions getLocationfunction() {
        return locationfunction;
    }

    public void setLocationfunction(LocationFunctions locationfunction) {
        this.locationfunction = locationfunction;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
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

        DeviceDTO deviceDTO = (DeviceDTO) o;
        if(deviceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deviceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeviceDTO{" +
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
