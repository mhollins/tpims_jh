package com.ngc.ts.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the DeviceData entity.
 */
public class DeviceDataDTO implements Serializable {

    private Long id;

    private ZonedDateTime timeStamp;

    private String deviceName;

    private Integer zone;

    private String status;

    private Integer speed;

    private Integer volume;

    private Integer occupancy;

    private Long deviceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(ZonedDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getZone() {
        return zone;
    }

    public void setZone(Integer zone) {
        this.zone = zone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Integer getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(Integer occupancy) {
        this.occupancy = occupancy;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeviceDataDTO deviceDataDTO = (DeviceDataDTO) o;
        if(deviceDataDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deviceDataDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeviceDataDTO{" +
            "id=" + getId() +
            ", timeStamp='" + getTimeStamp() + "'" +
            ", deviceName='" + getDeviceName() + "'" +
            ", zone=" + getZone() +
            ", status='" + getStatus() + "'" +
            ", speed=" + getSpeed() +
            ", volume=" + getVolume() +
            ", occupancy=" + getOccupancy() +
            "}";
    }
}
